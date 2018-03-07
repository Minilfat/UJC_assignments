package org.trip.store.dao.ifaces;

import java.util.List;

public interface DAO<K,V> {

    V getById(K key);
    List<V> getAll();
    K insert(V value);
}
