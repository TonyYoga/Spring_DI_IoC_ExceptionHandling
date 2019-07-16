package com.telran.springdiiocexceptionhandling.service;

import com.telran.springdiiocexceptionhandling.controllers.dto.*;

public interface CommentService {

    void addComment(String topicId, CommentFullDto commentFullDto);

    void removeComment(RemoveCommentDto remCommentDto);

    void updateComment(UpdateCommentDto updCommentDto);
}
