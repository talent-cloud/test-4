package com.tc.cache.integration;

import com.tc.cache.controller.CacheController;
import com.tc.cache.model.CacheData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Deque;

import static org.assertj.core.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class IntegrationTests {

    @Autowired
    CacheController cacheController;

    @Test
    void testCachePut() {
        CacheData cacheData = new CacheData();
        cacheData.setId(0);
        cacheData.setData("A");
        ResponseEntity<Void> voidResponseEntity = cacheController.cachePut(cacheData);
        assertThat(cacheData).hasFieldOrPropertyWithValue("id", 0)
                .hasFieldOrPropertyWithValue("data", "A");
        ResponseEntity<Deque<CacheData>> dequeResponseEntity = cacheController.cacheIdGet(cacheData.getId());
        assertThat(dequeResponseEntity).isNotNull();
    }
}
