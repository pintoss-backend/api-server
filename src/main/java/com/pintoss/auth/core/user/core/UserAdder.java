package com.pintoss.auth.core.user.core;

import com.pintoss.auth.core.user.domain.User;
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
