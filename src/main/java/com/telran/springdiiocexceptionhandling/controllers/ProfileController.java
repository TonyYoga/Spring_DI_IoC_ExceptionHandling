package com.telran.springdiiocexceptionhandling.controllers;

import com.telran.springdiiocexceptionhandling.controllers.dto.FullProfileDto;
import com.telran.springdiiocexceptionhandling.controllers.dto.ProfileDto;
import com.telran.springdiiocexceptionhandling.controllers.dto.SuccessResponseDto;

public interface ProfileController {

    FullProfileDto addProfile(ProfileDto profile);

    SuccessResponseDto updateProfile(ProfileDto profileDto);

    FullProfileDto getProfile(String id);

}
