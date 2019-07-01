package com.telran.springdiiocexceptionhandling.service;

import com.telran.springdiiocexceptionhandling.controllers.dto.UserDto;
import org.springframework.stereotype.Service;

public interface UserService {

    boolean addUser(UserDto userDto);


}
