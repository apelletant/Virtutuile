package com.virtutuile.shared;

import java.util.Map;

public class Pair<K, V> implements Map.Entry<K, V> {
    private K key = null;
    private V value = null;

    public Pair(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public Pair() {
    }

    @Override
    public K getKey() {
        return key;
    }

    @Override
    public V getValue() {
        return value;
    }

    @Override
    public V setValue(V value) {
        this.value = value;
        return value;
    }

    @Override
    public boolean equals(Object o) {
        return value.equals(o);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
