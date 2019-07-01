package com.telran.springdiiocexceptionhandling.service;

public interface OwnerValidator {

    boolean topicOwnerValidator(String topicId, String token);
    boolean commentOwnerValidator(String topicId, String commentId, String token);
}
