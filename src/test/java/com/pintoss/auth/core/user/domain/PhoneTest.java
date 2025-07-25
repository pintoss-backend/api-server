package com.pintoss.auth.core.user.domain;

import jakarta.persistence.*;
import jakarta.validation.*;
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
class PhoneTest {

    @Autowired
    EntityManager em;

    Validator validator;

    @BeforeEach
    void setup() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    @DisplayName("전화번호가 null인 경우 예외가 발생한다.")
    void givenNullPhone_whenCreate_thenThrowException() {
        // given
        String phone = null;

        // when
        TestEntity entity = new TestEntity(phone);

        // when, then
        Set<ConstraintViolation<TestEntity>> violations = validator.validate(entity);
        assertThat(violations).hasSize(1);
        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("phone.phone"));
    }

    @Test
    @DisplayName("전화번호가 빈 문자열인 경우 예외가 발생한다.")
    void givenEmptyPhone_whenCreate_thenThrowException() {
        // given
        String phone = "";

        // when
        TestEntity entity = new TestEntity(phone);

        // when, then
        Set<ConstraintViolation<TestEntity>> violations = validator.validate(entity);
        assertThat(violations).hasSize(1);
        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("phone.phone"));
    }

    @Test
    @DisplayName("전화번호가 저장될 때 암호화되어 저장된다")
    void givenPhone_whenPersist_thenPhoneIsEncrypted() {
        // given
        String rawPhone = "010-1234-5678";
        TestEntity entity = new TestEntity(rawPhone);
        em.persist(entity);
        em.flush();
        em.clear();

        // when
        String encryptedPhone = (String) em
                .createNativeQuery("SELECT phone FROM phone_test WHERE id = :id")
                .setParameter("id", entity.getId())
                .getSingleResult();

        // then
        assertThat(encryptedPhone).isNotEqualTo(rawPhone);
    }

    @Test
    @DisplayName("암호화된 전화번호를 다시 조회할 때는 복호화되어야 한다")
    void givenEncryptedPhone_whenFind_thenPhoneIsDecrypted() {
        // given
        String rawPhone = "010-1234-5678";
        TestEntity entity = new TestEntity(rawPhone);
        em.persist(entity);
        em.flush();
        em.clear();

        // when
        TestEntity foundEntity = em.find(TestEntity.class, entity.getId());
        String decryptedPhone = foundEntity.getPhone().getPhone();

        // then
        assertThat(decryptedPhone).isEqualTo(rawPhone);

        String encryptedPhone = (String) em
                .createNativeQuery("SELECT phone FROM phone_test WHERE id = :id")
                .setParameter("id", entity.getId())
                .getSingleResult();
        assertThat(encryptedPhone).isNotEqualTo(rawPhone);
    }

    @Entity
    @Table(name = "phone_test")
    static class TestEntity {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Embedded
        @Valid
        private Phone phone;

        protected TestEntity() { }

        public TestEntity(String phoneNumber) {
            this.phone = new Phone(phoneNumber);
        }

        public Long getId() {
            return id;
        }

        public Phone getPhone() {
            return phone;
        }
    }

}
