package com.telran.springdiiocexceptionhandling.controllers;

import com.telran.springdiiocexceptionhandling.controllers.dto.CommentFullDto;
import com.telran.springdiiocexceptionhandling.controllers.dto.TopicFullDto;
import com.telran.springdiiocexceptionhandling.controllers.dto.TopicDto;
import com.telran.springdiiocexceptionhandling.controllers.dto.TopicResponseDto;
import com.telran.springdiiocexceptionhandling.repository.TopicRepository;
import com.telran.springdiiocexceptionhandling.repository.entity.CommentEntity;
import com.telran.springdiiocexceptionhandling.repository.entity.TopicEntity;
import com.telran.springdiiocexceptionhandling.repository.exception.DuplicateIdException;
import com.telran.springdiiocexceptionhandling.repository.exception.IllegalIdException;
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

    @Override
    @PostMapping
    public TopicResponseDto addTopic(@RequestBody TopicDto topicDto) {
        TopicResponseDto res = TopicResponseDto.topicResponseBuilder()
                .id(UUID.randomUUID().toString())
                .author(topicDto.getAuthor())
                .title(topicDto.getTitle())
                .content(topicDto.getContent())
                .date(LocalDateTime.now())
                .build();
        try {
            repository.addTopic(map(res));
        } catch (DuplicateIdException ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        }
        return res;
    }

    @Override
    @GetMapping
    public Iterable<TopicFullDto> getAllTopics() {
        List<TopicFullDto> res = StreamSupport.stream(repository.getAllTopics().spliterator(), false)
                .map(this::map)
                .collect(Collectors.toUnmodifiableList());

        return res;
    }

    @Override
    @DeleteMapping("{id}")
    public void removeById(@PathVariable("id") String id) {
        try {
            if (repository.removeTopic(UUID.fromString(id))) {
                throw new ResponseStatusException(HttpStatus.OK, "Topic with id: " + id + " was removed");
            }
        } catch (IllegalArgumentException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id format error!");
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
//        System.out.println(topicEntity.toString());
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
