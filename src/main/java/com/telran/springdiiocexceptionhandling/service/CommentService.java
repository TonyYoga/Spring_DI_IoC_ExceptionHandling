package com.telran.springdiiocexceptionhandling.service;

import com.telran.springdiiocexceptionhandling.controllers.dto.*;

public interface CommentService {

    CommentFullDto addComment(AddCommentDto addCommentDto);

    void removeComment(RemoveCommentDto remCommentDto);

    void updateComment(UpdateCommentDto updCommentDto);
}
