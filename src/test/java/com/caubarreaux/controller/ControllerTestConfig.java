package com.caubarreaux.controller;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * User: ross
 * Date: 5/25/17
 * Time: 11:42 AM
 */

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {"com.caubarreaux.controller"})
public class ControllerTestConfig {

}
