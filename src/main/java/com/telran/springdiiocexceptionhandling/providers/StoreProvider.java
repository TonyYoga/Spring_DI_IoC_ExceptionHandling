package com.telran.springdiiocexceptionhandling.providers;

import java.util.List;

public interface StoreProvider<T> {
    void storeData(List<T> entities);
    List<T> loadData();
}
