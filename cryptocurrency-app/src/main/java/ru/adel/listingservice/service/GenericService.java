package ru.adel.listingservice.service;

import java.util.List;

public interface GenericService<T> {
    List<T> getDataList(int start, int limit);
    T getByName(String name);
    void saveData(T data);
    void saveAllData(List<T> dataList);
}
