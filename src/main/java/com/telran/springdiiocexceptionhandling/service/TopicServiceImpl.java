package com.telran.springdiiocexceptionhandling.service;

import com.telran.springdiiocexceptionhandling.controllers.dto.comment.CommentFullDto;
import com.telran.springdiiocexceptionhandling.controllers.dto.topic.TopicFullDto;
import com.telran.springdiiocexceptionhandling.controllers.dto.topic.TopicResponseDto;
import com.telran.springdiiocexceptionhandling.repository.ProfileRepository;
import com.telran.springdiiocexceptionhandling.repository.TopicRepository;
import com.telran.springdiiocexceptionhandling.repository.entity.CommentEntity;
import com.telran.springdiiocexceptionhandling.repository.entity.ProfileEntity;
import com.telran.springdiiocexceptionhandling.repository.entity.TopicEntity;
import com.telran.springdiiocexceptionhandling.repository.exception.IllegalIdException;
import com.telran.springdiiocexceptionhandling.repository.exception.RepositoryException;
import com.telran.springdiiocexceptionhandling.service.exception.ServiceException;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class TopicServiceImpl implements TopicService {
    private TopicRepository topicRepository;
    private ProfileRepository profileRepository;

    public TopicServiceImpl(TopicRepository topicRepository, ProfileRepository profileRepository) {
        this.topicRepository = topicRepository;
        this.profileRepository = profileRepository;
    }

    @Override
    public void addTopic(TopicResponseDto topicResponseDto) {
        try {
            topicRepository.addTopic(map(topicResponseDto));
        } catch (RepositoryException ex) {
            throw new ServiceException(ex.getMessage(),ex);
        }
    }

    @Override
    public Iterable<TopicFullDto> getAllTopics() {
        return StreamSupport.stream(topicRepository.getAllTopics().spliterator(), false)
                .map(this::map)
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public void removeById(String id) {
        try {
            topicRepository.removeTopic(Integer.valueOf(id));
        } catch (RepositoryException ex) {
            throw new ServiceException(ex.getMessage(), ex);
        }

    }

    private TopicEntity map(TopicResponseDto dto) {
        return new TopicEntity(
                Integer.valueOf(dto.getId()),
                dto.getOwner(),
                dto.getTitle(),
                dto.getContent(),
                Timestamp.valueOf(dto.getDate()),
                null
        );
    }

    private TopicFullDto map(TopicEntity topicEntity){
        ProfileEntity userProfile;
        try {
            userProfile = profileRepository.getProfileByOwner(topicEntity.getOwner());
        } catch (IllegalIdException ex) {
            userProfile = null;
        }
        String userName = userProfile != null ?
                String.format("%s %s", userProfile.getFirstName(), userProfile.getLastName()) : topicEntity.getOwner();

        return TopicFullDto.fullTopicBuilder()
                .id(topicEntity.getId().toString())
                .owner(topicEntity.getOwner())
                .firstLastName(userName)
                .title(topicEntity.getTitle())
                .content(topicEntity.getContent())
                .comments(map(topicEntity.getComments()))
                .date(topicEntity.getDate().toLocalDateTime())
                .build();
    }

    private List<CommentFullDto> map(List<CommentEntity> list){
        return list.stream()
                .map(this::map)
                .collect(Collectors.toList());
    }

    private CommentFullDto map(CommentEntity entity){
        ProfileEntity userProfile;
        try {
            userProfile = profileRepository.getProfileByOwner(entity.getOwner());
        } catch (IllegalIdException ex) {
            userProfile = null;
        }
        String userName = userProfile != null ?
                String.format("%s %s", userProfile.getFirstName(), userProfile.getLastName()) : entity.getOwner();

        return CommentFullDto.fullCommentBuilder()
                .id(entity.getId().toString())
                .date(entity.getDate().toLocalDateTime())
                .author(userName)
                .owner(entity.getOwner())
                .message(entity.getMessage())
                .build();
    }
}
