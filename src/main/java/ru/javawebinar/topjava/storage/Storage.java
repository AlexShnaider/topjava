package ru.javawebinar.topjava.storage;

import java.util.List;

public interface Storage<V> {

    V save(V value);

    void update(V value);

    V get(int id);

    void delete(int id);

    List<V> getAll();
}
