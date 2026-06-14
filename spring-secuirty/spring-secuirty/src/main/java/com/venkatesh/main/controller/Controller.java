package com.venkatesh.main.controller;


import com.venkatesh.main.dto.user.UserRegisterRequest;
import com.venkatesh.main.exception.UserAlreadyExistsException;
import com.venkatesh.main.service.UserServiceInfo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping(path = "v1")
public class Controller {

    private final UserServiceInfo userServiceInfo;

    public Controller(UserServiceInfo userServiceInfo) {
        this.userServiceInfo = userServiceInfo;
    }

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

    @PostMapping("/public/addUser")
    public ResponseEntity<String> addUser(@RequestBody UserRegisterRequest userRegisterRequest) throws UserAlreadyExistsException {
        String username = userRegisterRequest.getUsername();
        String password = userRegisterRequest.getPassword();
        userServiceInfo.addUser(username, password);
        return ResponseEntity.ok("user added Successfully");
    }
}
