package com.telran.springdiiocexceptionhandling.service;

import com.telran.springdiiocexceptionhandling.controllers.dto.TopicFullDto;
import com.telran.springdiiocexceptionhandling.controllers.dto.TopicResponseDto;

public interface TopicService {

    void addTopic(TopicResponseDto topicResponseDto);

    Iterable<TopicFullDto> getAllTopics();

    void removeById(String id);
}
