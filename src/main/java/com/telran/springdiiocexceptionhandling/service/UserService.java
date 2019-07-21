package com.telran.springdiiocexceptionhandling.service;

import com.telran.springdiiocexceptionhandling.controllers.dto.UserDto;

import java.util.List;

public interface UserService {

    boolean addUser(UserDto userDto);

    List<String> getUserRoles(String userEmail);



}
