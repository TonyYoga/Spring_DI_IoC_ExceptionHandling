package com.telran.springdiiocexceptionhandling.controllers.dto.topic;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.time.LocalDateTime;

/*
{
  "id":"[String]",
  "owner":"[String]",
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
@ApiModel(value = "TopicResponseDto",description = "Topic response data transfer object extends TopicDto")

public class TopicResponseDto extends TopicDto {
    @ApiModelProperty(notes = "Id of the topic")
    private String id;
    @ApiModelProperty(notes = "Owner of topic")
    private String owner;
    @ApiModelProperty(notes = "Topic date and time")
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime date;

    @Builder(builderMethodName = "topicResponseBuilder")
    public TopicResponseDto(String id, String owner, String title, String content, LocalDateTime date) {
        super(title, content);
        this.owner = owner;
        this.id = id;
        this.date = date;
    }
}
