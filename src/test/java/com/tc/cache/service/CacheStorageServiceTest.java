package com.tc.cache.service;

import com.tc.cache.model.CacheData;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.event.annotation.BeforeTestClass;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.*;

@SpringBootTest
@DisplayName("Cache storage service test")
public class CacheStorageServiceTest {
    @Autowired
    private CacheStorageService cacheStorageService;
    @Value("${tc.cache.size}")
    private Integer cacheSize;
    Map<Integer, Object> synchronizedMap;

    @BeforeEach
    public void setUp(){
        synchronizedMap = Collections.synchronizedMap(new LinkedHashMap<>());
    }

    @Nested
    @DisplayName("Test put()")
    public  class TestPut {

        @BeforeEach
        public void setUp(){
            ReflectionTestUtils.setField(cacheStorageService, "synchronizedMap", synchronizedMap);
        }

        @Test
        @DisplayName("When the cache storage has no value")
        public void putTestWhenNoValuePresent()  {

            CacheData cacheData = new CacheData();
            cacheData.setId(1);
            cacheData.setData("A");

            cacheStorageService.put(cacheData);

            Assertions.assertTrue(synchronizedMap.containsKey(cacheData.getId()));
        }

        @Test
        @DisplayName("Test put() when new data are passed in sequential order")
        public void putTestWhenSequentialNewValueInserted(){
            CacheData cacheData = new CacheData();
            cacheData.setId(1);
            cacheData.setData("A");

            cacheStorageService.put(cacheData);

            cacheData = new CacheData();
            cacheData.setId(2);
            cacheData.setData("B");

            cacheStorageService.put(cacheData);

            cacheData = new CacheData();
            cacheData.setId(3);
            cacheData.setData("C");

            cacheStorageService.put(cacheData);


            Assertions.assertEquals(synchronizedMap.size(), cacheSize);
            Assertions.assertTrue(synchronizedMap.containsKey(2));
            Assertions.assertTrue(synchronizedMap.containsKey(3));
            Assertions.assertFalse(synchronizedMap.containsKey(1));

        }
    }

    @Nested
    @DisplayName("Test get()")
    public class TestGet{
        @BeforeEach
        public void setUp(){
            ReflectionTestUtils.setField(cacheStorageService, "synchronizedMap", synchronizedMap);

            CacheData cacheData = new CacheData();
            cacheData.setId(1);
            cacheData.setData("A");
            synchronizedMap.put(cacheData.getId(), cacheData);

            cacheData = new CacheData();
            cacheData.setId(2);
            cacheData.setData("B");
            synchronizedMap.put(cacheData.getId(), cacheData);
        }

        @Test
        @DisplayName("Test the order when item is fetched")
        public void testMapKeyOrder(){
            CacheData cacheObject = (CacheData)cacheStorageService.get(1);
            Assertions.assertEquals(cacheObject.getId(), 1);
            Set<Integer> keySet = new LinkedHashSet<>();
            keySet.add(2);
            keySet.add(1);
            Assertions.assertIterableEquals(synchronizedMap.keySet(), keySet);
        }
    }


}
