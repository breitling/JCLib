package com.breitling.jclib;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@EntityScan("com.breitling.jclib.persistence")
@SpringBootApplication(scanBasePackages="com.breitling.jclib")
//@EnableCaching
public class JCLibApplication 
{

}
