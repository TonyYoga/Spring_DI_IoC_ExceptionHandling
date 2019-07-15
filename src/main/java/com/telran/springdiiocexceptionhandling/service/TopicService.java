package com.telran.springdiiocexceptionhandling.service;

import com.telran.springdiiocexceptionhandling.config.rolesInterfaces.RoleAdmin;
import com.telran.springdiiocexceptionhandling.config.rolesInterfaces.RoleUser;
import com.telran.springdiiocexceptionhandling.controllers.dto.TopicFullDto;
import com.telran.springdiiocexceptionhandling.controllers.dto.TopicResponseDto;

public interface TopicService {

    @RoleAdmin
    @RoleUser
    void addTopic(TopicResponseDto topicResponseDto);

    Iterable<TopicFullDto> getAllTopics();

    @RoleAdmin
    @RoleUser
    void removeById(String id);
}
