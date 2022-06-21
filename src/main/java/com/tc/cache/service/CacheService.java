package com.tc.cache.service;

import com.tc.cache.api.ApiUtil;
import com.tc.cache.api.CacheApiDelegate;
import com.tc.cache.model.CacheData;
import com.tc.cache.utils.ContentFormatter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.context.request.NativeWebRequest;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Objects;

@Slf4j
@Component
@CacheConfig(cacheNames = "cacheData")
public class CacheService implements CacheApiDelegate {

    private static final String CONTENT_TYPE = "text/plain";
    private final NativeWebRequest nativeWebRequest;

    private Deque<CacheData> cacheDataQueue;

    @Value("${tc.cache.size}")
    String tcCacheSize;

    public CacheService(NativeWebRequest nativeWebRequest) {
        this.nativeWebRequest = nativeWebRequest;
        cacheDataQueue = new LinkedList<>();
    }

    @Override
    public ResponseEntity<Object> cacheIdGet(Integer id) {
        log.debug("CacheService::cacheIdGet --> id: " + id);
        Object elementFromCache = getElementFromCache(id);
        log.info(String.valueOf("Objects.isNull(elementFromCache) >> " + Objects.isNull(elementFromCache)));
        if (Objects.isNull(elementFromCache)) {
            new ResponseEntity<>("Element doesn't exists with id: " + id, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(cacheDataQueue, HttpStatus.OK);
    }

    @Override
    @CachePut(value = "cacheData", key = "#cacheData.id")
    public ResponseEntity<Void> cachePut(@RequestBody CacheData cacheData) {
        log.debug("CacheService::cachePut -> cacheData: " + cacheData);
        putElementInCache(cacheData);
        String cacheContent = ContentFormatter.displayCacheContent(cacheDataQueue);
        ApiUtil.setExampleResponse(nativeWebRequest, CONTENT_TYPE, cacheContent);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private void putElementInCache(CacheData cacheData) {
        int key = cacheData.getId();
        if (cacheDataQueue.size() == Integer.parseInt(tcCacheSize)) {
            cacheDataQueue.removeLast();
        } else {
            for (CacheData cacheElement : cacheDataQueue) {
                if (key == cacheElement.getId()) {
                    cacheDataQueue.remove(cacheElement);
                } else {
                    if (cacheDataQueue.size() == Integer.parseInt(tcCacheSize)) {
                        cacheDataQueue.removeLast();
                    }
                }
            }
        }
        CacheData newCacheData = new CacheData();
        newCacheData.setId(cacheData.getId());
        newCacheData.setData(cacheData.getData());
        cacheDataQueue.addFirst(newCacheData);
    }

    public Object getElementFromCache(int id) {
        for (CacheData cacheElement : cacheDataQueue) {
            if (id == cacheElement.getId()) {
                cacheDataQueue.remove(cacheElement);
                cacheDataQueue.addFirst(cacheElement);
                return cacheElement;
            } else {
                log.debug("Element doesn't exists with id: " + id);
            }
        }
        return null;
    }
}