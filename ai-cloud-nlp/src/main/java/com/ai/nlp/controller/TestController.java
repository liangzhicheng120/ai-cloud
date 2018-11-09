package com.ai.nlp.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liangzhicheng
 * @date 2018/11/9
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/get")
    public String get() {
        return "get";
    }

}
