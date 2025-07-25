package com.pintoss.auth.api.support.converter;

import com.pintoss.auth.storage.user.jpa.entity.LoginType;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class LoginTypeConverter implements Converter<String, LoginType> {
    @Override
    public LoginType convert(String source) {
        return LoginType.fromString(source);
    }
}

