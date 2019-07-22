package com.telran.springdiiocexceptionhandling.controllers;

import com.telran.springdiiocexceptionhandling.controllers.dto.*;
import com.telran.springdiiocexceptionhandling.controllers.dto.comment.AddCommentDto;
import com.telran.springdiiocexceptionhandling.controllers.dto.comment.CommentFullDto;
import com.telran.springdiiocexceptionhandling.controllers.dto.comment.RemoveCommentDto;
import com.telran.springdiiocexceptionhandling.controllers.dto.comment.UpdateCommentDto;

public interface CommentController {
    CommentFullDto addComment(AddCommentDto addCommentDto);

    SuccessResponseDto removeComment(RemoveCommentDto remCommentDto);

    SuccessResponseDto updateComment(UpdateCommentDto updCommentDto);
}
