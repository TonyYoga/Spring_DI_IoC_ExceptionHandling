package com.telran.springdiiocexceptionhandling.controllers;

import com.telran.springdiiocexceptionhandling.controllers.dto.RemoveCommentDto;
import com.telran.springdiiocexceptionhandling.controllers.dto.SuccessResponseDto;
import com.telran.springdiiocexceptionhandling.service.CommentService;
import com.telran.springdiiocexceptionhandling.service.TopicService;
import com.telran.springdiiocexceptionhandling.service.exception.ServiceException;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("admin")
public class AdminControllerImpl implements AdminController {

    private TopicService topicService;
    private CommentService commentService;

    public AdminControllerImpl(TopicService topicService, CommentService commentService) {
        this.topicService = topicService;
        this.commentService = commentService;
    }

    @ApiOperation(value = "Remove topic by Id", response = SuccessResponseDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Topic with id: {id} was removed"),
            @ApiResponse(code = 409, message = "Topic with id: {id} wasn't removed")
    }
    )
    @DeleteMapping("topic/{id}")
    @Override
    public SuccessResponseDto removeById(@PathVariable("id") String id) {
        try {
            topicService.removeById(id);
            return new SuccessResponseDto("Topic with id: " + id + " was removed by admin");
        } catch (ServiceException ex) {
            throw  new ResponseStatusException(HttpStatus.CONFLICT, ex.getMessage());
        }

    }
    @ApiOperation(value = "Remove comment", response = SuccessResponseDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Comment with id: {id} was removed"),
            @ApiResponse(code = 409, message = "Topic with id: {id} does not exist"),
            @ApiResponse(code = 409, message = "Comment with id: {id} does not exist")
    }
    )
    @DeleteMapping("comment")
    @Override
    public SuccessResponseDto removeComment(@RequestBody RemoveCommentDto remCommentDto) {
        try {
            commentService.removeComment(remCommentDto);
            return new SuccessResponseDto("Comment with id: " + remCommentDto.getCommentId() + " was removed by admin");
        } catch (ServiceException ex) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, ex.getMessage());
        }

    }
}
