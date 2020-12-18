package com.org.api.vietin.common.constant;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * Constant
 *
 *
 * @since 2020/06/13
 */
@Getter
@Component
@PropertySource(value = "classpath:constant-${spring.profiles.active}.properties", encoding = "utf-8")
public class Constant {

    /**
     * Directory
     */
    @Value("${DIR.ROOT}")
    private String rootDir;

    /**
     * Log Directory
     */
    @Value("${DIR.LOG}")
    private String logDir;

}
