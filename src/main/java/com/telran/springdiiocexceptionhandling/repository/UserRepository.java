package com.telran.springdiiocexceptionhandling.repository;

import com.telran.springdiiocexceptionhandling.repository.entity.UserEntity;

public interface UserRepository {

    boolean addUser(UserEntity userEntity);

    UserEntity getUserByEmail(String email);

    String[] getRoles(String email);
}
