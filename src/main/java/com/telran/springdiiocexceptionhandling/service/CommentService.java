package com.telran.springdiiocexceptionhandling.service;

import com.telran.springdiiocexceptionhandling.config.rolesInterfaces.RoleAdmin;
import com.telran.springdiiocexceptionhandling.config.rolesInterfaces.RoleUser;
import com.telran.springdiiocexceptionhandling.controllers.dto.*;

public interface CommentService {

    @RoleUser
    void addComment(String topicId, CommentFullDto commentFullDto);

    @RoleAdmin
    @RoleUser
    void removeComment(RemoveCommentDto remCommentDto);

    @RoleAdmin
    @RoleUser
    void updateComment(UpdateCommentDto updCommentDto);
}
