package com.pintoss.auth.core.user.application.flow.processor;

import com.pintoss.auth.core.user.domain.LoginType;
import com.pintoss.auth.core.user.domain.Phone;
import com.pintoss.auth.core.user.domain.User;
import com.pintoss.auth.core.user.domain.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class UserCreateProcessor {

    private final PasswordEncoder passwordEncoder;

    public User register(String email, String password, String name, String phoneValue, LoginType loginType, Set<UserRole> roles) {
        Phone phone = new Phone(phoneValue);

        return User.register(
            email,
            passwordEncoder.encode(password),
            name,
            phone,
            loginType,
            roles
        );
    }

}
