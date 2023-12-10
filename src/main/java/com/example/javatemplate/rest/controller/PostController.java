package com.example.javatemplate.rest.controller;

import com.example.javatemplate.service.DataService;
import com.example.javatemplate.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PostController {

    @Autowired
    DataService dataService;
    @Autowired
    UserService userService;

    @GetMapping("/posts/{id}")
    public Object posts(@PathVariable("id") String id ) {
        return dataService.fetchPost(id);
    }
    @GetMapping("/posts")
    public Object posts() {
        return userService.fetchUserDetails();
    }
    @GetMapping("/user")
    public Object user() {
        return dataService.fetchPost();

    }
}
