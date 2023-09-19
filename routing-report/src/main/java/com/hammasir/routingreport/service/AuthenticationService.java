package com.hammasir.routingreport.service;

import com.hammasir.routingreport.model.authentication.AuthenticatedRequest;
import com.hammasir.routingreport.model.authentication.AuthenticatedResponse;
import com.hammasir.routingreport.model.authentication.RegisteredRequest;
import com.hammasir.routingreport.security.JwtService;
import com.hammasir.routingreport.model.enumuration.Role;
import com.hammasir.routingreport.model.entity.User;
import com.hammasir.routingreport.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticatedResponse signupUser(RegisteredRequest request) {
        User user = User.builder()
                .phoneNumber(request.getPhoneNumber())
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .nickName("Neshan User")
                .role(Role.USER)
                .build();
        userRepository.save(user);
        String jwtToken = jwtService.generateToken(user);
        return AuthenticatedResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticatedResponse loginUser(AuthenticatedRequest request) {
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
