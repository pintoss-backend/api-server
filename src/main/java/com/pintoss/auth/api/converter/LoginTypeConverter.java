package com.pintoss.auth.api.converter;

import com.pintoss.auth.module.user.domain.LoginType;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class LoginTypeConverter implements Converter<String, LoginType> {
    @Override
    public LoginType convert(String source) {
        return LoginType.fromString(source);
    }
}

