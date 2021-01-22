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
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
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

        String dir = constant.getRootDir() + File.separator + ruleDataset.getId();
        String fileName = ruleDataset.getId() + "_" + ruleDataset.getFileName();
        CommonUtils.saveOnce(dir, fileName, ruleDataset.getFile());

        boolean result = ruleMapper.insRule(ruleDataset) != 0;
        if (result)
            return updateRule(id, DataConstant.RULE_STATUS[0]);

        try {
            activeRules();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public List<RuleDataset> getAllRule() {
        return ruleMapper.selRules();
    }

    public boolean removeRule(String id) {
        boolean result = ruleMapper.delRule(id) != 0;
        if (result) {
            String dir = constant.getRootDir() + File.separator + id;
            File folder = new File(dir);
            if (Objects.nonNull(folder) && folder.exists() && folder.isDirectory()) {
                File[] confDirFileArr = folder.listFiles(f -> Objects.nonNull(f) && f.exists() && f.isFile() && f.getName().endsWith(".conf"));
                File confFile = Objects.nonNull(confDirFileArr) && confDirFileArr.length != 0 ? confDirFileArr[0] : null;
                try {
                    FileUtils.cleanDirectory(folder);
                    folder.delete();

                    if (Objects.nonNull(confFile)) {
                        new File(constant.getApacheModsDir() + confFile.getName()).delete();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return result;
    }

    public boolean updateRule(String id, String status) {
        // get config file in folder dir of webapplication
        String dirRule = constant.getRootDir() + File.separator + id + File.separator;
        File dirFolder = new File(dirRule);
        File confFile = null;
        if (Objects.nonNull(dirFolder) && dirFolder.exists() && dirFolder.isDirectory()) {
            File[] confDirFileArr = dirFolder.listFiles(f -> Objects.nonNull(f) && f.exists() && f.isFile() && f.getName().endsWith(".conf"));
            confFile = Objects.nonNull(confDirFileArr) && confDirFileArr.length != 0 ? confDirFileArr[0] : null;
        }

        try {
            // case enabled
            if (DataConstant.RULE_STATUS[0].equals(status)) {
                if (Objects.nonNull(confFile)) {
                    // copy file config to mod-security of apache2
                    FileUtils.copyFileToDirectory(confFile, new File(constant.getApacheModsDir()));

                    // restart apache2 to enable mod-security
                    CommonUtils.restartHttpd(constant);
                }
            } else // case ready and disabled
            if (DataConstant.RULE_STATUS[1].equals(status)) {
                if (Objects.nonNull(confFile)) {
                    // get file name
                    String confDirFile = constant.getApacheModsDir() + confFile.getName();
                    // delete file
                    new File(confDirFile).delete();

                    // restart apache2 to enable mod-security
                    CommonUtils.restartHttpd(constant);
                }
            }
            boolean result = ruleMapper.updRule(id, status) != 0;
            if (result) {
                activeRules();
                return true;
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        return false;
    }

    public RuleFileDataset downloadRule(String ruleId) {
        String dir = constant.getRootDir() + File.separator + ruleId;
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

    private void activeRules () throws IOException {
        List<RuleDataset> enableRules = ruleMapper.selEnableRules();
        String mods = "";
        for (RuleDataset rule: enableRules) {
            mods += ("Include mods/" + rule.getId() + "_" + rule.getFileName()) + "\n";
        }

        String modSecConfig = DataConstant.MOD_SEC_CONFIG.replace("MOD_SEC_DATA_CONTENT", mods);

        File modConfFile = new File(constant.getApacheConfDir() + "mods.conf");
        modConfFile.delete();

        File newModConfFile = new File(constant.getApacheConfDir() + "mods.conf");
        newModConfFile.createNewFile();

        Path path = Paths.get(constant.getApacheConfDir() + "mods.conf");
        Files.write(path, modSecConfig.getBytes(StandardCharsets.UTF_8),
                StandardOpenOption.CREATE,
                StandardOpenOption.APPEND);
    }
}
