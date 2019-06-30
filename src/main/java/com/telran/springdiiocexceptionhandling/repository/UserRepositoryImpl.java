package com.telran.springdiiocexceptionhandling.repository;

import com.telran.springdiiocexceptionhandling.providers.StoreProvider;
import com.telran.springdiiocexceptionhandling.repository.entity.UserEntity;
import com.telran.springdiiocexceptionhandling.repository.exception.DuplicateIdException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class UserRepositoryImpl implements UserRepository{
    private ConcurrentHashMap<String, UserEntity> users;

//    @Autowired
    private StoreProvider<UserEntity> provider;


    public UserRepositoryImpl(@Qualifier("userProvider") StoreProvider<UserEntity> provider) {
        System.out.println(">>>User provider");

        this.provider = provider;
        users = new ConcurrentHashMap<>();
    }

    @Override
    public boolean addUser(UserEntity userEntity) {
        if (users.putIfAbsent(userEntity.getEmail(), userEntity) == null) {
            return true;
        }
        throw new DuplicateIdException("User with email " + userEntity.getEmail() + " already exict");
    }

    @Override
    public UserEntity getUserByEmail(String email) {
        return users.get(email);
    }

    @PostConstruct
    private void loadData() {
        List<UserEntity> entities = provider.loadData();
        for (UserEntity user : entities) {
            users.putIfAbsent(user.getEmail(), user);
        }
    }

    @PreDestroy
    private void storeData() {
        provider.storeData(new ArrayList<UserEntity>(users.values()));
    }
}
