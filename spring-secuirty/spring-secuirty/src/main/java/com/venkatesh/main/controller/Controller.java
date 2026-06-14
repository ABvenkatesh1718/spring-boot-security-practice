package com.venkatesh.main.controller;


import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping(path = "v1")
public class Controller {

    //Below 2 API are public API
    @GetMapping(path = "public/greetMessage")
    public String greetMessage(){
        return "Welcome to Spring-Security Project";
    }

    @GetMapping(path = "public/homePage")
    public String homePage(){
        return "Welcome to home page";
    }
    //Below are private API
    @PostMapping(path = "/registerUser/{username}")
    public String registerUser(
            @PathVariable String username){
        return "Thanks , for Registration "+ username;
    }

    @GetMapping(path = "/dashboard/{username}")
    public String dashboard(
            @PathVariable String username){
        return "Welcome to home page "+username;
    }
}
