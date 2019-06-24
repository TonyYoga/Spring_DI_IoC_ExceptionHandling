package com.telran.springdiiocexceptionhandling.controllers;

import com.telran.springdiiocexceptionhandling.controllers.dto.AddCommentDto;
import com.telran.springdiiocexceptionhandling.controllers.dto.CommentFullDto;
import com.telran.springdiiocexceptionhandling.controllers.dto.RemoveCommentDto;
import com.telran.springdiiocexceptionhandling.controllers.dto.UpdateCommentDto;
import com.telran.springdiiocexceptionhandling.repository.TopicRepository;
import com.telran.springdiiocexceptionhandling.repository.entity.CommentEntity;
import com.telran.springdiiocexceptionhandling.repository.exception.IllegalIdException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequestMapping("comment")
public class CommentControllerImpl implements CommentController {
    @Autowired
    TopicRepository repository;

    @Override
    @PostMapping
    public CommentFullDto addComment(@RequestBody AddCommentDto addCommentDto) {
        try {
            CommentFullDto commentFullDto = CommentFullDto.fullCommentBuilder()
                    .id(UUID.randomUUID().toString())
                    .author(addCommentDto.getAuthor())
                    .message(addCommentDto.getMessage())
                    .date(LocalDateTime.now())
                    .build();
            System.out.println(commentFullDto.toString());
            repository.addComment(UUID.fromString(addCommentDto.getTopicId()), map(commentFullDto));
            return commentFullDto;
        } catch (IllegalArgumentException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id format error!");
        } catch (IllegalIdException ex) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Topic with id: " + addCommentDto.getTopicId() + " wasn't removed");
        }
    }

    @Override
    @DeleteMapping
    public void removeComment(@RequestBody RemoveCommentDto remCommentDto) {
        try {
            repository.removeComment(UUID.fromString(remCommentDto.getTopicId()), UUID.fromString(remCommentDto.getCommentId()));
        } catch (IllegalArgumentException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id format error!");
        } catch (IllegalIdException ex) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, ex.getMessage());
        }

    }

    @Override
    @PutMapping
    public void updateComment(@RequestBody UpdateCommentDto updCommentDto) {
        try {
            repository.updateComment(UUID.fromString(updCommentDto.getTopicId()), map(updCommentDto));
            throw new ResponseStatusException(HttpStatus.OK, "Comment with id: "+ updCommentDto.getTopicId() + " was updated");

        } catch (IllegalArgumentException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id format error!");
        } catch (IllegalIdException ex) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, ex.getMessage());
        }

    }

    private CommentEntity map(CommentFullDto comment) {
        return new CommentEntity(UUID.fromString(comment.getId()),
                comment.getAuthor(),
                comment.getMessage(),
                comment.getDate());
    }
}
