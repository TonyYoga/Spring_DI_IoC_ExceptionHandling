package com.telran.springdiiocexceptionhandling.service;

import com.telran.springdiiocexceptionhandling.controllers.dto.profile.FullProfileDto;

public interface ProfileService {

    void addProfile(FullProfileDto fullProfileDto);

    void updProfile(FullProfileDto fullProfileDto);

    FullProfileDto getProfileByOwner(String owner);
}
