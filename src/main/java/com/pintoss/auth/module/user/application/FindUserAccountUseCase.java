package com.pintoss.auth.module.user.application;

import com.pintoss.auth.module.user.core.UserReader;
import com.pintoss.auth.module.user.domain.Phone;
import com.pintoss.auth.module.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FindUserAccountUseCase {

    private final UserReader userReader;

    public String findAccount(String name, String phoneValue) {
        Phone phone = new Phone(phoneValue);
        User findUser = userReader.readByNameAndPhone(name, phone);

        return findUser.getEmail();

    }
}
