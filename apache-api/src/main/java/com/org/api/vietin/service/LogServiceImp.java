package com.org.api.vietin.service;

import com.org.api.vietin.common.constant.Constant;
import com.org.api.vietin.model.dataset.LogDataset;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
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
    public List<String> getAllLog() {
        String logDir = constant.getLogDir();

        File logFolder = new File(logDir);
        if (Objects.isNull(logFolder) || !logFolder.exists() || !logFolder.isDirectory()) {
            return null;
        }

        File[] logFileArr = logFolder.listFiles(file -> Objects.nonNull(file) && file.isFile() && file.getName().equals("error.log"));
        List<String> results = new ArrayList<>();
        Optional.ofNullable(Arrays.asList(logFileArr))
                .orElse(Collections.emptyList())
                .stream()
                .filter(Objects::nonNull)
                .forEach(file -> {
                    try {
                        FileReader fr = new FileReader(file, Charset.forName("UTF8"));
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
                });

        return results;
    }

}
