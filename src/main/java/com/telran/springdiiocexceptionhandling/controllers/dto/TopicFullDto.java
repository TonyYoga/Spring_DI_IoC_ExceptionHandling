package com.telran.springdiiocexceptionhandling.controllers.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

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
@ToString
@ApiModel(value = "TopicFullDto",description = "Full topic data transfer object extends TopicResponseDto")

public class TopicFullDto extends TopicResponseDto {
    @ApiModelProperty(notes = "List of comments")
    private List<CommentFullDto> comments;
    @Builder(builderMethodName = "fullTopicBuilder")
    public TopicFullDto(String id, String author, String title, String content, LocalDateTime date, List<CommentFullDto> comments) {
        super(id, author, title, content, date);
        this.comments = comments;
    }
}
