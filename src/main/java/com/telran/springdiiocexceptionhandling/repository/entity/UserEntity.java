package com.telran.springdiiocexceptionhandling.repository.entity;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@EqualsAndHashCode(of = "email")
public class UserEntity {
    private String email;
    private String password;
    private String name;
    private String city;
    private String phone;
}
