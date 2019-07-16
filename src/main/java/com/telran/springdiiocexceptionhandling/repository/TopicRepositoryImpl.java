package com.telran.springdiiocexceptionhandling.repository;

import com.telran.springdiiocexceptionhandling.providers.StoreProvider;
import com.telran.springdiiocexceptionhandling.repository.entity.CommentEntity;
import com.telran.springdiiocexceptionhandling.repository.entity.TopicEntity;
import com.telran.springdiiocexceptionhandling.repository.exception.DuplicateIdException;
import com.telran.springdiiocexceptionhandling.repository.exception.IllegalIdException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Collectors;

@Repository
public class TopicRepositoryImpl implements TopicRepository {

    private StoreProvider<TopicEntity> provider;

    private final Lock readLock;
    private final Lock writeLock;
    private ConcurrentHashMap<UUID,TopicEntity> topics;
    private ConcurrentHashMap<UUID, CopyOnWriteArrayList<CommentEntity>> comments;

    public TopicRepositoryImpl(@Qualifier("topicProvider") StoreProvider<TopicEntity> provider) {
        this.provider = provider;
        ReadWriteLock lock = new ReentrantReadWriteLock();
        readLock = lock.readLock();
        writeLock = lock.writeLock();
        topics = new ConcurrentHashMap<>();
        comments = new ConcurrentHashMap<>();
    }

    @Override
    public boolean addTopic(TopicEntity topic) {
        if(topics.putIfAbsent(topic.getId(),topic) == null){
            return true;
        }
        throw new DuplicateIdException("Topic with id:" + topic.getId().toString() + " already exist");
    }

    @Override
    public boolean removeTopic(UUID id) {
        writeLock.lock();
        try {
            if (topics.remove(id) != null) {
                comments.remove(id);
                return true;
            }
            throw new IllegalIdException("Topic with id: " + id + " does not exist");
        }finally {
            writeLock.unlock();
        }
    }

    @Override
    public Iterable<TopicEntity> getAllTopics() {
        readLock.lock();
        try{
            List<TopicEntity> res = topics.values().stream().collect(Collectors.toUnmodifiableList());
            for(TopicEntity topic : res){
                List<CommentEntity> list = comments.getOrDefault(topic.getId(),new CopyOnWriteArrayList<>());
                topic.setComments(list);
            }
            return res;
        }finally {
            readLock.unlock();
        }

    }

    @Override
    public TopicEntity getTopicById(UUID topicId) {
        readLock.lock();
        try {
            TopicEntity topicEntity = topics.get(topicId);
            if (topicEntity == null) {
                throw new IllegalIdException("Topic with id: " + topicId + " does not exist");
            }
            return topicEntity;
        }finally {
            readLock.unlock();
        }
    }

    @Override
    public boolean addComment(UUID topicId, CommentEntity comment) {
        readLock.lock();
        try {
            if(topics.containsKey(topicId)){
                comments.computeIfAbsent(topicId,key->new CopyOnWriteArrayList<>()).add(comment);
                return true;
            }
            throw new IllegalIdException("Topic with id: " + topicId + " does not exist");
        }finally {
            readLock.unlock();
        }
    }

    @Override
    public boolean removeComment(UUID topicId, UUID commentId) {
        CopyOnWriteArrayList<CommentEntity> curr = comments.get(topicId);
        if(curr == null){
            throw new IllegalIdException("Topic with id: " + topicId + " does not exist");
        }
        if(curr.removeIf(commentEntity -> commentEntity.getId().equals(commentId))){
            return true;
        }
        throw new IllegalIdException("Comment with id: " + commentId + " does not exist");
    }

    @Override
    public CommentEntity getCommentById(UUID topicId, UUID commentId) {
        readLock.lock();
        try {
            CopyOnWriteArrayList<CommentEntity> curr = comments.get(topicId);
            if (curr == null) {
                throw new IllegalIdException("Topic with id: " + topicId + " does not exist");
            }
            CommentEntity res = curr.stream()
                    .filter(comment -> comment.getId().equals(commentId))
                    .findAny()
                    .orElseThrow(() -> new IllegalIdException("Comment with id: " + commentId + " does not exist"));
            return res;
        }finally {
            readLock.unlock();
        }
    }

    @PostConstruct
    private void loadData() {
        List<TopicEntity> entities = provider.loadData();
        for (TopicEntity entity : entities) {
            addTopic(entity);
            if (entity.getComments() != null && !entity.getComments().isEmpty()) {
                comments.putIfAbsent(entity.getId(), new CopyOnWriteArrayList<>(entity.getComments()));
            }
        }

    }

    @PreDestroy
    private void storeData() {
        provider.storeData(new ArrayList<>(topics.values()));
    }
}
