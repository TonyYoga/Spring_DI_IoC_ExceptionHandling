package com.telran.springdiiocexceptionhandling.service;

import com.telran.springdiiocexceptionhandling.controllers.dto.comment.CommentFullDto;
import com.telran.springdiiocexceptionhandling.controllers.dto.comment.RemoveCommentDto;
import com.telran.springdiiocexceptionhandling.controllers.dto.comment.UpdateCommentDto;
import com.telran.springdiiocexceptionhandling.repository.TopicRepository;
import com.telran.springdiiocexceptionhandling.repository.entity.CommentEntity;
import com.telran.springdiiocexceptionhandling.repository.exception.RepositoryException;
import com.telran.springdiiocexceptionhandling.service.exception.ServiceException;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.UUID;

@Service
public class CommentServiceImpl implements CommentService {
    private TopicRepository repository;

    public CommentServiceImpl(TopicRepository repository) {
        this.repository = repository;
    }

    @Override
    public void addComment(String topicId, CommentFullDto commentFullDto) {
        try {
            repository.addComment(Integer.valueOf(topicId), map(commentFullDto));
        } catch (RepositoryException ex) {
            throw new ServiceException(ex.getMessage(), ex);
        }
    }

    @Override
    public void removeComment(RemoveCommentDto remCommentDto) {
        try {
            repository.removeComment(Integer.valueOf(remCommentDto.getTopicId()), Integer.valueOf(remCommentDto.getCommentId()));
        } catch (RepositoryException ex) {
            throw new ServiceException(ex.getMessage(), ex);
        }
    }

    @Override
    public void updateComment(UpdateCommentDto updCommentDto) {
        try {
            repository.updateComment(Integer.valueOf(updCommentDto.getTopicId()), map(updCommentDto));
        } catch (RepositoryException ex) {
            throw new ServiceException(ex.getMessage(), ex);
        }
    }

    private CommentEntity map(CommentFullDto comment) {
        return new CommentEntity(Integer.valueOf(comment.getId()),
                comment.getOwner(),
                comment.getMessage(),
                Timestamp.valueOf(comment.getDate()));
    }
}
