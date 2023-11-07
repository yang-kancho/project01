package com.project.project00;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;

@Slf4j
@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(final ResourceHandlerRegistry registry) {

        registry.addResourceHandler("/profile/**")
                .addResourceLocations("file:project00-main/project00-main/project00_test/src/main/resources/static/image/profile/");
//                .addResourceLocations("file:C:/project/project00/project00-main/project00-main/project00_test/src/main/resources/static/image/profile/");

    }
}