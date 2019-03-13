package com.laattre.spring;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({ "com.laattre.service" })
public class ServiceConfig {
}
