package com.telran.springdiiocexceptionhandling.controllers;

import com.telran.springdiiocexceptionhandling.controllers.dto.TopicFullResponceDto;
import com.telran.springdiiocexceptionhandling.controllers.dto.TopicRequestDto;
import com.telran.springdiiocexceptionhandling.controllers.dto.TopicResponceDto;
import com.telran.springdiiocexceptionhandling.services.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("topic")
public class TopicControllerImpl implements TopicController {
    @Autowired
    TopicService topicService;

    @Override
    @PostMapping
    public TopicResponceDto addTopic(@RequestBody TopicRequestDto topicDto) {
        return null;
    }

    @Override
    @GetMapping
    public Iterable<TopicFullResponceDto> getAllTopics() {
        return null;
    }

    @Override
    @PostMapping("{id}")
    public void removeById(@PathVariable("id") String id) {

    }
}
