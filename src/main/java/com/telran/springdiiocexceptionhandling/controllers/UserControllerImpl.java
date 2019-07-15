package com.telran.springdiiocexceptionhandling.controllers;

import com.telran.springdiiocexceptionhandling.controllers.dto.SuccessResponseDto;
import com.telran.springdiiocexceptionhandling.controllers.dto.UserDto;
import com.telran.springdiiocexceptionhandling.service.UserService;
import com.telran.springdiiocexceptionhandling.service.exception.ServiceException;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("user")
public class UserControllerImpl implements UserController {
    @Autowired
    UserService userService;

    @ApiOperation(value = "Add new user", response = SuccessResponseDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = ""),
            @ApiResponse(code = 401, message = "User with email: {email} already exist!")
    }
    )
    @PostMapping
    @Override
    public SuccessResponseDto registration(@RequestBody UserDto userDto) {
        try {
            userService.addUser(userDto);
            return new SuccessResponseDto("User with email " + userDto.getEmail() + " was added");
        } catch (ServiceException ex) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User with email " + userDto.getEmail() + " already exist");
        }

    }
}
