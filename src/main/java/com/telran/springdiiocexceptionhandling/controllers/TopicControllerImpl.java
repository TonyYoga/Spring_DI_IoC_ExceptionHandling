package com.telran.springdiiocexceptionhandling.controllers;

import com.telran.springdiiocexceptionhandling.controllers.dto.*;
import com.telran.springdiiocexceptionhandling.controllers.dto.topic.TopicDto;
import com.telran.springdiiocexceptionhandling.controllers.dto.topic.TopicFullDto;
import com.telran.springdiiocexceptionhandling.controllers.dto.topic.TopicResponseDto;
import com.telran.springdiiocexceptionhandling.monitoring.TopicControllerMetric;
import com.telran.springdiiocexceptionhandling.service.OwnerValidator;
import com.telran.springdiiocexceptionhandling.service.TopicService;
import com.telran.springdiiocexceptionhandling.service.exception.ServiceException;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

@RestController
@RequestMapping("topic")
public class TopicControllerImpl implements TopicController {

    private TopicService topicService;

    private OwnerValidator validator;

    private TopicControllerMetric controllerMetric;

    public TopicControllerImpl(TopicService topicService, OwnerValidator validator, TopicControllerMetric controllerMetric) {
        this.topicService = topicService;
        this.validator = validator;
        this.controllerMetric = controllerMetric;
    }

    @ApiOperation(value = "Add new topic", response = TopicResponseDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = ""),
            @ApiResponse(code = 409, message = "Topic with id: {id} already exist!")
    }
    )
    @Override
    @PostMapping
    public TopicResponseDto addTopic(@RequestBody TopicDto topicDto) {
        String owner = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        TopicResponseDto res = TopicResponseDto.topicResponseBuilder()
                .owner(owner)
                .title(topicDto.getTitle())
                .content(topicDto.getContent())
                .date(LocalDateTime.now())
                .build();
        try {
            int id = topicService.addTopic(res);
            res.setId(String.valueOf(id));
            controllerMetric.handleAddTopic();
        } catch (ServiceException ex) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, ex.getMessage());
        }
        return res;
    }
    @ApiOperation(value = "Get All Topics", response = TopicFullDto[].class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = ""),
    }
    )
    @Override
    @GetMapping
    public Iterable<TopicFullDto> getAllTopics() {
        controllerMetric.handleGetAllTopic();
        return topicService.getAllTopics();
    }

    @ApiOperation(value = "Remove topic by Id", response = SuccessResponseDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Topic with id: {id} was removed"),
            @ApiResponse(code = 409, message = "Topic with id: {id} wasn't removed")
    }
    )

    @Override
    @DeleteMapping("{id}")
    public SuccessResponseDto removeById(@PathVariable("id") String id) {
        try {
            controllerMetric.handleRemoveTopic();
            validator.topicOwnerValidator(id, SecurityContextHolder.getContext().getAuthentication().getName());
            topicService.removeById(id);
            return new SuccessResponseDto("Topic with id: " + id + " was removed");
        } catch (ServiceException ex) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Topic with id: " + id + " wasn't removed");
        } catch (SecurityException ex) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, ex.getMessage());
        }


    }
}
