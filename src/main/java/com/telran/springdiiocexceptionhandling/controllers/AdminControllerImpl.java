package com.telran.springdiiocexceptionhandling.controllers;

import com.telran.springdiiocexceptionhandling.controllers.dto.RemoveCommentDto;
import com.telran.springdiiocexceptionhandling.controllers.dto.SuccessResponseDto;
import com.telran.springdiiocexceptionhandling.service.CommentService;
import com.telran.springdiiocexceptionhandling.service.TopicService;
import com.telran.springdiiocexceptionhandling.service.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("admin")
public class AdminControllerImpl implements AdminController {

    @Autowired
    TopicService topicService;

    @Autowired
    CommentService commentService;

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
