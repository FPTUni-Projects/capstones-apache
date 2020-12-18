package com.org.api.vietin.service;

import com.org.api.vietin.common.constant.Constant;
import com.org.api.vietin.common.id.GenIDComponent;
import com.org.api.vietin.mapper.RuleMapper;
import com.org.api.vietin.model.dataset.RuleDataset;
import com.org.api.vietin.model.dataset.RuleFileDataset;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;

import com.org.api.vietin.common.utils.CommonUtils;
import java.io.File;
import java.io.IOException;
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
public class RuleServiceImp implements RuleService {

    private final Constant constant;
    private final GenIDComponent genIDComponent;
    private final RuleMapper ruleMapper;

    public RuleServiceImp(Constant constant,
                          GenIDComponent genIDComponent,
                          RuleMapper ruleMapper) {
        this.constant = constant;
        this.genIDComponent = genIDComponent;
        this.ruleMapper = ruleMapper;
    }

    @Override
    public boolean createRule(RuleDataset ruleDataset) {
        String id = genIDComponent.getId(7, "ca_rule");
        ruleDataset.setId(id);

        String dir = constant.getRootDir() + ruleDataset.getUserId() + File.separator + ruleDataset.getId();
        String fileName = ruleDataset.getFileName();
        CommonUtils.saveOnce(dir, fileName, ruleDataset.getFile());

        return ruleMapper.insRule(ruleDataset) != 0;
    }

    @Override
    public List<RuleDataset> getAllRule(String userId, String roleId) {
        return ruleMapper.selRules(userId, roleId);
    }

    @Override
    public boolean removeRule(String id, String userId) {
        boolean result = ruleMapper.delRule(id) != 0;
        if (result) {
            String dir = constant.getRootDir() + userId + File.separator + id;
            File folder = new File(dir);
            if (Objects.nonNull(folder) && folder.exists()) {
                try {
                    FileUtils.cleanDirectory(folder);
                    folder.delete();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return result;
    }

    @Override
    public boolean updateRule(String id, String status) {
        return ruleMapper.updRule(id, status) != 0;
    }

    @Override
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
