package com.telran.springdiiocexceptionhandling.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class RolesEntity {

    public static enum Role {
        USER, ADMIN
    }

    private String email;
    private List<Role> roles;
}
