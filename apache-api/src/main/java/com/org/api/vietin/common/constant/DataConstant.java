package com.org.api.vietin.common.constant;

/**
 * DataConstant
 *
 *
 * @since 2020/11/28
 */
public interface DataConstant {

    /**
     * Rule's status
     * 0: Enable
     * 1: Disable
     */
    String[] RULE_STATUS = {"0", "1"};

    String VHOST_CONFIG_TEMPLATE = "\n" +
            "<VirtualHost *:80>\n" +
            "    ServerAdmin webmaster@SERVER_NAME_CONTENT\n" +
            "    DocumentRoot \"${SRVROOT}/htdocs/SERVER_NAME_CONTENT\"\n" +
            "    ServerName SERVER_NAME_CONTENT\n" +
            "    ServerAlias SERVER_ALIAS_CONTENT\n" +
            "    ErrorLog \"logs/SERVER_NAME_CONTENT-error.log\"\n" +
            "    CustomLog \"logs/SERVER_NAME_CONTENT-access.log\" common\n" +
            "</VirtualHost>";

    String DEFAULT_TEMPLATE = "" +
            "<!DOCTYPE html>\n" +
            "<html lang=\"en\">\n" +
            "<head>\n" +
            "    <meta charset=\"UTF-8\">\n" +
            "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
            "    <title>SERVER_NAME_CONTENT</title>\n" +
            "</head>\n" +
            "<body>\n" +
            "    <1h>SERVER_NAME_CONTENT website has been deployed!</h1>" +
            "</body>\n" +
            "</html>";

    String MOD_SEC_CONFIG = "" +
            "LoadModule security2_module modules/mod_security2.so\n" +
            "SecRuleEngine DetectionOnly\n" +
            "<IfModule security2_module>\n" +
            "    SecRuleEngine On\n" +
            "    SecDefaultAction \"phase:2,deny,log,status:403\"\n" +
            "    MOD_SEC_DATA_CONTENT" +
            "</IfModule>\n";

}
