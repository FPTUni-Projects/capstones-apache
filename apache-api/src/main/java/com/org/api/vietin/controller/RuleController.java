package com.org.api.vietin.controller;

import com.org.api.vietin.model.dataset.RuleDataset;
import com.org.api.vietin.model.dataset.RuleFileDataset;
import com.org.api.vietin.model.dataset.UserDataset;
import com.org.api.vietin.model.dataset.UserPasswordDataset;
import com.org.api.vietin.service.RuleService;
import com.org.api.vietin.service.UserService;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * RuleController
 *
 *
 * @since 2020/11/14
 */
@RestController
@RequestMapping(value = "/vi/rule/api/v1")
public class RuleController {

    private final RuleService ruleService;

    public RuleController(RuleService ruleService) {
        this.ruleService = ruleService;
    }

    @PostMapping(value = "/create-rule", produces = MediaType.MULTIPART_FORM_DATA_VALUE)
    public boolean createRule(RuleDataset ruleDataset) {
        return ruleService.createRule(ruleDataset);
    }

    @GetMapping(value = "/get-all-rule", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<RuleDataset> getAllRule(@RequestParam("userId") String userId,
                                        @RequestParam("roleId") String roleId) {
        return ruleService.getAllRule(userId, roleId);
    }

    @GetMapping(value = "/remove-rule", produces = MediaType.APPLICATION_JSON_VALUE)
    public boolean removeRule(@RequestParam("ruleId") String ruleId,
                              @RequestParam("userId") String userId) {
        return ruleService.removeRule(ruleId, userId);
    }

    @GetMapping(value = "/update-rule", produces = MediaType.APPLICATION_JSON_VALUE)
    public boolean updateRule(@RequestParam("ruleId") String ruleId,
                              @RequestParam("status") String status) {
        return ruleService.updateRule(ruleId, status);
    }

    @GetMapping(value = "/download-rule", produces = MediaType.APPLICATION_JSON_VALUE)
    public RuleFileDataset downloadRule(@RequestParam("ruleId") String ruleId,
                                        @RequestParam("userId") String userId) {
        return ruleService.downloadRule(ruleId, userId);
    }
}
