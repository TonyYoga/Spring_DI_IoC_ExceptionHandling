package com.telran.springdiiocexceptionhandling.service;

import java.security.Principal;

public interface OwnerValidator {

    boolean topicOwnerValidator(String topicId, String ownerEmail);
    boolean commentOwnerValidator(String topicId, String commentId, String ownerEmail);
    boolean isAdminRole();
}
