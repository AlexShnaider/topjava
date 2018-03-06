package ru.javawebinar.topjava.Storage;

import java.util.List;

public interface Storage<V> {
    void clear();

    void save(V value);

    void update(V value);

    V get(int id);

    void delete(int id);

    List<V> getAllSorted();
}
