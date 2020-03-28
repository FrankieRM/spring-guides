package com.example.restservice;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

import static java.lang.String.format;

@RestController
public class GreetingController {

    private static final String DEFAULT_VALUE = "World";
    private static final String NAME = "/greeting";
    private static final String TEMPLATE = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @GetMapping(NAME)
    public Greeting greeting(@RequestParam(defaultValue = DEFAULT_VALUE) String name) {
        return new Greeting(counter.incrementAndGet(), format(TEMPLATE, name));
    }
}