package com.caubarreaux.integration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * User: ross
 * Date: 5/29/17
 * Time: 7:29 AM
 */

@Configuration
@ComponentScan(basePackages = {"com.caubarreaux.service"})
public abstract class IntegrationTestConfig {

}
