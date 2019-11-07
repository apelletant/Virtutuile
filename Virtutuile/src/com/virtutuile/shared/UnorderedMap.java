package com.virtutuile.shared;

import javafx.util.Pair;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

public class UnorderedMap<K, V> implements Map<K, V> {
    private List<Pair<K, V>> list = new ArrayList<>();

    public Vector toVector() {
        Vector vector = new Vector();
        list.forEach((element) -> {
            vector.add(element.getValue());
        });
        return vector;
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        for (Pair<K, V> pair : list) {
            if (pair.getKey().equals(key))
                return true;
        }
        return false;
    }

    @Override
    public boolean containsValue(Object value) {
        for (Pair<K, V> pair : list) {
            if (pair.getValue().equals(value))
                return true;
        }
        return false;
    }

    @Override
    public V get(Object key) {
        for (Pair<K, V> pair : list) {
            if (pair.getKey().equals(key))
                return pair.getValue();
        }
        return null;
    }

    @Override
    public V put(K key, V value) {
        list.add(new Pair<>(key, value));
        return value;
    }

    @Override
    public V remove(Object key) {
        int index = indexOf(key);
        if (index > -1) {
            return list.remove(index).getValue();
        }
        return null;
    }

    private int indexOf(Object key) {
        int i = 0;

        for (Pair<K, V> pair : list) {
            if (pair.getKey() == key)
                return i;
            ++i;
        }
        return -1;
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        m.forEach(this::put);
    }

    @Override
    public void clear() {
        list.clear();
    }

    @Override
    public Set<K> keySet() {
        Set<K> ret = new UnorderedSet<>();

        for (Pair<K, V> pair : list) {
            ret.add(pair.getKey());
        }
        return ret;
    }

    @Override
    public Collection<V> values() {
        Collection<V> ret = new UnorderedSet<>();

        for (Pair<K, V> pair : list) {
            ret.add(pair.getValue());
        }
        return ret;
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        Set<Entry<K, V>> ret = new UnorderedSet<>();

        for (Pair<K, V> pair : list) {
            ret.add(new AbstractMap.SimpleEntry<>(pair.getKey(), pair.getValue()));
        }
        return ret;
    }

    @Override
    public V getOrDefault(Object key, V defaultValue) {
        V found = get(key);
        if (found != null)
            return found;
        return defaultValue;
    }

    @Override
    public void forEach(BiConsumer<? super K, ? super V> action) {
        for (Pair<K, V> pair : list) {
            action.accept(pair.getKey(), pair.getValue());
        }
    }

    @Override
    public void replaceAll(BiFunction<? super K, ? super V, ? extends V> function) {
        int index = 0;
        for (Pair<K, V> pair : list) {
            Pair<K, V> newPair = new Pair<>(pair.getKey(), function.apply(pair.getKey(), pair.getValue()));
            list.set(index, newPair);
            ++index;
        }
    }

    @Override
    public V putIfAbsent(K key, V value) {
        if (indexOf(key) == -1) {
            list.add(new Pair<>(key, value));
        }
        return value;
    }

    @Override
    public boolean remove(Object key, Object value) {
        int index = indexOf(key);
        if (index >= 0) {
            list.remove(index);
            return true;
        }
        return false;
    }

    @Override
    public boolean replace(K key, V oldValue, V newValue) {
        int index = indexOf(key);
        if (index >= 0 && list.get(index).getValue().equals(oldValue)) {
            Pair<K, V> newPair = new Pair<>(key, newValue);
            list.set(index, newPair);
            return true;
        }
        return false;
    }

    @Override
    public V replace(K key, V value) {
        int index = indexOf(key);
        if (index >= 0) {
            V old = list.get(index).getValue();
            Pair<K, V> newPair = new Pair<>(key, value);
            list.set(index, newPair);
            return old;
        }
        return null;
    }

    @Override
    public V computeIfAbsent(K key, Function<? super K, ? extends V> mappingFunction) {
        int index = indexOf(key);
        if (index < 0) {
            Pair<K, V> pair = new Pair<>(key, mappingFunction.apply(key));
            list.add(pair);
            return pair.getValue();
        }
        return null;
    }

    @Override
    public V computeIfPresent(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
        int index = indexOf(key);
        if (index >= 0) {
            Pair<K, V> pair = new Pair<>(key, remappingFunction.apply(key, list.get(index).getValue()));
            list.set(index, pair);
            return pair.getValue();
        }
        return null;
    }

    @Override
    public V compute(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
        int index = indexOf(key);
        V val = null;
        if (index >= 0) {
            val = list.get(index).getValue();
        }
        Pair<K, V> pair = new Pair<>(key, remappingFunction.apply(key, val));
        list.set(index, pair);
        return pair.getValue();
    }

    @Override
    public V merge(K key, V value, BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
        int index = indexOf(key);
        V val = null;
        if (index >= 0) {
            val = list.get(index).getValue();
        }
        Pair<K, V> pair = new Pair<>(key, remappingFunction.apply(val, value));
        if (index >= 0)
            list.set(index, pair);
        else
            list.add(pair);
        return pair.getValue();
    }

    private class UnorderedSet<T> extends ArrayList<T> implements Set<T> {
    }

}
