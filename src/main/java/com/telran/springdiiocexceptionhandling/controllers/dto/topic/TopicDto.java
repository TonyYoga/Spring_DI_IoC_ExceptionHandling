package com.telran.springdiiocexceptionhandling.controllers.dto.topic;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/*
TOPIC:
id - String format, server should generate	it
date - DateTime added, server should put when topic adding, Format: "dd/MM/yyyy hh:mm"
owner - String format, required
title - String format, required
content - String format, required
JSON Example of Topic request
{
  "owner":"[String]",
  "title":"[String]",
  "content":"[String]"
}

 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
@ApiModel(value = "TopicDto",description = "Topic data transfer object")

public class TopicDto {
//    @ApiModelProperty(notes = "Author of topic")
//    private String owner;
    @ApiModelProperty(notes = "Title of topic")
    private String title;
    @ApiModelProperty(notes = "Topic content")
    private String content;

}
