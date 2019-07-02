package com.telran.springdiiocexceptionhandling.controllers;

import com.telran.springdiiocexceptionhandling.controllers.dto.*;
import com.telran.springdiiocexceptionhandling.monitoring.CommentControllerMetric;
import com.telran.springdiiocexceptionhandling.repository.TopicRepository;
import com.telran.springdiiocexceptionhandling.repository.entity.CommentEntity;
import com.telran.springdiiocexceptionhandling.repository.exception.*;
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
import java.util.UUID;

@RestController
@RequestMapping("comment")
public class CommentControllerImpl implements CommentController {
    @Autowired
    TopicRepository repository;

    @Autowired
    TokenService validationService;

    @Autowired
    OwnerValidator ownerValidator;

    CommentControllerMetric commentControllerMetric;

    public CommentControllerImpl(CommentControllerMetric commentControllerMetric) {
        this.commentControllerMetric = commentControllerMetric;
    }

    @ApiOperation(value = "Add new comment", response = CommentFullDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = ""),
            @ApiResponse(code = 409, message = "Topic with id: {id} does not exist")
    }
    )
    @Override
    @PostMapping
    public CommentFullDto addComment(@RequestBody AddCommentDto addCommentDto, @RequestHeader("Authorization") String token) {
        UserCredentials user = validationService.decodeToken(token);
        try {
            CommentFullDto commentFullDto = CommentFullDto.fullCommentBuilder()
                    .id(UUID.randomUUID().toString())
                    .author(user.getEmail())
                    .message(addCommentDto.getMessage())
                    .date(LocalDateTime.now())
                    .build();
            repository.addComment(UUID.fromString(addCommentDto.getTopicId()), map(commentFullDto));
            commentControllerMetric.handleAddComment();
            return commentFullDto;
        } catch (IllegalIdException ex) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, ex.getMessage());
        }
    }

    @ApiOperation(value = "Remove comment", response = SuccessResponseDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Comment with id: {id} was removed"),
            @ApiResponse(code = 409, message = "Topic with id: {id} does not exist"),
            @ApiResponse(code = 409, message = "Comment with id: {id} does not exist")
    }
    )
    @Override
    @DeleteMapping
    public SuccessResponseDto removeComment(@RequestBody RemoveCommentDto remCommentDto, @RequestHeader("Authorization") String token) {
        try {
            ownerValidator.commentOwnerValidator(remCommentDto.getTopicId(), remCommentDto.getCommentId(), token);
            repository.removeComment(UUID.fromString(remCommentDto.getTopicId()), UUID.fromString(remCommentDto.getCommentId()));
            commentControllerMetric.handleRemoveComment();
            return new SuccessResponseDto("Comment was " + remCommentDto.getCommentId() + "  successful removed");
        } catch (IllegalIdException ex) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, ex.getMessage());
        }

    }

    @ApiOperation(value = "Update comment", response = SuccessResponseDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Comment with id: {id} was updated"),
            @ApiResponse(code = 409, message = "Topic with id: {id} does not exist"),
            @ApiResponse(code = 409, message = "Comment with id: {id} does not exist")
    }
    )
    @Override
    @PutMapping
    public SuccessResponseDto updateComment(@RequestBody UpdateCommentDto updCommentDto, @RequestHeader("Authorization") String token) {
        try {
            ownerValidator.commentOwnerValidator(updCommentDto.getTopicId(), updCommentDto.getId(), token);
            repository.updateComment(UUID.fromString(updCommentDto.getTopicId()), map(updCommentDto));
            commentControllerMetric.handleUpdateComment();
            return new SuccessResponseDto("Comment with id: "+ updCommentDto.getTopicId() + " was updated");
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
