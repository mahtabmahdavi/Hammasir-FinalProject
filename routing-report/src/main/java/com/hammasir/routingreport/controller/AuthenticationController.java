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

    @PostMapping(value = "/signup")
    public ResponseEntity<AuthenticatedResponse> signup(@RequestBody RegisteredRequest request) {
        return ResponseEntity.ok(authenticationService.signupUser(request));
    }

    @PostMapping(value = "/login")
    public ResponseEntity<AuthenticatedResponse> login(@RequestBody AuthenticatedRequest request) {
        return ResponseEntity.ok(authenticationService.loginUser(request));
    }
}
