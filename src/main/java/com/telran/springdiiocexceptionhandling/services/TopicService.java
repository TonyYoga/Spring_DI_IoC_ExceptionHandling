package com.telran.springdiiocexceptionhandling.services;

import com.telran.springdiiocexceptionhandling.services.BusinesObjects.TopicBusinessObj;

import java.util.UUID;

public interface TopicService {
    TopicBusinessObj addTopic(TopicBusinessObj topic);

    Iterable<TopicBusinessObj> getAllTopics();

    boolean removeTopicById(UUID uuid);
}
