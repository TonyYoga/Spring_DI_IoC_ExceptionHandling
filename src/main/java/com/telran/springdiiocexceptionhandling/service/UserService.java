package com.telran.springdiiocexceptionhandling.service;

import com.telran.springdiiocexceptionhandling.controllers.dto.UserDto;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    void addUser(UserDto userDto);
}
