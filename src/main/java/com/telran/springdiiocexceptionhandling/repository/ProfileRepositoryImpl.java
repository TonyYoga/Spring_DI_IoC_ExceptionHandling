package com.telran.springdiiocexceptionhandling.repository;

import com.telran.springdiiocexceptionhandling.providers.StoreProvider;
import com.telran.springdiiocexceptionhandling.repository.entity.ProfileEntity;
import com.telran.springdiiocexceptionhandling.repository.exception.DuplicateIdException;
import com.telran.springdiiocexceptionhandling.repository.exception.IllegalIdException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class ProfileRepositoryImpl implements ProfileRepository {
    private StoreProvider<ProfileEntity> provider;
    private ConcurrentHashMap<String, ProfileEntity> repoProfile;

    public ProfileRepositoryImpl(@Qualifier("profileProviderImpl") StoreProvider<ProfileEntity> provider) {
        this.provider = provider;
        repoProfile  = new ConcurrentHashMap<>();
    }

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

    @PostConstruct
    private void loadData() {
        List<ProfileEntity> entities = provider.loadData();
        for (ProfileEntity entity : entities) {
            repoProfile.putIfAbsent(entity.getOwner(), entity);
        }

    }

    @PreDestroy
    private void storeData() {
        provider.storeData(new ArrayList<>(repoProfile.values()));
    }
}
