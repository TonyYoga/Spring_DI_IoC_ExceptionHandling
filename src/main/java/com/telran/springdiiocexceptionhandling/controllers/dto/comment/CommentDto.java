package com.telran.springdiiocexceptionhandling.controllers.dto.comment;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/*
COMMENT:
message - String format, required
 */
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter

@ApiModel(value = "CommentDto",description = "Comment data transfer object")
public class CommentDto {
    @ApiModelProperty(notes = "Message")
    private String message;
}
