package com.telran.springdiiocexceptionhandling.repository.entity;

import lombok.*;

import java.sql.Timestamp;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of="id")
@Builder
@ToString
public class TopicEntity {
    private Integer id;
    private String owner;
    private String title;
    private String content;
    private Timestamp date;
    private List<CommentEntity> comments;
}
