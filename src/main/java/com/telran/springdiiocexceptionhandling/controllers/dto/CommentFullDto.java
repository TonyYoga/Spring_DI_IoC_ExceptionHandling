package com.telran.springdiiocexceptionhandling.controllers.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class CommentFullDto extends CommentDto{
    private String id;
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime date;

    @Builder(builderMethodName = "fullCommentBuilder")
    public CommentFullDto(String author, String message, String id, LocalDateTime date) {
        super(author, message);
        this.id = id;
        this.date = date;
    }
}
