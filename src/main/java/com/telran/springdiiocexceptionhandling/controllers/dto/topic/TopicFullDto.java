package com.telran.springdiiocexceptionhandling.controllers.dto.topic;

import com.telran.springdiiocexceptionhandling.controllers.dto.comment.CommentFullDto;
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
    "owner": "[String]",
    "title": "[String]",
    "content": "[String]",
    "date": "[DateTime]",
    "comments": [
      {
        "id": "[String]",
        "date": "[DateTime]",
        "owner": "[String]",
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

    @ApiModelProperty(notes = "Owner first and last name")
    private String firstLastName;
    @ApiModelProperty(notes = "List of comments")
    private List<CommentFullDto> comments;
    @Builder(builderMethodName = "fullTopicBuilder")

    public TopicFullDto(String id, String owner, String title, String content, LocalDateTime date, List<CommentFullDto> comments, String firstLastName) {
        super(id, owner, title, content, date);
        this.firstLastName = firstLastName;
        this.comments = comments;
    }
}
