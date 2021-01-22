package com.org.api.vietin.service;

import com.org.api.vietin.common.constant.Constant;
import com.org.api.vietin.common.constant.DataConstant;
import com.org.api.vietin.common.id.GenIDComponent;
import com.org.api.vietin.mapper.RuleMapper;
import com.org.api.vietin.model.dataset.RuleDataset;
import com.org.api.vietin.model.dataset.RuleFileDataset;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;

import com.org.api.vietin.common.utils.CommonUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * UserServiceImp
 *
 *
 * @since 2020/11/29
 */
@Service
public class RuleService {

    private final Constant constant;
    private final GenIDComponent genIDComponent;
    private final RuleMapper ruleMapper;

    public RuleService(Constant constant,
                       GenIDComponent genIDComponent,
                       RuleMapper ruleMapper) {
        this.constant = constant;
        this.genIDComponent = genIDComponent;
        this.ruleMapper = ruleMapper;
    }
    public boolean createRule(RuleDataset ruleDataset) {
        String id = genIDComponent.getId(7, "ca_rule");
        ruleDataset.setId(id);

        String dir = constant.getRootDir() + ruleDataset.getUserId() + File.separator + ruleDataset.getId();
        String fileName = ruleDataset.getId() + "_" + ruleDataset.getFileName();
        CommonUtils.saveOnce(dir, fileName, ruleDataset.getFile());

        return ruleMapper.insRule(ruleDataset) != 0;
    }
    public List<RuleDataset> getAllRule(String userId, String roleId) {
        return ruleMapper.selRules(userId, roleId);
    }
    public boolean removeRule(String id, String userId) {
        boolean result = ruleMapper.delRule(id) != 0;
        if (result) {
            String dir = constant.getRootDir() + userId + File.separator + id;
            File folder = new File(dir);
            if (Objects.nonNull(folder) && folder.exists() && folder.isDirectory()) {
                File[] confDirFileArr = folder.listFiles(f -> Objects.nonNull(f) && f.exists() && f.isFile() && f.getName().endsWith(".conf"));
                File confFile = Objects.nonNull(confDirFileArr) && confDirFileArr.length != 0 ? confDirFileArr[0] : null;
                try {
                    FileUtils.cleanDirectory(folder);
                    folder.delete();

                    if (Objects.nonNull(confFile)) {
                        new File(constant.getConfDir() + confFile.getName()).delete();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return result;
    }
    public boolean updateRule(String id, String userId, String status) {
        // get config file in folder dir of webapplication
        String dirRule = constant.getRootDir() + userId + File.separator + id + File.separator;
        File dirFolder = new File(dirRule);
        File confFile = null;
        if (Objects.nonNull(dirFolder) && dirFolder.exists() && dirFolder.isDirectory()) {
            File[] confDirFileArr = dirFolder.listFiles(f -> Objects.nonNull(f) && f.exists() && f.isFile() && f.getName().endsWith(".conf"));
            confFile = Objects.nonNull(confDirFileArr) && confDirFileArr.length != 0 ? confDirFileArr[0] : null;
        }

        try {
            // case enabled
            if (DataConstant.RULE_STATUS[1].equals(status)) {
                if (Objects.nonNull(confFile)) {
                    // copy file config to mod-security of apache2
                    FileUtils.copyFileToDirectory(confFile, new File(constant.getConfDir()));

                    // restart apache2 to enable mod-security
                    Runtime run = Runtime.getRuntime();
                    Process pr = run.exec(constant.getCmdRestartApacheUbuntu());
                    pr.waitFor();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(pr.getInputStream()));
                    String line;
                    while((line = bufferedReader.readLine()) != null) {
                        System.out.println(line);
                    }
                }
            } else // case ready and disabled
            if (DataConstant.RULE_STATUS[0].equals(status) || DataConstant.RULE_STATUS[2].equals(status)) {
                if (Objects.nonNull(confFile)) {
                    // get file name
                    String confDirFile = constant.getConfDir() + confFile.getName();
                    // delete file
                    new File(confDirFile).delete();

                    // restart apache2 to enable mod-security
                    Runtime run = Runtime.getRuntime();
                    Process pr = run.exec(constant.getCmdRestartApacheUbuntu());
                    pr.waitFor();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(pr.getInputStream()));
                    String line;
                    while((line = bufferedReader.readLine()) != null) {
                        System.out.println(line);
                    }
                }
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return ruleMapper.updRule(id, status) != 0;
    }
    public RuleFileDataset downloadRule(String ruleId, String userId) {
        String dir = constant.getRootDir() + userId + File.separator + ruleId;
        File folder = new File(dir);

        if (Objects.isNull(folder) || !folder.exists()) {
            return null;
        }

        File[] fileArr = folder.listFiles(f -> Objects.nonNull(f) && f.exists() && f.isFile());
        if (Objects.isNull(fileArr) || fileArr.length == 0) {
            return null;
        }

        RuleFileDataset ruleFileDataset = new RuleFileDataset(fileArr[0].getName(), CommonUtils.readFileAsBase64Url(fileArr[0].getAbsolutePath()));
        return ruleFileDataset;
    }
}
