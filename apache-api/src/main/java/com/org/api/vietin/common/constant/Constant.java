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
@PropertySource(value = "classpath:constant.properties", encoding = "utf-8")
public class Constant {

    @Value("${DIR.ROOT}")
    private String rootDir;

    @Value("${DIR.APACHE}")
    private String apacheDir;
    @Value("${DIR.APACHE.BIN}")
    private String apacheBinDir;
    @Value("${DIR.APACHE.HTDOCS}")
    private String apacheHtdocs;
    @Value("${DIR.APACHE.CONF}")
    private String apacheConfDir;
    @Value("${DIR.APACHE.CONF_EXTRA}")
    private String apacheConfExtraDir;
    @Value("${DIR.APACHE.MODS}")
    private String apacheModsDir;
    @Value("${DIR.APACHE.LOGS}")
    private String apacheLogsDir;

    @Value("${AUTH.EXPIRATION}")
    private Integer authExpiration;

}
