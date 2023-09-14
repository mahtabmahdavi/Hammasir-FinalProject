package com.hammasir.routingreport.controller;

import com.hammasir.routingreport.model.auth.AuthenticatedRequest;
import com.hammasir.routingreport.model.auth.AuthenticatedResponse;
import com.hammasir.routingreport.model.auth.RegisteredRequest;
import com.hammasir.routingreport.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "authentication")
@RequiredArgsConstructor
public class AuthenticationController {

    private  final AuthenticationService authenticationService;

    @GetMapping
    public ResponseEntity<String> welcome() {
        return ResponseEntity.ok("Welcome to Routing Report System.");
    }

    @PostMapping(value = "/signup")
    public ResponseEntity<AuthenticatedResponse> signUp(@RequestBody RegisteredRequest request) {
        return ResponseEntity.ok(authenticationService.signUpUser(request));
    }

    @PostMapping(value = "/signin")
    public ResponseEntity<AuthenticatedResponse> signIn(@RequestBody AuthenticatedRequest request) {
        return ResponseEntity.ok(authenticationService.signInUser(request));
    }
}
