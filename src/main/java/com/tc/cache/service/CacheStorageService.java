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

    public CacheStorageService() {
        synchronizedMap = Collections.synchronizedMap(new LinkedHashMap<>());
    }

    public void put(CacheData cacheData){
        synchronizedMap.put(cacheData.getId(), cacheData.getData());
        configureCacheStorage(cacheData);
    }

    private void configureCacheStorage(CacheData cacheData) {
        if(synchronizedMap.size()>cacheSize){
            List<Integer> keys = new ArrayList<>(synchronizedMap.keySet());
            Integer keyToBeRemoved = keys.get(0);
            log.info("Removing key: {} ", keyToBeRemoved);
            synchronizedMap.remove(keyToBeRemoved);
        }

    }

    public Object get(Integer key){
        Object keyObject = synchronizedMap.get(key);
        synchronizedMap.remove(key); // removing and then inserting to update the order of LinkedHashMap
        synchronizedMap.put(key, keyObject); // inserting the data again for updating the order of LinkedHashMap
        return synchronizedMap.get(key);
    }
}
