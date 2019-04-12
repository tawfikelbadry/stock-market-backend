package com.gradteam.porsaty.controller;

import org.springframework.web.bind.annotation.*;

@RestController
public class ExceptionHandlerResource {

//    @GetMapping("/access-denied")
//    public String getMessage(){
//        return "<h2>you can't access this resource</h2>" +
//                "<h3>It required full autherized </h3>";
//    }
//
    @GetMapping("/msg")
    public String getError(){
        return "Hello";
    }

}
