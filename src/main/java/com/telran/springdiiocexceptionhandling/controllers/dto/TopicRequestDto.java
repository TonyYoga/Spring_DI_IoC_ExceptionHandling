package com.telran.springdiiocexceptionhandling.controllers.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/*
TOPIC:
id - String format, server should generate	it
date - DateTime added, server should put when topic adding, Format: "dd/MM/yyyy hh:mm"
author - String format, required
title - String format, required
content - String format, required
JSON Example of Topic request
{
  "author":"[String]",
  "title":"[String]",
  "content":"[String]"
}

 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class TopicRequestDto {
    private String author;
    private String title;
    private String content;
}
