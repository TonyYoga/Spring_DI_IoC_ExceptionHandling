package com.telran.springdiiocexceptionhandling.controllers.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class UserDto {
    private String email;
    private String password;
    private String name;
    private String city;
    private String phone;
}
