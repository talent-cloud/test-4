package com.tc.cache.controller;

import com.google.gson.Gson;
import com.tc.cache.model.CacheData;
import com.tc.cache.service.CacheService;
import com.tc.cache.utils.ContentFormatter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.Charset;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = CacheController.class)
class CacheControllerTests {

    @MockBean
    private CacheService cacheService;

    @MockBean
    private ContentFormatter contentFormatter;

    @Autowired
    private MockMvc mockMvc;

    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    @Test
    void testCachePut() throws Exception {
        CacheData cacheData = new CacheData();
        cacheData.setId(0);
        cacheData.setData("A");

        Gson gson = new Gson();
        String requestJson = gson.toJson(cacheData);

        mockMvc.perform(put("/api/v1/caches").contentType(APPLICATION_JSON_UTF8)
                        .content(requestJson))
                .andExpect(status().isOk());
    }

//    @Test
//    void testCacheIdGet() throws Exception {
//        CacheData cacheData = new CacheData();
//        cacheData.setId(0);
//        cacheData.setData("A");
//
//        Gson gson = new Gson();
//        String requestJson = gson.toJson(cacheData);
//
//        mockMvc.perform(get("/api/v1/caches").contentType(APPLICATION_JSON_UTF8)
//                        .content(requestJson))
//                .andExpect(status().isOk());
//    }
}
