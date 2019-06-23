package com.telran.springdiiocexceptionhandling.controllers;

import com.telran.springdiiocexceptionhandling.controllers.dto.TopicFullDto;
import com.telran.springdiiocexceptionhandling.controllers.dto.TopicDto;
import com.telran.springdiiocexceptionhandling.controllers.dto.TopicResponseDto;

public interface TopicController {

    TopicResponseDto addTopic(TopicDto topicDto);

    Iterable<TopicFullDto> getAllTopics();

    void removeById(String id);
}
