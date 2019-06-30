package com.telran.springdiiocexceptionhandling.controllers;

import com.telran.springdiiocexceptionhandling.controllers.dto.SuccessResponseDto;
import com.telran.springdiiocexceptionhandling.controllers.dto.UserDto;

public interface UserController {

    SuccessResponseDto registration(UserDto userDto);
}
