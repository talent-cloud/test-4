package com.tc.cache.manager;

import com.tc.cache.config.CacheProperties;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Map;

@Component
public class CacheManager {

    private final Map<Integer, Object> cacheMap;

    public CacheManager(CacheProperties cacheProperties) {
        this.cacheMap = Collections.synchronizedMap(new CacheMap<>(cacheProperties.getSize()));
    }

    public Object get(Integer id) {
        return cacheMap.get(id);
    }

    public void put(Integer id, Object data) {
        cacheMap.put(id, data);
    }

    public String getContent() {
        return cacheMap.toString();
    }
}
