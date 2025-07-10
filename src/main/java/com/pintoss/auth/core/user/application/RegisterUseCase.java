package com.pintoss.auth.core.user.application;

import com.pintoss.auth.core.user.domain.UserRoleEnum;
import com.pintoss.auth.core.user.domain.User;
import com.pintoss.auth.core.user.domain.UserRole;
import com.pintoss.auth.core.user.application.dto.RegisterCommand;
import com.pintoss.auth.core.user.core.UserAdder;
import com.pintoss.auth.core.user.core.UserCreator;
import com.pintoss.auth.core.user.core.UserValidator;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegisterUseCase {

    private final UserValidator userValidator;
    private final UserAdder userAdder;
    private final UserCreator userCreator;

    public void register(RegisterCommand command) {
        userValidator.duplicateEmail(command.getEmail());

        User user = userCreator.register(
            command.getEmail(),
            command.getPassword(),
            command.getName(),
            command.getPhone(),
            command.getLoginType(),
            Set.of(new UserRole(UserRoleEnum.USER))
        );

        userAdder.add(user);
    }
}
