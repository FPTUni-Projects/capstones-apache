package com.org.api.vietin.service;

import com.org.api.vietin.common.constant.Constant;
import com.org.api.vietin.mapper.UserMapper;
import com.org.api.vietin.model.dataset.ServerAnalysisDataset;
import com.org.api.vietin.model.dataset.UserServerDataset;
import org.apache.catalina.Server;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.*;

/**
 * LogService
 *
 * @since 2020/12/17
 */
@Service
public class LogService {

    private final Constant constant;
    private final UserMapper userMapper;

    public LogService(Constant constant, UserMapper userMapper) {
        this.constant = constant;
        this.userMapper = userMapper;
    }
    public List<String> getAllLog(String serverName) {
        String apacheLogsDir = constant.getApacheLogsDir();

        File logFolder = new File(apacheLogsDir);
        if (Objects.isNull(logFolder) || !logFolder.exists() || !logFolder.isDirectory())
            return Collections.emptyList();

        File logFile = new File(apacheLogsDir + serverName + "-error.log");
        if (!logFile.exists() || !logFile.isFile())
            return Collections.emptyList();

        List<String> results = new ArrayList<>();
        try {
            FileReader fr = new FileReader(logFile, Charset.forName("UTF8"));
            BufferedReader br = new BufferedReader(fr);

            String line = br.readLine();
            while (Objects.nonNull(line)) {
                if (StringUtils.hasLength(line)) {
                    results.add(line);
                }
                line = br.readLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return results;
    }

    public List<ServerAnalysisDataset> getAnalysisLog() {
        List<UserServerDataset> userServerDatasets = userMapper.selAllActiveUser();

        if (CollectionUtils.isEmpty(userServerDatasets))
            return Collections.emptyList();

        List<ServerAnalysisDataset> results = new ArrayList<>();
        for(UserServerDataset userServerDataset: userServerDatasets) {
            Integer lineLog = getAllLog(userServerDataset.getServerName()).size();
            results.add(new ServerAnalysisDataset(userServerDataset.getServerName(), lineLog));
        }

        return results;
    }

}
