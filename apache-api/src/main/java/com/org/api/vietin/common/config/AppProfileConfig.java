package com.org.api.vietin.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;

/**
 * AppConstant
 *
 *
 * @since 2020/06/13
 */
@Configuration
public class AppProfileConfig {

    @Bean
    public PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer(Environment environment) {
        PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer = new PropertySourcesPlaceholderConfigurer();
        propertySourcesPlaceholderConfigurer.setIgnoreResourceNotFound(true);

        for (String profileName : environment.getActiveProfiles()) {
            System.out.println("RPOFILE NAME ====>  " + profileName);

            if ("prod".equals(profileName)) {
                propertySourcesPlaceholderConfigurer.setLocations(new ClassPathResource("constant-prod.properties"));
                propertySourcesPlaceholderConfigurer.setLocalOverride(true);
                propertySourcesPlaceholderConfigurer.setFileEncoding("UTF-8");
            } else if ("dev".equals(profileName)) {
                propertySourcesPlaceholderConfigurer.setLocations(new ClassPathResource("constant-dev.properties"));
                propertySourcesPlaceholderConfigurer.setLocalOverride(true);
                propertySourcesPlaceholderConfigurer.setFileEncoding("UTF-8");
            }
        }
        return propertySourcesPlaceholderConfigurer;
    }

}
