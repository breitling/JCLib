package com.breitling.jclib;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;

@EntityScan("com.breitling.jclib.persistence")
@SpringBootApplication(scanBasePackages="com.breitling.jclib")
@EnableCaching
public class JCLibApplication 
{
    public static void main(String... args) 
    {
        SpringApplication.run(JCLibApplication.class, args);
    }
}
