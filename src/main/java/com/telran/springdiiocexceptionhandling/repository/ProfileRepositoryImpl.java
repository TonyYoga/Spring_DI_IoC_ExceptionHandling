package com.telran.springdiiocexceptionhandling.repository;

import com.telran.springdiiocexceptionhandling.repository.entity.ProfileEntity;
import com.telran.springdiiocexceptionhandling.repository.exception.DuplicateIdException;
import com.telran.springdiiocexceptionhandling.repository.exception.IllegalIdException;
import org.springframework.stereotype.Repository;

import java.util.concurrent.ConcurrentHashMap;

@Repository
public class ProfileRepositoryImpl implements ProfileRepository {
    private ConcurrentHashMap<String, ProfileEntity> repoProfile = new ConcurrentHashMap<>();

    @Override
    public boolean addProfile(ProfileEntity profile) {
        if (repoProfile.putIfAbsent(profile.getOwner(), profile) == null) {
            return true;
        }
        throw new DuplicateIdException(String.format("User profile with owner %s already excists", profile.getOwner()));
    }

    @Override
    public boolean updProfile(ProfileEntity profile) {
        if (repoProfile.replace(profile.getOwner(), profile) != null) {
            return true;
        }
        throw new IllegalIdException(String.format("User profile with owner %s not found", profile.getOwner()));

    }

    @Override
    public ProfileEntity getProfileByOwner(String owner) {
        ProfileEntity res = repoProfile.get(owner);
        if (res != null) {
            return res;
        }
        throw new IllegalIdException(String.format("User profile with owner %s not found", owner));
    }
}
