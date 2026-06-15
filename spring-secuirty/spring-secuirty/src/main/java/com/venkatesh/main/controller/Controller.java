package com.venkatesh.main.controller;


import com.venkatesh.main.dto.user.UserRegisterRequest;
import com.venkatesh.main.service.UserServiceInfo;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping(path = "v1")
public class Controller {

    private final UserServiceInfo userServiceInfo;

    public Controller(UserServiceInfo userServiceInfo) {
        this.userServiceInfo = userServiceInfo;
    }

    // 🟢 Public APIs: Handled globally in your SecurityFilterChain permitAll() configuration
    @GetMapping(path = "public/greetMessage")
    public String greetMessage() { return "Welcome to Spring-Security Project"; }

    @GetMapping(path = "public/homePage")
    public String homePage() { return "Welcome to home page"; }


    // 🟠 PRIVATE APIS: Controlled via strict role/permission checks

    @PostMapping(path = "/registerUser/{username}")
    @PreAuthorize("hasAuthority('DATA_WRITE')") // 🎯 REQUIRES WRITE: Only Admin gets past this!
    public String registerUser(@PathVariable String username) {
        return "Thanks, for Registration " + username;
    }

    @GetMapping(path = "/dashboard/{username}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')") // 🎯 BOTH ROLES ALLOWED: Everyone who logs in has access
    public String dashboard(@PathVariable String username) {
        return "Welcome to dashboard page " + username;
    }

    @PostMapping("/public/addUser")
    @PreAuthorize("hasRole('ADMIN')") // 🎯 ROLE RESTRICTION: Purely for corporate administrators
    public ResponseEntity<String> addUser(@RequestBody UserRegisterRequest userRegisterRequest) {
        // ... your database registration logic ...
        return ResponseEntity.ok("user added Successfully");
    }
//    //Below 2 API are public API
//    @GetMapping(path = "public/greetMessage")
//    public String greetMessage(){
//        return "Welcome to Spring-Security Project";
//    }
//
//    @GetMapping(path = "public/homePage")
//    public String homePage(){
//        return "Welcome to home page";
//    }
//    //Below are private API
//    @PostMapping(path = "/registerUser/{username}")
//    public String registerUser(
//            @PathVariable String username){
//        return "Thanks , for Registration "+ username;
//    }
//
//    @GetMapping(path = "/dashboard/{username}")
//    public String dashboard(
//            @PathVariable String username){
//        return "Welcome to home page "+username;
//    }
//
//    @PostMapping("/public/addUser")
//    public ResponseEntity<String> addUser(@RequestBody UserRegisterRequest userRegisterRequest) throws UserAlreadyExistsException {
//        String username = userRegisterRequest.getUsername();
//        String password = userRegisterRequest.getPassword();
//        userServiceInfo.addUser(username, password);
//        return ResponseEntity.ok("user added Successfully");
//    }
}
