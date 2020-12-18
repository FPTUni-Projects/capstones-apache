package com.org.api.vietin.service;

import com.org.api.vietin.common.constant.Constant;
import com.org.api.vietin.model.dataset.LogDataset;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * LogService
 *
 * @since 2020/12/17
 */
@Service
public class LogServiceImp implements LogService {

    private final Constant constant;

    public LogServiceImp(Constant constant) {
        this.constant = constant;
    }

    @Override
    public List<LogDataset> getAllLog() {
        String logDir = constant.getLogDir();

        File logFolder = new File(logDir);
        if (Objects.isNull(logFolder) || !logFolder.exists() || !logFolder.isDirectory()) {
            return null;
        }

        File[] logFileArr = logFolder.listFiles(file -> Objects.nonNull(file) && file.isFile() && file.getName().contains("error"));
        List<LogDataset> results = new ArrayList<>();
        Optional.ofNullable(Arrays.asList(logFileArr))
                .orElse(Collections.emptyList())
                .stream()
                .filter(Objects::nonNull)
                .forEach(file -> {
                    try {
                        FileReader fr = new FileReader(file);
                        BufferedReader br = new BufferedReader(fr);

                        String line = br.readLine();
                        while (Objects.nonNull(line)) {
                            if (line.contains("[") && line.contains("]")) {
                                String tempInfo1 = line.substring(line.indexOf("[") + 1, line.lastIndexOf("]"));
                                System.out.println(tempInfo1);

                                tempInfo1 = tempInfo1.replaceAll("] \\[", "@@");
                                System.out.println(tempInfo1);
                                String[] tempInfo1Arr = tempInfo1.split("@@");
                                String time = tempInfo1Arr[0];
                                String error = tempInfo1Arr[1];
                                String host = tempInfo1Arr[2];
                                String description = line.substring(line.lastIndexOf("]") + 1).trim();

                                LogDataset logDataset = new LogDataset(time, error, host, description);
                                results.add(logDataset);
                            }

                            line = br.readLine();
                        }
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });

        return results;
    }

}
