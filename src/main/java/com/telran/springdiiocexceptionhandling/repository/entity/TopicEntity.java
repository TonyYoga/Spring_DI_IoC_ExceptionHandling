package com.telran.springdiiocexceptionhandling.repository.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of="id")
@ToString
public class TopicEntity {
    private UUID id;
    private String author;
    private String title;
    private String content;
    private LocalDateTime date;
    private List<CommentEntity> comments;
}
