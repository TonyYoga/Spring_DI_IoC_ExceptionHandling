package com.telran.springdiiocexceptionhandling.controllers;

import com.telran.springdiiocexceptionhandling.controllers.dto.*;

public interface CommentController {
    CommentFullDto addComment(AddCommentDto addCommentDto);

    SuccessResponseDto removeComment(RemoveCommentDto remCommentDto);

    SuccessResponseDto updateComment(UpdateCommentDto updCommentDto);
}
