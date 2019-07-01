package com.telran.springdiiocexceptionhandling.controllers;

import com.telran.springdiiocexceptionhandling.controllers.dto.*;

public interface CommentController {
    CommentFullDto addComment(AddCommentDto addCommentDto, String token);

    SuccessResponseDto removeComment(RemoveCommentDto remCommentDto, String token);

    SuccessResponseDto updateComment(UpdateCommentDto updCommentDto, String token);
}
