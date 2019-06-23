package com.telran.springdiiocexceptionhandling.controllers.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
public class TopicResponceDto {
    private String id;
    private String author;
    private String title;
    private String content;
    @JsonFormat(pattern = "dd/MM/yyyy hh:mm")
    private LocalDateTime date;
}
