package com.pintoss.auth.core.user.domain;

import jakarta.persistence.EntityManager;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
@ActiveProfiles("test")
class UserTest {

    @Autowired
    EntityManager em;

    Validator validator;

    @BeforeEach
    void setup() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    @DisplayName("사용자 이메일이 null인 경우 예외가 발생한다.")
    void givenNullEmail_whenCreate_thenThrowException() {
        // given
        String email = null;

        // when
        User user = User.register(
                email,
                "password123",
                "John Doe",
                new Phone("010-1234-5678"),
                LoginType.LOCAL,
                Set.of(new UserRole(UserRoleEnum.USER))
        );

        // then
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertThat(violations).hasSize(1);
        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("email"));
    }

    @Test
    @DisplayName("이메일이 빈 문자열인 경우 예외가 발생한다.")
    void givenEmptyEmail_whenCreate_thenThrowException() {
        // given
        String email = "";

        // when
        User user = User.register(
                email,
                "password123",
                "John Doe",
                new Phone("010-1234-5678"),
                LoginType.LOCAL,
                Set.of(new UserRole(UserRoleEnum.USER))
        );

        // then
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertThat(violations).hasSize(1);
        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("email"));
    }

    @Test
    @DisplayName("비밀번호가 null인 경우 예외가 발생한다.")
    void givenNullPassword_whenCreate_thenThrowException() {
        // given
        String password = null;

        // when
        User user = User.register(
                "testemail@gmail.com",
                password,
                "John Doe",
                new Phone("010-1234-5678"),
                LoginType.LOCAL,
                Set.of(new UserRole(UserRoleEnum.USER))
        );

        // then
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertThat(violations).hasSize(1);
        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("password"));
    }

    @Test
    @DisplayName("비밀번호가 빈 문자열인 경우 예외가 발생한다.")
    void givenEmptyPassword_whenCreate_thenThrowException() {
        // given
        String password = "";

        // when
        User user = User.register(
                "testemail@gmail.com",
                password,
                "John Doe",
                new Phone("010-1234-5678"),
                LoginType.LOCAL,
                Set.of(new UserRole(UserRoleEnum.USER))
        );

        // then
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertThat(violations).hasSize(1);
        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("password"));
    }

    @Test
    @DisplayName("사용자 이름이 null인 경우 예외가 발생한다.")
    void givenNullName_whenCreate_thenThrowException() {
        // given
        String name = null;

        // when
        User user = User.register(
                "testemail@gmail.com",
                "password123",
                name,
                new Phone("010-1234-5678"),
                LoginType.LOCAL,
                Set.of(new UserRole(UserRoleEnum.USER))
        );

        // then
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertThat(violations).hasSize(1);
        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("name"));
    }

    @Test
    @DisplayName("사용자 이름이 빈 문자열인 경우 예외가 발생한다.")
    void givenEmptyName_whenCreate_thenThrowException() {
        // given
        String name = "";

        // when
        User user = User.register(
                "testemail@gmail.com",
                "password123",
                name,
                new Phone("010-1234-5678"),
                LoginType.LOCAL,
                Set.of(new UserRole(UserRoleEnum.USER))
        );

        // then
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertThat(violations).hasSize(1);
        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("name"));
    }

    @Test
    @DisplayName("이메일은 저장될 때 암호화되어 저장된다")
    void givenEmail_whenPersist_thenEmailIsEncrypted() {
        // given
        User user = User.register(
                "testemail@gmail.com",
                "password123",
                "John Doe",
                new Phone("010-1234-5678"),
                LoginType.LOCAL,
                Set.of(new UserRole(UserRoleEnum.USER))
        );
        em.persist(user);
        em.flush();
        em.clear();

        // when
        String encryptedEmail = (String) em.createNativeQuery("select email from users where id = :id")
                .setParameter("id", user.getId())
                .getSingleResult();

        // then
        assertThat(encryptedEmail).isNotEqualTo(user.getEmail());
    }

    @Test
    @DisplayName("암호화된 이메일을 다시 조회할 때는 복호화되어야 한다")
    void givenEncryptedEmail_whenFind_thenEmailIsDecrypted() {
        // given
        User user = User.register(
                "testemail@gmail.com",
                "password123",
                "John Doe",
                new Phone("010-1234-5678"),
                LoginType.LOCAL,
                Set.of(new UserRole(UserRoleEnum.USER))
        );
        em.persist(user);
        em.flush();
        em.clear();

        // when
        String decryptedEmail = em.find(User.class, user.getId()).getEmail();

        // then
        assertThat(decryptedEmail).isEqualTo(user.getEmail());

        String encryptedEmail = (String) em.createNativeQuery("select email from users where id = :id")
                .setParameter("id", user.getId())
                .getSingleResult();
        assertThat(encryptedEmail).isNotEqualTo(user.getEmail());
    }

    @Test
    @DisplayName("refreshToken을 저장할 때 암호화되어 저장된다")
    void givenRefreshToken_whenPersist_thenRefreshTokenIsEncrypted() {
        // given
        User user = User.register(
                "testemail@gmail.com",
                "password123",
                "John Doe",
                new Phone("010-1234-5678"),
                LoginType.LOCAL,
                Set.of(new UserRole(UserRoleEnum.USER))
        );
        user.storeRefreshToken("test-refresh-token");
        em.persist(user);
        em.flush();
        em.clear();

        // when
        String encryptedRefreshToken = (String) em.createNativeQuery("select refresh_token from users where id = :id")
                .setParameter("id", user.getId())
                .getSingleResult();

        // then
        assertThat(encryptedRefreshToken).isNotEqualTo(user.getRefreshToken());

        String decryptedRefreshToken = em.find(User.class, user.getId()).getRefreshToken();
        assertThat(decryptedRefreshToken).isEqualTo(user.getRefreshToken());
    }

    @Test
    @DisplayName("refreshToken을 다시 조회할 때는 복호화되어야 한다")
    void givenEncryptedRefreshToken_whenFind_thenRefreshTokenIsDecrypted() {
        // given
        User user = User.register(
                "testemail@gmail.com",
                "password123",
                "John Doe",
                new Phone("010-1234-5678"),
                LoginType.LOCAL,
                Set.of(new UserRole(UserRoleEnum.USER))
        );
        user.storeRefreshToken("test-refresh-token");
        em.persist(user);
        em.flush();
        em.clear();

        // when
        String decryptedRefreshToken = em.find(User.class, user.getId()).getRefreshToken();

        // then
        assertThat(decryptedRefreshToken).isEqualTo(user.getRefreshToken());
        String encryptedRefreshToken = (String) em.createNativeQuery("select refresh_token from users where id = :id")
                .setParameter("id", user.getId())
                .getSingleResult();
        assertThat(encryptedRefreshToken).isNotEqualTo(user.getRefreshToken());
    }
}
