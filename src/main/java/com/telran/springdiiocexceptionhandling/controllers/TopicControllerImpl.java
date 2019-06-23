package com.telran.springdiiocexceptionhandling.controllers;

import com.telran.springdiiocexceptionhandling.controllers.dto.TopicFullDto;
import com.telran.springdiiocexceptionhandling.controllers.dto.TopicDto;
import com.telran.springdiiocexceptionhandling.controllers.dto.TopicResponseDto;
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
    public TopicResponseDto addTopic(@RequestBody TopicDto topicDto) {
        return null;
    }

    @Override
    @GetMapping
    public Iterable<TopicFullDto> getAllTopics() {
        return null;
    }

    @Override
    @PostMapping("{id}")
    public void removeById(@PathVariable("id") String id) {

    }
}
