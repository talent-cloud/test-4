package com.tc.cache.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.tc.cache.api.CacheApiDelegate;
import com.tc.cache.model.CacheData;

@Component
public class CacheService implements CacheApiDelegate {

    @Override
    public ResponseEntity<Object> cacheIdGet(Integer id) {
        //TODO implement method
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    @Override
    public ResponseEntity<Void> cachePut(CacheData cacheData) {
        //TODO implement method
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }
}
