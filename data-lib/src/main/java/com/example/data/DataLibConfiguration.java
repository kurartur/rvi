package com.example.data;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan(basePackageClasses = DataLibConfiguration.class)
@PropertySource("classpath:defaults.properties")
public class DataLibConfiguration {

}
