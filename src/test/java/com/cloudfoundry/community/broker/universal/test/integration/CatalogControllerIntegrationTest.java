package com.cloudfoundry.community.broker.universal.test.integration;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.cloudfoundry.community.broker.universal.controller.CatalogController;
import com.cloudfoundry.community.broker.universal.service.CatalogService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
@WebAppConfiguration
public class CatalogControllerIntegrationTest {
	
	@Autowired
    private WebApplicationContext ctx;
	MockMvc mockMvc;

	@Before
	public void setup() {
		/*
		MockitoAnnotations.initMocks(this);

	    this.mockMvc = MockMvcBuilders.standaloneSetup(controller)
	            .setMessageConverters(new MappingJackson2HttpMessageConverter()).build();
	            */
		this.mockMvc = MockMvcBuilders.webAppContextSetup(ctx).build();
	}
	
	@Test
	public void catalogIsRetrievedCorrectly() throws Exception {
	
		MvcResult result = this.mockMvc.perform(get(CatalogController.BASE_PATH)
		        .accept(MediaType.APPLICATION_JSON))
		        .andExpect(status().isOk())
	            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
	            .andReturn();
	    
	    // TO DO - check rest of the json including plans
		String content = result.getResponse().getContentAsString();
		
		assertNotNull(content);
	}
	
    @Configuration
    @EnableWebMvc
    public static class TestConfiguration {
 
        @Bean
        public CatalogController catalogController() throws Exception {
            return new CatalogController();
        }
    }
}
