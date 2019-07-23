package com.telran.springdiiocexceptionhandling.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.telran.springdiiocexceptionhandling.providers.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanFactory {

    private ObjectMapper mapper;

    public BeanFactory(ObjectMapper mapper) {
        this.mapper = mapper;
    }

//    @Bean
//    @Qualifier("topicProvider")
//    public StoreProvider getTopicStoreProvider() {
//        return new StoreProviderImpl(mapper);
//    }
//
//    @Bean
//    @Qualifier("userProvider")
//    public StoreProvider getUserStoreProvider() {
//        return new UserStoreProviderImpl(mapper);
//    }
//
//    @Bean
//    @Qualifier("userRolesProvider")
//    public StoreProvider getUserRoleStoreProvider() {
//        return new UserRolesStoreProviderImpl(mapper);
//    }
//
//    @Bean
//    @Qualifier("profileProviderImpl")
//    public StoreProvider getProfileProvider() {
//        return new ProfileProviderImpl(mapper);
//    }

}
