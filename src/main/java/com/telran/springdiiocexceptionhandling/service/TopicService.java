package com.telran.springdiiocexceptionhandling.service;

import com.telran.springdiiocexceptionhandling.controllers.dto.topic.TopicFullDto;
import com.telran.springdiiocexceptionhandling.controllers.dto.topic.TopicResponseDto;

public interface TopicService {

    void addTopic(TopicResponseDto topicResponseDto);

    Iterable<TopicFullDto> getAllTopics();

    void removeById(String id);
}
