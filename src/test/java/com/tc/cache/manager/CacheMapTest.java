package com.tc.cache.manager;

import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.*;

class CacheMapTest {

    private CacheMap<Integer, Object> cacheMap = new CacheMap<>(3);

    @Test
    void whenClear_thenDoClear() {
        cacheMap.put(1, "A");
        assertEquals(1, cacheMap.size());

        cacheMap.clear();
        assertEquals(0, cacheMap.size());
    }

    @Test
    void whenAddWhereCacheSizeZero_thenNotAddCache() {
        cacheMap = new CacheMap<>(0);
        assertTrue(cacheMap.isEmpty());

        cacheMap.put(1, "A");
        assertEquals(0, cacheMap.size());
        assertEquals("[]", cacheMap.toString());
    }

    @Test
    void whenSize_thenReturnSize() {
        assertEquals(0, cacheMap.size());
    }

    @Test
    void whenPutElement_thenPut() {
        cacheMap.put(1, "A");

        assertEquals(1, cacheMap.size());
        assertEquals("[{1=A}]", cacheMap.toString());
    }

    @Test
    void whenPutAll_thenPut() {
        Map<Integer, Object> treeMap = new TreeMap<>();
        treeMap.put(1, "A");
        treeMap.put(2, "B");
        treeMap.put(3, "C");
        treeMap.put(4, "D");
        cacheMap.putAll(treeMap);

        assertEquals(3, cacheMap.size());
        assertEquals("[{4=D}, {3=C}, {2=B}]", cacheMap.toString());
    }

    @Test
    void whenKeySet_thenReturnKeys() {
        cacheMap.put(1, "A");
        cacheMap.put(2, "B");
        cacheMap.put(3, "C");
        cacheMap.put(4, "D");

        assertEquals("[4, 3, 2]", cacheMap.keySet().toString());
    }

    @Test
    void whenValues_thenReturnValues() {
        cacheMap.put(1, "A");
        cacheMap.put(2, "B");
        cacheMap.put(3, "C");
        cacheMap.put(4, "D");
        cacheMap.put(1, "E");

        assertEquals("[E, D, C]", cacheMap.values().toString());
    }

    @Test
    void whenContainsKey_thenReturnValue() {
        cacheMap.put(1, "A");
        assertTrue(cacheMap.containsKey(1));
    }

    @Test
    void whenContainsValue_thenThrowsException() {
        assertThrows(IllegalStateException.class, () -> assertTrue(cacheMap.containsValue("A")));
    }

    @Test
    void whenRemoveElement_thenRemove() {
        cacheMap.put(1, "A");
        cacheMap.put(2, "B");

        cacheMap.remove(2);
        assertEquals(1, cacheMap.size());
        assertEquals("[{1=A}]", cacheMap.toString());

        cacheMap.remove(1);
        assertEquals(0, cacheMap.size());
        assertEquals("[]", cacheMap.toString());
    }

    @Test
    void whenRemoveElementNotExistent_thenReturnsNull() {
        Object value = cacheMap.remove(1);
        assertNull(value);
    }

    @Test
    void whenUpdateWithMultiElements_thenMoveToFront() {
        cacheMap.put(1, "A");
        cacheMap.put(2, "B");
        cacheMap.put(3, "C");
        assertEquals("[{3=C}, {2=B}, {1=A}]", cacheMap.toString());

        cacheMap.put(1, "D");
        assertEquals("[{1=D}, {3=C}, {2=B}]", cacheMap.toString());
    }

    @Test
    void whenPutMultiElements_thenKeepUntilTheLimit() {
        cacheMap.put(1, "A");
        cacheMap.put(2, "B");
        cacheMap.put(3, "C");
        assertEquals("[{3=C}, {2=B}, {1=A}]", cacheMap.toString());

        cacheMap.put(4, "D");
        assertEquals("[{4=D}, {3=C}, {2=B}]", cacheMap.toString());
    }
}
