package com.pintoss.auth.module.user.domain;

import com.pintoss.auth.common.exception.client.EmptyPhoneException;
import jakarta.persistence.Embeddable;
import java.util.Objects;
import java.util.regex.Pattern;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Phone {

    // private static final String PHONE_REGEX = "^010-?\\d{4}-?\\d{4}$";
    private static final String PHONE_REGEX = "^010-\\d{4}-\\d{4}$";;
    private static final Pattern PHONE_PATTERN = Pattern.compile(PHONE_REGEX);

    private String value;

    public Phone(String value) {
        validate(value);
        this.value = value;
    }

    private void validate(String value) {
        if(value == null || value.isBlank()) {
            throw new EmptyPhoneException("전화번호는 필수 입력 값 입니다..");
        }
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        Phone target = (Phone) o;
        return Objects.equals(value, target.value);
    }

    @Override
    public int hashCode() {
        return  Objects.hash(value);
    }

    @Override
    public String toString() {
        return value;
    }
}

