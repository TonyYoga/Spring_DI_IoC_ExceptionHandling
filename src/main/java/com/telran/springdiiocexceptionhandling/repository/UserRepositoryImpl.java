package com.telran.springdiiocexceptionhandling.repository;

import com.telran.springdiiocexceptionhandling.providers.StoreProvider;
import com.telran.springdiiocexceptionhandling.repository.entity.RolesEntity;
import com.telran.springdiiocexceptionhandling.repository.entity.UserEntity;
import com.telran.springdiiocexceptionhandling.repository.exception.DuplicateIdException;
import com.telran.springdiiocexceptionhandling.repository.exception.RepositoryException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

@Repository
public class UserRepositoryImpl implements UserRepository{
    private ConcurrentHashMap<String, UserEntity> users;
    private CopyOnWriteArrayList<RolesEntity> rolesOwners;

//    @Autowired
    private StoreProvider<UserEntity> provider;

    @Autowired
    @Qualifier("userRolesProvider")
    private StoreProvider<RolesEntity> userRolesProvider;


    public UserRepositoryImpl(@Qualifier("userProvider") StoreProvider<UserEntity> provider) {
        System.out.println(">>>User provider");

        this.provider = provider;
        users = new ConcurrentHashMap<>();
        rolesOwners = new CopyOnWriteArrayList<>();
    }

    @Override
    public boolean addUser(UserEntity userEntity) {
        if (users.putIfAbsent(userEntity.getEmail(), userEntity) == null) {
            List<RolesEntity.Role> roles = new ArrayList<>();
            RolesEntity.Role currentRole = RolesEntity.Role.USER;
            if (rolesOwners.isEmpty()) {
                currentRole = RolesEntity.Role.ADMIN;
            }
            roles.add(currentRole);
            rolesOwners.add(new RolesEntity(userEntity.getEmail(), roles));
            return true;
        }
        throw new DuplicateIdException("User with email " + userEntity.getEmail() + " already exict");
    }

    @Override
    public UserEntity getUserByEmail(String email) {
        return users.get(email);
    }

    @Override
    public RolesEntity.Role[] getRoles(String email) {
        RolesEntity res = rolesOwners.stream().filter(rolesEntity -> rolesEntity.getEmail().equals(email)).findAny().orElseThrow();
        return res.getRoles().toArray(new RolesEntity.Role[0]);
    }

    @PostConstruct
    private void loadData() {
        List<UserEntity> entities = provider.loadData();
        for (UserEntity user : entities) {
            users.putIfAbsent(user.getEmail(), user);
        }
        rolesOwners.addAll(userRolesProvider.loadData());

    }

    @PreDestroy
    private void storeData() {
        provider.storeData(new ArrayList<UserEntity>(users.values()));
        userRolesProvider.storeData(rolesOwners);
    }
}
