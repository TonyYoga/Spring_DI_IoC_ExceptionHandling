package com.telran.springdiiocexceptionhandling.service;

import com.telran.springdiiocexceptionhandling.controllers.dto.profile.FullProfileDto;
import com.telran.springdiiocexceptionhandling.repository.ProfileRepository;
import com.telran.springdiiocexceptionhandling.repository.entity.ProfileEntity;
import com.telran.springdiiocexceptionhandling.repository.exception.RepositoryException;
import com.telran.springdiiocexceptionhandling.service.exception.ServiceException;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
public class ProfileServiceImpl implements ProfileService{

    private ProfileRepository repository;

    public ProfileServiceImpl(ProfileRepository repository) {
        this.repository = repository;
    }

    @Override
    public void addProfile(FullProfileDto fullProfileDto) {
        try {
            repository.addProfile(map(fullProfileDto));
        } catch (RepositoryException ex) {
            throw new ServiceException(ex.getMessage(), ex);
        }

    }

    @Override
    public void updProfile(FullProfileDto fullProfileDto) {
        try {
            repository.updProfile(map(fullProfileDto));
        } catch (RepositoryException ex) {
            throw new ServiceException(ex.getMessage(), ex);
        }
    }

    @Override
    public FullProfileDto getProfileByOwner(String owner) {
        try {
            return map(repository.getProfileByOwner(owner));
        } catch (RepositoryException ex) {
            throw new ServiceException(ex.getMessage(), ex);
        }
    }

    private ProfileEntity map(FullProfileDto profileDto) {
        return ProfileEntity.builder()
                .firstName(profileDto.getFirstName())
                .lastName(profileDto.getLastName())
                .owner(profileDto.getEmail())
                .bDay(Timestamp.valueOf(profileDto.getBDay().atStartOfDay()))
                .build();
    }

    private FullProfileDto map(ProfileEntity profile) {
        return FullProfileDto.fullProfileDto()
                .firstName(profile.getFirstName())
                .lastName(profile.getLastName())
                .email(profile.getOwner())
                .bDay(profile.getBDay().toLocalDateTime().toLocalDate())
                .build();
    }
}
