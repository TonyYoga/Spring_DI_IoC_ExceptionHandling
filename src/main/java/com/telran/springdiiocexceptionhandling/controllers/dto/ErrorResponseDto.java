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
@ApiModel(value = "ErrorResponseDto",description = "Error response data transfer object")

public class ErrorResponseDto {
    @ApiModelProperty(notes = "Error message")
    private String message;
}
