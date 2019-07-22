package com.telran.springdiiocexceptionhandling.controllers.dto.comment;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/*
COMMENT:
id - String format, server should generate	it
date - DateTime added, server should put when topic adding, Format: "dd/MM/yyyy hh:mm"
owner - String format, required
message - String format, required
 */
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter

@ApiModel(value = "CommentDto",description = "Comment data transfer object")
public class CommentDto {
//    @ApiModelProperty(notes = "Author of Comment")
//    private String author;
    @ApiModelProperty(notes = "Message")
    private String message;
}
