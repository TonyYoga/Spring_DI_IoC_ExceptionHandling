package com.telran.springdiiocexceptionhandling.controllers.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString

@ApiModel(value = "RemoveCommentDto",description = "RemoveComment data transfer object")
public class RemoveCommentDto {
    @ApiModelProperty(notes = "Id of the topic")
    private String topicId;
    @ApiModelProperty(notes = "Id of the comment to delete")
    private String commentId;
}
