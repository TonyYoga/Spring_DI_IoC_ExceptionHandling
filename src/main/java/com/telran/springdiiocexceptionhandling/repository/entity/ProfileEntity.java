package com.telran.springdiiocexceptionhandling.repository.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.sql.Timestamp;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@EqualsAndHashCode(of = "owner")
@Builder
public class ProfileEntity {
    private String owner;
    private String firstName;
    private String lastName;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Timestamp bDay;
}
