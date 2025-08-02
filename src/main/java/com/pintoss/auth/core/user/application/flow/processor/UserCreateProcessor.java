package com.pintoss.auth.core.user.application.flow.processor;

import com.pintoss.auth.core.user.application.flow.external.PasswordEncoderWrapper;
import com.pintoss.auth.storage.user.jpa.entity.LoginType;
import com.pintoss.auth.storage.user.jpa.entity.Phone;
import com.pintoss.auth.storage.user.jpa.entity.User;
import com.pintoss.auth.storage.user.jpa.entity.UserRole;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserCreateProcessor {

    private final PasswordEncoderWrapper passwordEncoder;

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
