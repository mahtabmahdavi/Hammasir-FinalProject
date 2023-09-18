package com.hammasir.routingreport.service;

import com.hammasir.routingreport.model.auth.AuthenticatedRequest;
import com.hammasir.routingreport.model.auth.AuthenticatedResponse;
import com.hammasir.routingreport.model.auth.RegisteredRequest;
import com.hammasir.routingreport.authentication.JwtService;
import com.hammasir.routingreport.model.enums.Role;
import com.hammasir.routingreport.model.entity.User;
import com.hammasir.routingreport.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticatedResponse signUpUser(RegisteredRequest request) {
        User user = User.builder()
                .phoneNumber(request.getPhoneNumber())
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .nickName("کاربر نشان")
                .role(Role.USER)
                .build();
        userRepository.save(user);
        String jwtToken = jwtService.generateToken(user);
        return AuthenticatedResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticatedResponse signInUser(AuthenticatedRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getUsername(), request.getPassword()));
        User user = userRepository.findByUsername(request.getUsername()).orElseThrow();
        String jwtToken = jwtService.generateToken(user);
        return AuthenticatedResponse.builder()
                .token(jwtToken)
                .build();
    }

    public User findUser(String username) {
        Optional<User> desiredUser = userRepository.findByUsername(username);
        if (desiredUser.isPresent()) {
            return desiredUser.get();
        } else {
            throw new IllegalArgumentException("User is NOT found!");
        }
    }
}
