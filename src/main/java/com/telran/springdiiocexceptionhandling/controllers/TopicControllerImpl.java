package com.telran.springdiiocexceptionhandling.controllers;

import com.telran.springdiiocexceptionhandling.controllers.dto.*;
import com.telran.springdiiocexceptionhandling.monitoring.TopicControllerMetric;
import com.telran.springdiiocexceptionhandling.repository.TopicRepository;
import com.telran.springdiiocexceptionhandling.repository.entity.CommentEntity;
import com.telran.springdiiocexceptionhandling.repository.entity.TopicEntity;
import com.telran.springdiiocexceptionhandling.repository.exception.DuplicateIdException;
import com.telran.springdiiocexceptionhandling.repository.exception.IllegalIdException;
import com.telran.springdiiocexceptionhandling.service.OwnerValidator;
import com.telran.springdiiocexceptionhandling.service.TokenService;
import com.telran.springdiiocexceptionhandling.service.UserCredentials;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("topic")
public class TopicControllerImpl implements TopicController {

    @Autowired
    TopicRepository repository;

    @Autowired
    private TokenService validationService;

    @Autowired
    OwnerValidator ownerValidator;

    private TopicControllerMetric controllerMetric;

    public TopicControllerImpl(TopicControllerMetric controllerMetric) {
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
    public TopicResponseDto addTopic(@RequestBody TopicDto topicDto, @RequestHeader("Authorization") String token) {
        UserCredentials user = validationService.decodeToken(token);
        TopicResponseDto res = TopicResponseDto.topicResponseBuilder()
                .id(UUID.randomUUID().toString())
                .author(user.getEmail()) //TODO - need authorName
                .title(topicDto.getTitle())
                .content(topicDto.getContent())
                .date(LocalDateTime.now())
                .build();
        try {
            repository.addTopic(map(res));
            controllerMetric.handleAddTopic();
        } catch (DuplicateIdException ex) {
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
        return StreamSupport.stream(repository.getAllTopics().spliterator(), false)
                .map(this::map)
                .collect(Collectors.toUnmodifiableList());
    }

    @ApiOperation(value = "Remove topic by Id", response = SuccessResponseDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Topic with id: {id} was removed"),
            @ApiResponse(code = 409, message = "Topic with id: {id} wasn't removed")
    }
    )

    @Override
    @DeleteMapping("{id}")
    public SuccessResponseDto removeById(@PathVariable("id") String id, @RequestHeader("Authorization") String token) {
        try {
            UserCredentials user = validationService.decodeToken(token);
            ownerValidator.topicOwnerValidator(id, user.getEmail());
            controllerMetric.handleRemoveTopic();
            repository.removeTopic(UUID.fromString(id));
            return new SuccessResponseDto("Topic with id: " + id + " was removed");
        } catch (IllegalIdException ex) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Topic with id: " + id + " wasn't removed");
        }


    }

    private TopicEntity map(TopicResponseDto dto) {
        return new TopicEntity(
                UUID.fromString(dto.getId()),
                dto.getAuthor(),
                dto.getTitle(),
                dto.getContent(),
                dto.getDate(),
                null
        );
    }

    private TopicFullDto map(TopicEntity topicEntity){
        return TopicFullDto.fullTopicBuilder()
                .id(topicEntity.getId().toString())
                .author(topicEntity.getAuthor())
                .title(topicEntity.getTitle())
                .content(topicEntity.getContent())
                .comments(map(topicEntity.getComments()))
                .date(topicEntity.getDate())
                .build();
    }

    private List<CommentFullDto> map(List<CommentEntity> list){
        return list.stream()
                .map(this::map)
                .collect(Collectors.toList());
    }

    private CommentFullDto map(CommentEntity entity){
        return CommentFullDto.fullCommentBuilder()
                .id(entity.getId().toString())
                .date(entity.getDate())
                .author(entity.getAuthor())
                .message(entity.getMessage())
                .build();
    }
}
