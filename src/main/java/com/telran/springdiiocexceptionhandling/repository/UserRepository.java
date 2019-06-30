package com.telran.springdiiocexceptionhandling.repository;

import com.telran.springdiiocexceptionhandling.repository.entity.UserEntity;
import org.springframework.stereotype.Repository;

public interface UserRepository {

    boolean addUser(UserEntity userEntity);

    UserEntity getUserByEmail(String email);
}
