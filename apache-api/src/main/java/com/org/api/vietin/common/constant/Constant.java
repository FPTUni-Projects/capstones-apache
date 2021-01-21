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

    @Value("${DIR.ROOT}")
    private String rootDir;
    @Value("${DIR.LOG}")
    private String logDir;
    @Value("${DIR.CONF}")
    private String confDir;

    @Value("${DIR.APACHE}")
    private String apacheDir;
    @Value("${DIR.APACHE.BIN}")
    private String apacheBinDir;
    @Value("${DIR.APACHE.HTDOCS}")
    private String apacheHtdocs;
    @Value("${DIR.APACHE.CONF_EXTRA}")
    private String apacheConfExtraDir;

    @Value("${CMD.RESTART_APACHE_UBUNTU}")
    private String cmdRestartApacheUbuntu;
    @Value("${CMD.RESTART_APACHE_CENTOS}")
    private String cmdRestartApacheCentOS;
    @Value("${AUTH.EXPIRATION}")
    private Integer authExpiration;

}
