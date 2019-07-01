package com.telran.springdiiocexceptionhandling.service;

import com.telran.springdiiocexceptionhandling.repository.TopicRepository;
import com.telran.springdiiocexceptionhandling.repository.entity.CommentEntity;
import com.telran.springdiiocexceptionhandling.repository.exception.IllegalIdException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class OwnerValidatorImpl implements OwnerValidator {
    @Autowired
    TopicRepository repository;
    @Autowired
    TokenService tokenService;
    @Override
    public boolean topicOwnerValidator(String topicId, String token) {
        UserCredentials user = tokenService.decodeToken(token);
        if (!repository.getTopicById(UUID.fromString(topicId)).getAuthor().equals(user.getEmail())) {
            throw new SecurityException("Wrong topic owner" + user.getEmail());
        }
        return true;
    }

    @Override
    public boolean commentOwnerValidator(String topicId, String commentId, String token) {
        try {
            CommentEntity entity = repository.getCommentById(UUID.fromString(topicId), UUID.fromString(commentId));
            UserCredentials user = tokenService.decodeToken(token);
            if (entity.getAuthor() != user.getEmail()) {
                throw new SecurityException("Wrong comment owner" + user.getEmail());
            }
            return true;
        } catch (IllegalIdException ex) {
            throw new SecurityException(ex.getMessage(), ex);
        }
    }
}
