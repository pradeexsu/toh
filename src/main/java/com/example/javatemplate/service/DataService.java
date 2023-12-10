package com.example.javatemplate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class DataService {

    @Autowired
    RestTemplate restTemplate;


    public Object fetchPost(String id){
        return restTemplate.getForObject(
                "https://jsonplaceholder.typicode.com/posts/"+id, Object.class);
    }
    public Object fetchPost(){
        return restTemplate.getForObject(
                "https://jsonplaceholder.typicode.com/posts", Object.class);
    }
}
