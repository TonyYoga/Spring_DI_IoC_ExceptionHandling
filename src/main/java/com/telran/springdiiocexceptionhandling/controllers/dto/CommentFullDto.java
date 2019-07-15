package com.telran.springdiiocexceptionhandling.controllers.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString

@ApiModel(value = "CommentFullDto",description = "Full Comment data transfer object extends CommentDto")

public class CommentFullDto extends CommentDto{

    @ApiModelProperty(notes = "Id of comment")
    private String id;

    @ApiModelProperty(notes = "Date and time of comment")
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime date;

    @Builder(builderMethodName = "fullCommentBuilder")
    public CommentFullDto(String author, String message, String id, LocalDateTime date) {
        super(author, message);
        this.id = id;
        this.date = date;
    }
}
