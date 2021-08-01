package com.mindskip.xzs.configuration.spring.mvc;

import com.mindskip.xzs.configuration.property.SystemConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

import java.util.List;


@Configuration
public class WebMvcConfiguration extends WebMvcConfigurationSupport {

    private final SystemConfig systemConfig;

    @Autowired
    public WebMvcConfiguration(SystemConfig systemConfig) {
        this.systemConfig = systemConfig;
    }


    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowCredentials(true)
                .allowedMethods("*")
                .allowedOrigins("*")
                .allowedHeaders("*");
        super.addCorsMappings(registry);
    }

}
