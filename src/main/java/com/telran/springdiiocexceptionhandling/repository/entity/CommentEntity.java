package com.telran.springdiiocexceptionhandling.repository.entity;

import lombok.*;

import java.sql.Timestamp;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode(of="id")
@Builder
public class CommentEntity {
    private Integer id;
    private String owner;
    private String message;
    private Timestamp date;
}
