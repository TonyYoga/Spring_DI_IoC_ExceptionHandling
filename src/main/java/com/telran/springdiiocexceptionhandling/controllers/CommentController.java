package com.telran.springdiiocexceptionhandling.controllers;

import com.telran.springdiiocexceptionhandling.controllers.dto.CommentRequestDto;
import com.telran.springdiiocexceptionhandling.controllers.dto.CommentResponceDto;

public interface CommentController {
    CommentResponceDto addComment(String id, CommentRequestDto commentDto);

    void removeComment(String id);

    void updateComment(CommentResponceDto commentDto);
}
