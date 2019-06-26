package com.telran.springdiiocexceptionhandling.repository.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode(of="id")

public class CommentEntity {
    private UUID id;
    private String author;
    private String message;
    private LocalDateTime date;
}
