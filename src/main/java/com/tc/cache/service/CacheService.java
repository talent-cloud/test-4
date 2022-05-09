package com.tc.cache.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;

import com.tc.cache.api.CacheApiDelegate;
import com.tc.cache.model.CacheData;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/lru")
@AllArgsConstructor
@Slf4j
public class CacheService implements CacheApiDelegate {
    private final CacheStorageService inMemoryCache;

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<Object> cacheIdGet(@PathVariable("id") Integer id) {
        Object cacheObject =  inMemoryCache.get(id);
        return ResponseEntity.ok(cacheObject);
    }

    @Override
    @PutMapping
    public ResponseEntity<Void> cachePut(@RequestBody CacheData cacheData) {
        log.info("Saving cache data with key {} and object: {}", cacheData.getId(), cacheData.getData());
        inMemoryCache.put(cacheData);
        return ResponseEntity.ok().build();
    }
}
