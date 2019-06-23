package com.telran.springdiiocexceptionhandling.controllers.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

/*
Json example
[
  {
    "id": "[String]",
    "author": "[String]",
    "title": "[String]",
    "content": "[String]",
    "date": "[DateTime]",
    "comments": [
      {
        "id": "[String]",
        "date": "[DateTime]",
        "author": "[String]",
        "message": "[String]"
      }
    ]
  }
]

 */
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class TopicFullResponceDto {
    private String id;
    private String author;
    private String title;
    private String content;
    @JsonFormat(pattern = "dd/MM/yyyy hh:mm")
    private LocalDateTime date;
    private List<CommentResponceDto> comments;
}
