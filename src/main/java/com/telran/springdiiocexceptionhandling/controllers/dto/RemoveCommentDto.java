package com.telran.springdiiocexceptionhandling.controllers.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class RemoveCommentDto {
    private String topicId;
    private String commentId;
}
