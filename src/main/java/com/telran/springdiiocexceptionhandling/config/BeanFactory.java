package com.telran.springdiiocexceptionhandling.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanFactory {

    private ObjectMapper mapper;

    public BeanFactory(ObjectMapper mapper) {
        this.mapper = mapper;
    }

}
