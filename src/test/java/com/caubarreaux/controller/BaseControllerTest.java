package com.caubarreaux.controller;

import com.caubarreaux.service.WordService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.web.config.SpringDataWebConfiguration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;

/**
 * User: ross
 * Date: 5/25/17
 * Time: 12:07 PM
 */

@WebAppConfiguration
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {ControllerTestConfig.class, SpringDataWebConfiguration.class})
public abstract class BaseControllerTest {

    @Autowired
    private WebApplicationContext context;

    public MockMvc mockMvc;

    @MockBean
    public WordService wordService;

//    public void resetMocks() {
//        Mockito.reset(wordService);
//    }

    public void setUp() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
//        resetMocks();
    }

    public static byte[] convertObjectToJsonBytes(Object object) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsBytes(object);
    }
}
