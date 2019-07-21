package com.telran.springdiiocexceptionhandling.controllers.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@ApiModel(value = "ProfileDto",description = "User profile data transfer object")
public class ProfileDto {
    @ApiModelProperty(notes = "User first name")
    private String firstName;
    @ApiModelProperty(notes = "User last name")
    private String lastName;
    @ApiModelProperty(notes = "User birthday")
    private LocalDateTime bDay;

}
