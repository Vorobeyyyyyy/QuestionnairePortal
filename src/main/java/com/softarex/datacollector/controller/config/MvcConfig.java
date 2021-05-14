package com.softarex.datacollector.controller.config;

import com.softarex.datacollector.model.converter.StringToFieldTypeConverter;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("redirect:fields");
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/answer_success").setViewName("answer_success");
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new StringToFieldTypeConverter());
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
