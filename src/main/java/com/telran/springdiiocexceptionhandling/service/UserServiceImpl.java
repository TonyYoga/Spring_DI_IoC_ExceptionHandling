package com.telran.springdiiocexceptionhandling.service;

import com.telran.springdiiocexceptionhandling.config.SecurityConfig;
import com.telran.springdiiocexceptionhandling.controllers.dto.UserDto;
import com.telran.springdiiocexceptionhandling.repository.UserRepository;
import com.telran.springdiiocexceptionhandling.repository.entity.RolesEntity;
import com.telran.springdiiocexceptionhandling.repository.entity.UserEntity;
import com.telran.springdiiocexceptionhandling.repository.exception.RepositoryException;
import com.telran.springdiiocexceptionhandling.service.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    SecurityConfig securityConfig;

    @Override
    public boolean addUser(UserDto userDto) {
        try {
            return userRepository.addUser(map(userDto));
        } catch (RepositoryException ex) {
            throw new ServiceException(ex.getMessage(), ex);
        }

    }

    @Override
    public List<RolesEntity.Role> getUserRoles(String userEmail) {
        return Arrays.stream(userRepository.getRoles(userEmail)).collect(Collectors.toList());
    }

    private UserEntity map(UserDto userDto) {
        return UserEntity.builder()
                .email(userDto.getEmail())
                .password(userDto.getPassword())
                .name(userDto.getName())
                .city(userDto.getCity())
                .phone(userDto.getPhone())
                .build();
    }
}
