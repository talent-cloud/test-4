package com.tc.cache.service;

import com.tc.cache.model.CacheData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
public class CacheServiceTests {

    @InjectMocks
    CacheService cacheService;

    @InjectMocks
    Environment environment;

    @Test
    public void testCachePut() {
        CacheData cacheData = new CacheData();
        cacheData.setId(0);
        cacheData.setData("A");
        ResponseEntity<Void> voidResponseEntity = cacheService.cachePut(cacheData);
    }
}
