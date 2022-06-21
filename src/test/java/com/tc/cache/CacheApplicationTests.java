package com.tc.cache;

import com.tc.cache.controller.CacheController;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class CacheApplicationTests {

	@Autowired
	CacheController cacheController;

	@Test
	void contextLoads() {
		Assertions.assertThat(cacheController).isNotNull();
	}

}
