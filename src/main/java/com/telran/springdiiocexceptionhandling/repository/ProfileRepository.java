package com.telran.springdiiocexceptionhandling.repository;

import com.telran.springdiiocexceptionhandling.repository.entity.ProfileEntity;

public interface ProfileRepository {

    boolean addProfile(ProfileEntity profile);

    boolean updProfile(ProfileEntity profile);

    ProfileEntity getProfileByOwner(String owner);
}
