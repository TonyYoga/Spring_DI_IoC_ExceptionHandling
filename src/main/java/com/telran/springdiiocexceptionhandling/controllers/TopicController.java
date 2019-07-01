package com.telran.springdiiocexceptionhandling.controllers;

import com.telran.springdiiocexceptionhandling.controllers.dto.SuccessResponseDto;
import com.telran.springdiiocexceptionhandling.controllers.dto.TopicFullDto;
import com.telran.springdiiocexceptionhandling.controllers.dto.TopicDto;
import com.telran.springdiiocexceptionhandling.controllers.dto.TopicResponseDto;

public interface TopicController {

    TopicResponseDto addTopic(TopicDto topicDto, String token);

    Iterable<TopicFullDto> getAllTopics();

    SuccessResponseDto removeById(String id, String token);
}
