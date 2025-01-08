package com.breitling.jclib;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages="com.breitling.jclib")
// @EnableCaching
public class JCLibApplication 
{
    public static void main(String... args) 
    {
        SpringApplication.run(JCLibApplication.class, args);
    }
}
