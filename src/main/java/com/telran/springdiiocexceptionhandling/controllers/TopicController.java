package com.telran.springdiiocexceptionhandling.controllers;

import com.telran.springdiiocexceptionhandling.controllers.dto.SuccessResponseDto;
import com.telran.springdiiocexceptionhandling.controllers.dto.topic.TopicFullDto;
import com.telran.springdiiocexceptionhandling.controllers.dto.topic.TopicDto;
import com.telran.springdiiocexceptionhandling.controllers.dto.topic.TopicResponseDto;

public interface TopicController {

    TopicResponseDto addTopic(TopicDto topicDto);

    Iterable<TopicFullDto> getAllTopics();

    SuccessResponseDto removeById(String id);
}
