package com.telran.springdiiocexceptionhandling.controllers.dto.profile;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

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
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate bDay;

}
