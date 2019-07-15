package com.telran.springdiiocexceptionhandling.repository;

import com.telran.springdiiocexceptionhandling.repository.entity.RolesEntity;
import com.telran.springdiiocexceptionhandling.repository.entity.UserEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface UserRepository {

    boolean addUser(UserEntity userEntity);

    UserEntity getUserByEmail(String email);

    RolesEntity.Role[] getRoles(String email);
}
