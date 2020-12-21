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

    /**
     * Conf Directory
     */
    @Value("${DIR.CONF}")
    private String confDir;

    /**
     * CMD Restart apache ubuntu
     */
    @Value("${CMD.RESTART_APACHE_UBUNTU}")
    private String cmdRestartApacheUbuntu;

    /**
     * CMD Restart apache centos
     */
    @Value("${CMD.RESTART_APACHE_CENTOS}")
    private String cmdRestartApacheCentOS;

    /**
     * CMD Restart apache centos
     */
    @Value("${AUTH.EXPIRATION}")
    private Integer authExpiration;

}
