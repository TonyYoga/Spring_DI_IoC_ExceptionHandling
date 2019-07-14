package com.telran.springdiiocexceptionhandling.config;

import com.telran.springdiiocexceptionhandling.providers.StoreProvider;
import com.telran.springdiiocexceptionhandling.providers.StoreProviderImpl;
import com.telran.springdiiocexceptionhandling.providers.UserRolesStoreProviderImpl;
import com.telran.springdiiocexceptionhandling.providers.UserStoreProviderImpl;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanFactory {

    @Bean
    @Qualifier("topicProvider")
    public StoreProvider getTopicStoreProvider() {
        return new StoreProviderImpl();
    }

    @Bean
    @Qualifier("userProvider")
    public StoreProvider getUserStoreProvider() {
        return new UserStoreProviderImpl();
    }

    @Bean
    @Qualifier("userRolesProvider")
    public StoreProvider getUserRoleStoreProvider() {
        return new UserRolesStoreProviderImpl();
    }

}
