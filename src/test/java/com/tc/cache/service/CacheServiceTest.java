package com.tc.cache.service;

import com.tc.cache.config.CacheProperties;
import com.tc.cache.manager.CacheManager;
import com.tc.cache.model.CacheData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;

class CacheServiceTest {

    @InjectMocks
    private CacheService cacheService;

    @Spy
    private CacheManager cacheManager = new CacheManager(new CacheProperties(3));

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this).close();
    }

    @Test
    void whenCacheIdGetWhereIdExists_thenReturnsObject() {
        Integer id = 4;
        Object data = "A";
        cacheManager.put(id, data);

        ResponseEntity<Object> result = cacheService.cacheIdGet(id);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(data, result.getBody());

        verify(cacheManager).get(anyInt());
        assertEquals("[{" + id + "=" + data + "}]", cacheManager.getContent());
    }

    @Test
    void whenCacheIdGetWhereIdNotExists_thenReturnsNothing() {
        Integer id = 4;

        ResponseEntity<Object> result = cacheService.cacheIdGet(id);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNull(result.getBody());

        verify(cacheManager).get(anyInt());
        assertEquals("[]", cacheManager.getContent());
    }

    @Test
    void whenCachePut_thenReturnOk() {
        Integer id = 1;
        Object data = "A";
        CacheData cacheData = new CacheData().id(id).data(data);

        ResponseEntity<Void> result = cacheService.cachePut(cacheData);
        assertEquals(HttpStatus.OK, result.getStatusCode());

        verify(cacheManager).put(id, data);
        assertEquals("[{" + id + "=" + data + "}]", cacheManager.getContent());
    }
}
