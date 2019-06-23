package com.telran.springdiiocexceptionhandling.controllers;

import com.telran.springdiiocexceptionhandling.controllers.dto.TopicFullResponceDto;
import com.telran.springdiiocexceptionhandling.controllers.dto.TopicRequestDto;
import com.telran.springdiiocexceptionhandling.controllers.dto.TopicResponceDto;

public interface TopicController {

    TopicResponceDto addTopic(TopicRequestDto topicDto);

    Iterable<TopicFullResponceDto> getAllTopics();

    void removeById(String id);
}
