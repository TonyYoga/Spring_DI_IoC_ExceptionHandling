package com.telran.springdiiocexceptionhandling.repository.entity;

import lombok.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.UUID;

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
