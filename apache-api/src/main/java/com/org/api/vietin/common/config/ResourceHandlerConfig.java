package com.org.api.vietin.common.config;

import com.org.api.vietin.common.constant.Constant;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.web.servlet.DispatcherServletAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * ResourceHandlerConfig
 *
 * @author khal
 * @since 2020/07/06
 */
@Configuration
@AutoConfigureAfter(DispatcherServletAutoConfiguration.class)
public class ResourceHandlerConfig implements WebMvcConfigurer {

    private Constant constant;

    /**
     * Default constructor
     */
    public ResourceHandlerConfig (final Constant constant) {
        this.constant = constant;
    }

    /**
     * Resource configuration
     *
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**",
                "swagger-ui.html",
                "/webjars/**")
                .addResourceLocations("file:" + constant.getRootDir(),
                        "classpath:/static/",
                        "classpath:/WEB-INF/",
                        "classpath:/META-INF/resources/",
                        "classpath:/META-INF/resources/webjars/");
    }

}
