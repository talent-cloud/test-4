package com.tc.cache.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.is;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CacheServiceTest {
	@Autowired
	private MockMvc mvc;

	@Test
	void contextLoads() {
	}

	@Test
	public void testCacheService() throws Exception {
		String cacheData = "{\"id\":1,\"data\":\"A\"}";

		mvc.perform(put("/lru")
				.contentType(MediaType.APPLICATION_JSON)
				.content(cacheData))
				.andExpect(status().isOk());

		MvcResult response = mvc.perform(get("/lru/1")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn();
		assertThat(response.getResponse().getContentAsString()).isEqualTo("A");

		cacheData = "{\"id\":2,\"data\":\"B\"}";

		mvc.perform(put("/lru")
						.contentType(MediaType.APPLICATION_JSON)
						.content(cacheData))
				.andExpect(status().isOk());

		response = mvc.perform(get("/lru/2")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn();
		assertThat(response.getResponse().getContentAsString()).isEqualTo("B");


		cacheData = "{\"id\":3,\"data\":\"C\"}";

		mvc.perform(put("/lru")
						.contentType(MediaType.APPLICATION_JSON)
						.content(cacheData))
				.andExpect(status().isOk());

		response = mvc.perform(get("/lru/3")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn();
		assertThat(response.getResponse().getContentAsString()).isEqualTo("C");


		response = mvc.perform(get("/lru/1")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn();
		assertThat(response.getResponse().getContentAsString()).isEqualTo("");
	}
}
