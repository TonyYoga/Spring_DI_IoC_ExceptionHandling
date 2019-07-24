package com.telran.springdiiocexceptionhandling.service;

import com.telran.springdiiocexceptionhandling.controllers.dto.comment.CommentFullDto;
import com.telran.springdiiocexceptionhandling.controllers.dto.comment.RemoveCommentDto;
import com.telran.springdiiocexceptionhandling.controllers.dto.comment.UpdateCommentDto;

public interface CommentService {

    int addComment(String topicId, CommentFullDto commentFullDto);

    void removeComment(RemoveCommentDto remCommentDto);

    void updateComment(UpdateCommentDto updCommentDto);
}
