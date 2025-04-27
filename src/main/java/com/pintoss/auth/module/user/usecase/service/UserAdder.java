package com.pintoss.auth.module.user.usecase.service;

import com.pintoss.auth.module.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserAdder {

    private final UserRepository userRepository;

    public User add(User user) {
        userRepository.save(user);
        return user;
    }

}
