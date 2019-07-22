package com.telran.springdiiocexceptionhandling.controllers.dto.comment;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@ToString
@ApiModel(value = "CommentDto",description = "Comment data transfer object")
public class UpdateCommentDto extends CommentFullDto {
    @ApiModelProperty(notes = "Id of the topic")
    private String topicId;

    @Builder(builderMethodName = "updateCommentBuilder")
    public UpdateCommentDto(String message, String id, String author, String owner , LocalDateTime date, String topicId) {
        super(message, id, author, owner, date);
        this.topicId = topicId;
    }
}
