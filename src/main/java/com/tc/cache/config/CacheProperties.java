package com.tc.cache.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class CacheProperties {

    private final Integer size;

	public CacheProperties(@Value("${tc.cache.size}") Integer size) {
		this.size = size;
	}

	public Integer getSize() {
		return this.size;
	}

}
