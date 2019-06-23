package com.telran.springdiiocexceptionhandling.controllers.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@Getter
@ToString
public class AddCommentDto extends CommentDto {
    private String topicId;

}
