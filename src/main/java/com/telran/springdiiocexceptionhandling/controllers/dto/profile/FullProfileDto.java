package com.telran.springdiiocexceptionhandling.controllers.dto.profile;

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
@ApiModel(value = "FullProfileDto",description = "User profile data transfer object extends ProfileDto")
public class FullProfileDto extends ProfileDto {
    @ApiModelProperty(notes = "User email")
    private String email;

    @Builder(builderMethodName = "fullProfileDto")
    public FullProfileDto(String firstName, String lastName, LocalDate bDay, String email) {
        super(firstName, lastName, bDay);
        this.email = email;
    }
}
