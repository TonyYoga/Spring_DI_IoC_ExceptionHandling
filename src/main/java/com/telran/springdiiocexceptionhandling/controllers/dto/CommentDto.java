package com.telran.springdiiocexceptionhandling.controllers.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/*
COMMENT:
id - String format, server should generate	it
date - DateTime added, server should put when topic adding, Format: "dd/MM/yyyy hh:mm"
author - String format, required
message - String format, required
 */
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class CommentDto {
    private String author;
    private String message;
}
