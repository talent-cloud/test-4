package com.tc.cache.manager;

import java.util.AbstractMap;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CacheMap<K, V> implements Map<K, V> {

    private final Map<K, CacheEntry<K, V>> map = new HashMap<>();
    private final CacheEntry<K, V> header = new CacheEntry<>(null, null);

    private final int cacheSize;

    public CacheMap(int cacheSize) {
        this.cacheSize = cacheSize;
        this.header.before = this.header.after = this.header;
    }

    @Override
    public int size() {
        return map.size();
    }

    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return map.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        throw new IllegalStateException("Method cannot be used!");
    }

    @Override
    public V get(Object key) {
        CacheEntry<K, V> entry = map.get(key);
        if (entry != null) {
            moveToStart(entry);
            return entry.getValue();
        }
        return null;
    }

    private void moveToStart(CacheEntry<K, V> entry) {
        if (entry.before != header) {
            // remove
            entry.before.after = entry.after;
            entry.after.before = entry.before;
            // add first
            entry.after = header.after;
            entry.before = header;
            header.after.before = entry;
            header.after = entry;
        }
    }

    @Override
    public V put(K key, V value) {
        CacheEntry<K, V> old = map.get(key);

        if (old != null) {
            old.setValue(value);
            moveToStart(old);
        } else {
            CacheEntry<K, V> entry = new CacheEntry<>(key, value);
            map.put(key, entry);

            entry.before = header;
            entry.after = header.after;
            header.after.before = entry;
            header.after = entry;

            if (size() > cacheSize) {
                map.remove(header.before.getKey());
                header.before.before.after = header;
                header.before = header.before.before;
            }
        }

        return value;
    }

    @Override
    public V remove(Object key) {
        CacheEntry<K, V> entry = map.remove(key);
        if (entry != null) {
            entry.after.before = entry.before;
            entry.before.after = entry.after;
            return entry.getValue();
        }
        return null;
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        m.forEach(this::put);
    }

    @Override
    public void clear() {
        map.clear();
        header.before = header.after = header;
    }

    @Override
    public Set<K> keySet() {
        Set<K> keySet = new LinkedHashSet<>();
        CacheEntry<K, V> actual = header;
        while (actual.after != header) {
            actual = actual.after;
            keySet.add(actual.getKey());
        }
        return keySet;
    }

    @Override
    public Collection<V> values() {
        List<V> values = new LinkedList<>();
        CacheEntry<K, V> actual = header;
        while (actual.after != header) {
            actual = actual.after;
            values.add(actual.getValue());
        }
        return values;
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        Set<Entry<K, V>> entrySet = new LinkedHashSet<>();
        CacheEntry<K, V> actual = header;
        while (actual.after != header) {
            actual = actual.after;
            entrySet.add(actual);
        }
        return entrySet;
    }

    @Override
    public String toString() {
        return entrySet().toString();
    }

    static class CacheEntry<K, V> extends AbstractMap.SimpleEntry<K, V> {

        CacheEntry<K, V> before;
        CacheEntry<K, V> after;

        public CacheEntry(K key, V value) {
            super(key, value);
        }

        @Override
        public String toString() {
            return "{" + getKey() + "=" + getValue() + '}';
        }
    }
}
