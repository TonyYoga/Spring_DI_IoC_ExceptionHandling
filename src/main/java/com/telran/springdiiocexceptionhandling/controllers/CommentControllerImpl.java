package com.telran.springdiiocexceptionhandling.controllers;

import com.telran.springdiiocexceptionhandling.controllers.dto.*;
import com.telran.springdiiocexceptionhandling.monitoring.CommentControllerMetric;
import com.telran.springdiiocexceptionhandling.service.CommentService;
import com.telran.springdiiocexceptionhandling.service.exception.ServiceException;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequestMapping("comment")
public class CommentControllerImpl implements CommentController {

    @Autowired
    CommentService commentService;

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
    public CommentFullDto addComment(@RequestBody AddCommentDto addCommentDto) {
        String owner = SecurityContextHolder.getContext().getAuthentication().getName();
        try {
            CommentFullDto commentFullDto = CommentFullDto.fullCommentBuilder()
                    .id(UUID.randomUUID().toString())
                    .author(owner)
                    .message(addCommentDto.getMessage())
                    .date(LocalDateTime.now())
                    .build();
            commentService.addComment(addCommentDto.getTopicId(), commentFullDto);
            commentControllerMetric.handleAddComment();
            return commentFullDto;
        } catch (ServiceException ex) {
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
    public SuccessResponseDto removeComment(@RequestBody RemoveCommentDto remCommentDto) {
        try {
            commentService.removeComment(remCommentDto);
            commentControllerMetric.handleRemoveComment();
            return new SuccessResponseDto("Comment was " + remCommentDto.getCommentId() + "  successful removed");
        } catch (ServiceException ex) {
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
    public SuccessResponseDto updateComment(@RequestBody UpdateCommentDto updCommentDto) {
        try {
            commentService.updateComment(updCommentDto);
            commentControllerMetric.handleUpdateComment();
            return new SuccessResponseDto("Comment with id: "+ updCommentDto.getTopicId() + " was updated");
        } catch (ServiceException ex) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, ex.getMessage());
        }

    }
}
