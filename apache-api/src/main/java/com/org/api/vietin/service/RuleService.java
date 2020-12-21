package com.org.api.vietin.service;

import com.org.api.vietin.model.dataset.RuleDataset;
import com.org.api.vietin.model.dataset.RuleFileDataset;
import com.org.api.vietin.model.dataset.UserDataset;
import com.org.api.vietin.model.dataset.UserPasswordDataset;

import java.util.List;

/**
 * UserService
 *
 *
 * @since 2020/11/29
 */
public interface RuleService {

    boolean createRule(RuleDataset ruleDataset);
    List<RuleDataset> getAllRule(String userId, String roleId);
    boolean removeRule (String id, String userId);
    boolean updateRule (String id, String userId, String status);
    RuleFileDataset downloadRule (String ruleId, String userId);

}
