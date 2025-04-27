package com.pintoss.auth.module.user.usecase;

import com.pintoss.auth.common.security.UserRoleEnum;
import com.pintoss.auth.module.user.usecase.service.UserAdder;
import com.pintoss.auth.module.user.usecase.service.UserCreator;
import com.pintoss.auth.module.user.usecase.service.UserValidator;
import com.pintoss.auth.module.user.model.User;
import com.pintoss.auth.module.user.model.UserRole;
import com.pintoss.auth.module.user.usecase.dto.RegisterCommand;
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
