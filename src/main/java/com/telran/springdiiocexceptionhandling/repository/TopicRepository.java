package com.telran.springdiiocexceptionhandling.repository;

import com.telran.springdiiocexceptionhandling.repository.entity.CommentEntity;
import com.telran.springdiiocexceptionhandling.repository.entity.TopicEntity;
import com.telran.springdiiocexceptionhandling.repository.exception.RepositoryException;

import java.util.UUID;

public interface TopicRepository {

    boolean addTopic(TopicEntity topic);

    boolean removeTopic(UUID id);

    Iterable<TopicEntity> getAllTopics();

    boolean addComment(UUID topicId, CommentEntity comment);
    boolean removeComment(UUID topicId, UUID commentId);

    default boolean updateComment(UUID topicId, CommentEntity comment){
        if(removeComment(topicId,comment.getId())){
            return addComment(topicId,comment);
        }
        throw new RepositoryException("TopicRepository error");
    }
}
