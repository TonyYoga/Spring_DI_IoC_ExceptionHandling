package com.telran.springdiiocexceptionhandling.providers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.telran.springdiiocexceptionhandling.providers.exception.StoreProviderException;
import com.telran.springdiiocexceptionhandling.repository.entity.RolesEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;

//@Component
public class UserRolesStoreProviderImpl implements StoreProvider<RolesEntity> {
    private ObjectMapper mapper;

    @Value("${dbRoles}")
    private String filename;

    public UserRolesStoreProviderImpl(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public void storeData(List<RolesEntity> entities) {
        try {
            mapper.writeValue(Files.newOutputStream(Path.of(filename)), entities);
        } catch (IOException ex) {
            ex.printStackTrace();
            throw new StoreProviderException("Files system error", ex);
        }


    }

    @Override
    public List<RolesEntity> loadData() {
        try {
            if (Files.exists(Path.of(filename))) {
               return mapper.readValue(Files.newBufferedReader(Path.of(filename)), new TypeReference<List<RolesEntity>>(){});
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            throw new StoreProviderException("Files system error", ex);
        }

        return Collections.emptyList();
    }
}
