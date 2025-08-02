package com.pintoss.auth.core.user.application;

import com.pintoss.auth.storage.user.jpa.entity.UserRoleEnum;
import com.pintoss.auth.storage.user.jpa.entity.User;
import com.pintoss.auth.storage.user.jpa.entity.UserRole;
import com.pintoss.auth.core.user.application.dto.RegisterCommand;
import com.pintoss.auth.core.user.application.flow.writer.UserAdder;
import com.pintoss.auth.core.user.application.flow.processor.UserCreateProcessor;
import com.pintoss.auth.core.user.application.flow.validator.UserValidator;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegisterUseCase {

    private final UserValidator userValidator;
    private final UserAdder userAdder;
    private final UserCreateProcessor userCreateProcessor;

    public void register(RegisterCommand command) {
        userValidator.duplicateEmail(command.getEmail());

        User user = userCreateProcessor.register(
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
