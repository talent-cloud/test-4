package com.tc.cache.manager;

import com.tc.cache.config.CacheProperties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class CacheManagerTest {

    @InjectMocks
    @Spy
    private CacheManager cacheManager;

    @Spy
    private CacheProperties cacheProperties = new CacheProperties(2);

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this).close();
    }

    @Test
    void whenPut_thenReturnOk() {
        Integer id = 4;
        Object data = "A";

        cacheManager.put(id, data);

        assertEquals("[{4=A}]", cacheManager.getContent());

        verify(cacheManager, times(1)).put(id, data);
    }

    @Test
    void whenPutWhereKeyExists_thenUpdateExistingCacheAndMoveToStart() {
        Integer id1 = 1;
        Object data1 = "A";
        int id2 = 2;
        Object data2 = "B";
        Object data3 = "C";

        cacheManager.put(id1, data1);
        assertEquals("[{" + id1 + "=" + data1 + "}]", cacheManager.getContent());

        cacheManager.put(id2, data2);
        assertEquals("[{" + id2 + "=" + data2 + "}, {" + id1 + "=" + data1 + "}]", cacheManager.getContent());

        cacheManager.put(id1, data1);
        assertEquals("[{" + id1 + "=" + data1 + "}, {" + id2 + "=" + data2 + "}]", cacheManager.getContent());

        cacheManager.put(id1, data3);
        assertEquals("[{" + id1 + "=" + data3 + "}, {" + id2 + "=" + data2 + "}]", cacheManager.getContent());

        Object data = cacheManager.get(id2);
        assertEquals(data2, data);

        assertEquals("[{" + id2 + "=" + data2 + "}, {" + id1 + "=" + data3 + "}]", cacheManager.getContent());
    }

    @Test
    void whenGetWhereIdExists_thenReturnsObject() {
        Integer id = 4;
        Object data = "A";

        cacheManager.put(id, data);

        Object result = cacheManager.get(id);
        assertEquals(data, result);

        verify(cacheManager, times(1)).get(id);
    }

    @Test
    void whenGetWhereIdNotExists_thenReturnsNothing() {
        Integer id = 4;

        Object result = cacheManager.get(id);
        assertNull(result);

        verify(cacheManager).get(id);
    }

    @Test
    void whenGetWhereKeyNotExists_thenReturnEmpty() {
        Integer id = 1;
        Object data = cacheManager.get(id);
        assertNull(data);

        verify(cacheManager, times(1)).get(id);
    }
}
