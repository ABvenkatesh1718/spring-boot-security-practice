package com.venkatesh.main.controller;


import com.venkatesh.main.dto.user.AuthRequest;
import com.venkatesh.main.util.JWTUtility;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "v1")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JWTUtility jwtUtility;

    public AuthController(AuthenticationManager authenticationManager, JWTUtility jwtUtility) {
        this.authenticationManager = authenticationManager;
        this.jwtUtility = jwtUtility;
    }

    @PostMapping(path = "/public/authenticate")
    public ResponseEntity<String> authenticate(@RequestBody AuthRequest authRequest) {
        try {
            String username = authRequest.getUsername();
            String password = authRequest.getPassword();
            Authentication authenticate = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );
            return ResponseEntity.ok(jwtUtility.generateToken(username));
        } catch (org.springframework.security.authentication.BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }
    }

}
