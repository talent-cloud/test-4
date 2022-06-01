package com.tc.cache.controller;

import com.tc.cache.api.ApiUtil;
import com.tc.cache.model.CacheData;
import com.tc.cache.service.CacheService;
import com.tc.cache.utils.ContentFormatter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.NativeWebRequest;

import java.util.Deque;

@Slf4j
@RestController
@RequestMapping("/api/v1")
public class CacheController {

    private static final String CONTENT_TYPE = "text/plain";
    private final NativeWebRequest nativeWebRequest;
    private final CacheService cacheService;

    public CacheController(NativeWebRequest nativeWebRequest, CacheService cacheService) {
        this.nativeWebRequest = nativeWebRequest;
        this.cacheService = cacheService;
    }

    @GetMapping("/caches/{id}")
    public ResponseEntity<Deque<CacheData>> cacheIdGet(@PathVariable Integer id) {
        log.debug("CacheController::cacheIdGet -> id: "+id);
        ResponseEntity<Object> objectResponseEntity = cacheService.cacheIdGet(id);
        Deque<CacheData> cacheDataDeque = (Deque<CacheData>) objectResponseEntity.getBody();
        String cacheContent = ContentFormatter.displayCacheContent(cacheDataDeque);
        ApiUtil.setExampleResponse(nativeWebRequest, CONTENT_TYPE, cacheContent);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/caches")
    public ResponseEntity<Void> cachePut(@RequestBody CacheData cacheData) {
        log.info("CacheController::cachePut -> cacheData: "+cacheData);
        cacheService.cachePut(cacheData);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }
}
