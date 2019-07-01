package com.telran.springdiiocexceptionhandling.controllers.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@ApiModel(value = "UserDto",description = "User data transfer object")
public class UserDto {
    @ApiModelProperty(notes = "User email")
    private String email;
    @ApiModelProperty(notes = "User password")
    private String password;
    @ApiModelProperty(notes = "User name")
    private String name;
    @ApiModelProperty(notes = "User city")
    private String city;
    @ApiModelProperty(notes = "User phone")
    private String phone;
}
