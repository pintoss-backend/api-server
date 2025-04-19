package com.pintoss.auth.module.user.process;

import com.pintoss.auth.module.user.process.domain.LoginType;
import com.pintoss.auth.module.user.process.domain.Phone;
import com.pintoss.auth.module.user.process.domain.User;
import com.pintoss.auth.module.user.process.domain.UserRole;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserCreator {

    private final PasswordEncoder passwordEncoder;

    public User register(String email, String password, String name, String phoneValue, LoginType loginType, Set<UserRole> roles) {
        Phone phone = new Phone(
            loginType == LoginType.LOCAL ?
                phoneValue : "RANDOM_PASSWORD1!"
        );
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
