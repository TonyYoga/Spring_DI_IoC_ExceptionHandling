package com.telran.springdiiocexceptionhandling.controllers.dto.comment;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@Getter
@ToString

@ApiModel(value = "AddCommentDto",description = "CommentAdd data transfer object extends CommentDto")
public class AddCommentDto extends CommentDto {
    @ApiModelProperty(notes = "Topic Id")
    private String topicId;
}
