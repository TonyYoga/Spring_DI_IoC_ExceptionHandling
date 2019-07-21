package com.telran.springdiiocexceptionhandling.controllers;

import com.telran.springdiiocexceptionhandling.controllers.dto.FullProfileDto;
import com.telran.springdiiocexceptionhandling.controllers.dto.ProfileDto;
import com.telran.springdiiocexceptionhandling.controllers.dto.SuccessResponseDto;
import com.telran.springdiiocexceptionhandling.service.ProfileService;
import com.telran.springdiiocexceptionhandling.service.UserService;
import com.telran.springdiiocexceptionhandling.service.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("profile")
public class ProfileControllerImpl implements ProfileController {
    @Autowired
    ProfileService profileService;

    @Autowired
    UserService userService;

    @PostMapping
    @Override
    public FullProfileDto addProfile(@RequestBody ProfileDto profile) {
        String owner = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        FullProfileDto fullProfileDto = FullProfileDto.builder()
                .email(owner)
                .firstName(profile.getFirstName())
                .lastName(profile.getLastName())
                .bDay(profile.getBDay())
                .build();
        try {
            profileService.addProfile(fullProfileDto);
        } catch (ServiceException ex) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, ex.getMessage());
        }
        return fullProfileDto;
    }

    @PutMapping
    @Override
    public SuccessResponseDto updateProfile(@RequestBody FullProfileDto profileDto) {
        try {
            profileService.updProfile(profileDto);
            return new SuccessResponseDto("Profile of " + profileDto.getEmail() + " was updated!");
        } catch (ServiceException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        }
    }


    @Override
    @GetMapping("{id}")
    public FullProfileDto getProfile(@PathVariable("id") String id) {
        try {
            return profileService.getProfileById(id);
        } catch (ServiceException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        }
    }
}