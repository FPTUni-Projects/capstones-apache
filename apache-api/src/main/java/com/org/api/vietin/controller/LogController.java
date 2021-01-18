package com.org.api.vietin.controller;

import com.org.api.vietin.model.dataset.LogDataset;
import com.org.api.vietin.model.dataset.RuleDataset;
import com.org.api.vietin.model.dataset.RuleFileDataset;
import com.org.api.vietin.service.LogService;
import com.org.api.vietin.service.RuleService;
import lombok.Getter;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * LogController
 *
 *
 * @since 2020/11/14
 */
@RestController
@RequestMapping(value = "/vi/log/api/v1")
public class LogController {

    private final LogService logService;

    public LogController(LogService logService) {
        this.logService = logService;
    }

    @GetMapping(value = "/get-all-log", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<String> getAllLog() {
        return logService.getAllLog();
    }

}
