package com.hammasir.routingreport.component;

import com.hammasir.routingreport.model.dto.ReportDto;
import com.hammasir.routingreport.model.entity.User;
import com.hammasir.routingreport.repository.UserRepository;

import java.util.Optional;

public class UserFactory {

    private final UserRepository userRepository;

    public UserFactory(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findUser(ReportDto report) {
        Optional<User> user = userRepository.findByUsername(report.getUsername());
        if (user.isPresent()) {
            return user.get();
        } else {
            throw new IllegalArgumentException("User is NOT found!");
        }
    }
}
