package com.caubarreaux.service;

import com.caubarreaux.integration.WordRepository;

import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * User: ross
 * Date: 5/20/17
 * Time: 10:49 AM
 */

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {ServiceTestConfig.class})
public abstract class BaseServiceTest {

    @MockBean
    public WordRepository wordRepository;
}
