package com.telran.springdiiocexceptionhandling.service;

import com.telran.springdiiocexceptionhandling.controllers.dto.CommentFullDto;
import com.telran.springdiiocexceptionhandling.controllers.dto.RemoveCommentDto;
import com.telran.springdiiocexceptionhandling.controllers.dto.UpdateCommentDto;
import com.telran.springdiiocexceptionhandling.repository.TopicRepository;
import com.telran.springdiiocexceptionhandling.repository.entity.CommentEntity;
import com.telran.springdiiocexceptionhandling.repository.exception.RepositoryException;
import com.telran.springdiiocexceptionhandling.service.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    TopicRepository repository;

    @Autowired
    OwnerValidator ownerValidator;

    @Override
    public void addComment(CommentFullDto commentFullDto) {
        try {
            repository.addComment(UUID.fromString(commentFullDto.getId()), map(commentFullDto));
        } catch (RepositoryException ex) {
            throw new ServiceException(ex.getMessage(), ex);
        }
    }

    @Override
    public void removeComment(RemoveCommentDto remCommentDto) {
        String owner = SecurityContextHolder.getContext().getAuthentication().getName();
        try {
            if (ownerValidator.isAdminRole()) {
                repository.removeComment(UUID.fromString(remCommentDto.getTopicId()), UUID.fromString(remCommentDto.getCommentId()));
                return;
            }
            if (ownerValidator.commentOwnerValidator(remCommentDto.getTopicId(), remCommentDto.getCommentId(), owner)) {
                repository.removeComment(UUID.fromString(remCommentDto.getTopicId()), UUID.fromString(remCommentDto.getCommentId()));
            }
        } catch (RepositoryException ex) {
            throw new ServiceException(ex.getMessage(), ex);
        }
    }

    @Override
    public void updateComment(UpdateCommentDto updCommentDto) {
        String owner = SecurityContextHolder.getContext().getAuthentication().getName();
        try {
            if (ownerValidator.isAdminRole()) {
                repository.updateComment(UUID.fromString(updCommentDto.getTopicId()), map(updCommentDto));
                return;
            }
            if (ownerValidator.commentOwnerValidator(updCommentDto.getTopicId(), updCommentDto.getId(), owner)) {
                repository.updateComment(UUID.fromString(updCommentDto.getTopicId()), map(updCommentDto));            }
        } catch (RepositoryException ex) {
            throw new ServiceException(ex.getMessage(), ex);
        }
    }

    private CommentEntity map(CommentFullDto comment) {
        return new CommentEntity(UUID.fromString(comment.getId()),
                comment.getAuthor(),
                comment.getMessage(),
                comment.getDate());
    }
}
