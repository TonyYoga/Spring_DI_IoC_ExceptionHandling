package com.telran.springdiiocexceptionhandling.controllers;

import com.telran.springdiiocexceptionhandling.controllers.dto.*;
import com.telran.springdiiocexceptionhandling.repository.TopicRepository;
import com.telran.springdiiocexceptionhandling.repository.entity.CommentEntity;
import com.telran.springdiiocexceptionhandling.repository.exception.IllegalIdException;
import com.telran.springdiiocexceptionhandling.repository.exception.RepositoryException;
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
            repository.addComment(UUID.fromString(addCommentDto.getTopicId()), map(commentFullDto));
            return commentFullDto;
//        } catch (IllegalArgumentException ex) {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id format error!");
        } catch (IllegalIdException ex) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, ex.getMessage());
        }
    }

    @Override
    @DeleteMapping
    public SuccessResponseDto removeComment(@RequestBody RemoveCommentDto remCommentDto) {
        try {
            repository.removeComment(UUID.fromString(remCommentDto.getTopicId()), UUID.fromString(remCommentDto.getCommentId()));
            return new SuccessResponseDto("Comment was " + remCommentDto.getCommentId() + "  successful removed");
//        } catch (IllegalArgumentException ex) {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id format error!");
        } catch (IllegalIdException ex) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, ex.getMessage());
        }

    }

    @Override
    @PutMapping
    public SuccessResponseDto updateComment(@RequestBody UpdateCommentDto updCommentDto) {
        try {
            repository.updateComment(UUID.fromString(updCommentDto.getTopicId()), map(updCommentDto));
            return new SuccessResponseDto("Comment with id: "+ updCommentDto.getTopicId() + " was updated");

//        } catch (IllegalArgumentException ex) {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id format error!");
        } catch (RepositoryException ex) {
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
