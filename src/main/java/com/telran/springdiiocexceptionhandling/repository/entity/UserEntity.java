package com.telran.springdiiocexceptionhandling.repository.entity;

import lombok.*;

import java.util.UUID;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@EqualsAndHashCode(of = "email")
@Builder
public class UserEntity {
    private String email;
    private String password;
    private String name;
    private String city;
    private String phone;
}
