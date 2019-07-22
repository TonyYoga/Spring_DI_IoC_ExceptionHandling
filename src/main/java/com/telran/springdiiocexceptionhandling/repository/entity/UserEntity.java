package com.telran.springdiiocexceptionhandling.repository.entity;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@EqualsAndHashCode(of = "email")
@Builder
public class UserEntity {
    private String email;
    private String password;
}
