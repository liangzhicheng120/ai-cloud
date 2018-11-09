package com.ai.gategory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@SpringBootApplication
@EnableZuulProxy
public class GategoryApplication {

    public static void main(String[] args) {
        SpringApplication.run(GategoryApplication.class, args);
    }
}
