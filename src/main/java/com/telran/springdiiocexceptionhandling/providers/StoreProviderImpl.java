package com.telran.springdiiocexceptionhandling.providers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.telran.springdiiocexceptionhandling.providers.exception.StoreProviderException;
import com.telran.springdiiocexceptionhandling.repository.entity.TopicEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;

@Component
public class StoreProviderImpl implements StoreProvider<TopicEntity> {

    @Autowired
    ObjectMapper mapper;

    @Value("dbTopics")
    private String fileName;

    @Override
    public void storeData(List<TopicEntity> entities) {
        try {
            mapper.writeValue(Files.newOutputStream(Path.of(fileName)), entities);
        } catch (IOException e) {
            e.printStackTrace();
            throw new StoreProviderException("Files system error", e);
        }
    }

    @Override
    public List<TopicEntity> loadData() {
        try {
            if (Files.exists(Path.of(fileName))) {
                return mapper.readValue(Files.newBufferedReader(Path.of(fileName)), new TypeReference<List<TopicEntity>>() {
                });
            } else {
                return Collections.emptyList();
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new StoreProviderException("File System error", e);
        }
    }
}
