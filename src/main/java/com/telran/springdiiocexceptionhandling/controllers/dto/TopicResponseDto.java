package com.telran.springdiiocexceptionhandling.controllers.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;

/*
{
  "id":"[String]",
  "author":"[String]",
  "title":"[String]",
  "content":"[String]",
  "date":"[DateTime]"
}

 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class TopicResponseDto extends TopicDto {

    private String id;
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime date;

    @Builder(builderMethodName = "topicResponseBuilder")
    public TopicResponseDto(String author, String title, String content, String id, LocalDateTime date) {
        super(author, title, content);
        this.id = id;
        this.date = date;
    }
}
