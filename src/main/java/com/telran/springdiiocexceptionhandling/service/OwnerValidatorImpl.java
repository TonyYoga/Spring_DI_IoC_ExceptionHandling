package com.telran.springdiiocexceptionhandling.service;

import com.telran.springdiiocexceptionhandling.repository.TopicRepository;
import com.telran.springdiiocexceptionhandling.repository.entity.CommentEntity;
import com.telran.springdiiocexceptionhandling.repository.entity.RolesEntity;
import com.telran.springdiiocexceptionhandling.repository.exception.IllegalIdException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class OwnerValidatorImpl implements OwnerValidator {
    @Autowired
    TopicRepository repository;

    @Override
    public boolean topicOwnerValidator(String topicId, String owner) {

        if (!repository.getTopicById(UUID.fromString(topicId)).getAuthor().equals(owner)) {
            throw new SecurityException("Wrong topic owner" + owner);
        }
        return true;
    }

    @Override
    public boolean commentOwnerValidator(String topicId, String commentId, String owner) {
        try {
            CommentEntity entity = repository.getCommentById(UUID.fromString(topicId), UUID.fromString(commentId));
            if (entity.getAuthor() != owner) {
                throw new SecurityException("Wrong comment owner" + owner);
            }
            return true;
        } catch (IllegalIdException ex) {
            throw new SecurityException(ex.getMessage(), ex);
        }
    }

    @Override
    public boolean isAdminRole() {
        //TODO check bad code style!
//        SecurityContextHolder.getContext().getAuthentication().getAuthorities().forEach(System.out::println);
//        System.out.println("ROLE_" + RolesEntity.Role.ADMIN.name());
        boolean res = SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                .map(a -> ((GrantedAuthority) a).getAuthority())
                .collect(Collectors.toList()).contains("ROLE_" + RolesEntity.Role.ADMIN.name());

//        boolean res = SecurityContextHolder.getContext().getAuthentication().getAuthorities().contains("ROLE_" + RolesEntity.Role.ADMIN.name());
        return res;

    }
}
