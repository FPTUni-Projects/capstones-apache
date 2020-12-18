package com.org.api.vietin.service;

import com.org.api.vietin.model.dataset.LogDataset;

import java.util.List;

/**
 * LogService
 *
 * @since 2020/12/17
 */
public interface LogService {

    List<LogDataset> getAllLog();

}
