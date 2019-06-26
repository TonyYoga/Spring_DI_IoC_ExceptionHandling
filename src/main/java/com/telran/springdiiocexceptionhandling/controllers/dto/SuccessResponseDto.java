package com.telran.springdiiocexceptionhandling.controllers.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
@ApiModel(value = "SuccessResponseDto",description = "Success response data transfer object")

public class SuccessResponseDto {
    @ApiModelProperty(notes = "Message")
    private String message;
}
