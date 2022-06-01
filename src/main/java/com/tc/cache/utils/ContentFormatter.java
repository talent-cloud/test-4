package com.tc.cache.utils;

import com.tc.cache.model.CacheData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Queue;
import java.util.stream.Collectors;

@Slf4j
@Component
public class ContentFormatter {

    private ContentFormatter() {
    }

    public static String displayCacheContent(Queue<CacheData> cacheDataQueue) {
        String cacheContent = cacheDataQueue
                .stream()
                .map(cacheElement -> cacheElement.getId()+", "+cacheElement.getData()+"\n")
                .collect(Collectors.joining());
        log.info("\nCache content: \n"+cacheContent);
        log.debug("\nCache content: \n"+cacheContent);
        return cacheContent;
    }

}
