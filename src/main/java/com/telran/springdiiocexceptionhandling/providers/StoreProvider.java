package com.telran.springdiiocexceptionhandling.providers;

import com.telran.springdiiocexceptionhandling.repository.entity.TopicEntity;

import java.util.List;

public interface StoreProvider {
    void storeData(List<TopicEntity> entities);
    List<TopicEntity> loadData();
}
