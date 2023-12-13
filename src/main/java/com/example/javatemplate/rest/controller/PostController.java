package com.example.javatemplate.rest.controller;

import com.example.javatemplate.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PostController {

    @Autowired
    DataService dataService;

    @GetMapping("/bitcoin")
    public Object user(@RequestParam String startTime, @RequestParam String endTime) {
        return dataService.getBitCoinPrice(startTime, endTime);
    }
}
