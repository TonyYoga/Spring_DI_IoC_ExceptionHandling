package com.telran.springdiiocexceptionhandling.controllers;

import com.telran.springdiiocexceptionhandling.controllers.dto.*;

public interface CommentController {
    CommentFullDto addComment(AddCommentDto addCommentDto);

    void removeComment(RemoveCommentDto remCommentDto);

    void updateComment(UpdateCommentDto updCommentDto);
}
