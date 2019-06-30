package com.telran.springdiiocexceptionhandling.controllers;

import com.telran.springdiiocexceptionhandling.controllers.dto.SuccessResponseDto;
import com.telran.springdiiocexceptionhandling.controllers.dto.UserDto;
import com.telran.springdiiocexceptionhandling.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user")
public class UserControllerImpl implements UserController {
    @Autowired
    UserService userService;

    @PostMapping
    @Override
    public SuccessResponseDto registration(@RequestBody UserDto userDto) {
        userService.addUser(userDto);
    }
}
