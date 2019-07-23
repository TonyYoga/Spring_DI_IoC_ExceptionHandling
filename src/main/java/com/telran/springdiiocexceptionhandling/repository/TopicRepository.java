package com.telran.springdiiocexceptionhandling.repository;

import com.telran.springdiiocexceptionhandling.repository.entity.CommentEntity;
import com.telran.springdiiocexceptionhandling.repository.entity.TopicEntity;
import com.telran.springdiiocexceptionhandling.repository.exception.RepositoryException;

public interface TopicRepository {

    int addTopic(TopicEntity topic);

    boolean removeTopic(int id);

    Iterable<TopicEntity> getAllTopics();

    int addComment(int topicId, CommentEntity comment);
    boolean removeComment(int topicId, int commentId);
    TopicEntity getTopicById(int topicId);
    CommentEntity getCommentById(int topicId, int commentId);

    default boolean updateComment(int topicId, CommentEntity comment){
        if(removeComment(topicId,comment.getId())){
            addComment(topicId,comment);
            return true;
        }
        throw new RepositoryException("TopicRepository error");
    }
}
