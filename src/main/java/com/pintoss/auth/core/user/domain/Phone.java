package com.pintoss.auth.core.user.domain;

import com.pintoss.auth.support.exception.EmptyPhoneException;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;
import java.util.regex.Pattern;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Phone {

    // private static final String PHONE_REGEX = "^010-?\\d{4}-?\\d{4}$";
    private static final String PHONE_REGEX = "^010-\\d{4}-\\d{4}$";;
    private static final Pattern PHONE_PATTERN = Pattern.compile(PHONE_REGEX);

    private String phone;

    /**
     * Constructs a Phone object after validating that the provided phone number is not null or blank.
     *
     * @param phone the phone number string to be encapsulated
     * @throws EmptyPhoneException if the phone number is null or blank
     */
    public Phone(String phone) {
        validate(phone);
        this.phone = phone;
    }

    /**
     * Validates that the provided phone number string is not null or blank.
     *
     * @param value the phone number string to validate
     * @throws EmptyPhoneException if the phone number is null or blank
     */
    private void validate(String value) {
        if(value == null || value.isBlank()) {
            throw new EmptyPhoneException("전화번호는 필수 입력 값 입니다..");
        }
    }

    /**
     * Determines whether this Phone object is equal to another object based on the phone number value.
     *
     * @param o the object to compare with this Phone instance
     * @return true if the specified object is a Phone with the same phone number, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        Phone target = (Phone) o;
        return Objects.equals(phone, target.phone);
    }

    /**
     * Returns a hash code value for this Phone based on its phone number.
     *
     * @return the hash code of the phone number
     */
    @Override
    public int hashCode() {
        return  Objects.hash(phone);
    }

    /**
     * Returns the phone number as a string.
     *
     * @return the phone number value
     */
    @Override
    public String toString() {
        return phone;
    }
}

