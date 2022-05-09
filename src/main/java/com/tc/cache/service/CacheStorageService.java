package com.tc.cache.service;

import com.tc.cache.model.CacheData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
public class CacheStorageService {

    @Value("${tc.cache.size}")
    private Integer cacheSize;
    private Map<Integer,Object> synchronizedMap;
    private Queue<Integer> keyQueue = new ArrayDeque<>();

    public CacheStorageService() {
        Map<Integer,Object> map = new HashMap<>();
        synchronizedMap = Collections.synchronizedMap(map);
    }

    public void put(CacheData cacheData){
        synchronizedMap.put(cacheData.getId(), cacheData.getData());
        configureMemoryKeyQueue(cacheData);
    }

    private void configureMemoryKeyQueue(CacheData cacheData) {
        keyQueue.add(cacheData.getId());
        if(keyQueue.size()>cacheSize){
            Integer key = keyQueue.poll();
            log.info("Removing key: {}", key);
            synchronizedMap.remove(key);
        }
    }

    public Object get(Integer key){
        return synchronizedMap.get(key);
    }
}
