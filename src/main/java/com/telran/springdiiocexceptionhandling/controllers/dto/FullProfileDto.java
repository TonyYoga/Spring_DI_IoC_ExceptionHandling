package com.telran.springdiiocexceptionhandling.controllers.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@ApiModel(value = "FullProfileDto",description = "User profile data transfer object extends ProfileDto")
public class FullProfileDto extends ProfileDto {
    @ApiModelProperty(notes = "User email")
    private String email;

    @Builder(builderMethodName = "fullProfileDto")
    public FullProfileDto(String firstName, String lastName, LocalDateTime bDay, String email) {
        super(firstName, lastName, bDay);
        this.email = email;
    }
}
