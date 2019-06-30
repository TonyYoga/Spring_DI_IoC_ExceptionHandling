package com.telran.springdiiocexceptionhandling.repository;

import com.telran.springdiiocexceptionhandling.providers.StoreProvider;
import com.telran.springdiiocexceptionhandling.repository.entity.UserEntity;
import com.telran.springdiiocexceptionhandling.repository.exception.DuplicateIdException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class UserRepositoryImpl implements UserRepository{
    private ConcurrentHashMap<String, UserEntity> users;


    StoreProvider provider;


    public UserRepositoryImpl(@Qualifier("userProvider") StoreProvider provider) {
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

    }
}
