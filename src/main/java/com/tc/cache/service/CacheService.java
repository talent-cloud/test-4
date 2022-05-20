package com.tc.cache.service;

import com.tc.cache.api.CacheApiDelegate;
import com.tc.cache.manager.CacheManager;
import com.tc.cache.model.CacheData;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class CacheService implements CacheApiDelegate {

    private final CacheManager cacheManager;

    public CacheService(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    @Override
    public ResponseEntity<Object> cacheIdGet(Integer id) {
        return ResponseEntity.ok(cacheManager.get(id));
    }

    @Override
    public ResponseEntity<Void> cachePut(CacheData cacheData) {
        cacheManager.put(cacheData.getId(), cacheData.getData());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
