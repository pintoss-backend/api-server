package com.pintoss.auth.module.user.process;

import com.pintoss.auth.module.user.process.domain.User;
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
