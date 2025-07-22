package com.pintoss.auth.storage.voucher.jpa.entity;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceException;
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

import java.math.BigDecimal;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Transactional
@SpringBootTest
@ActiveProfiles("test")
class VoucherIssuerEntityTest {

    @Autowired
    EntityManager em;

    private Validator validator;

    @BeforeEach
    void setup() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    @DisplayName("상품권 발급자의 이름이 null이면 유효성 검사에 실패한다")
    void givenVoucherIssuerWithNullName_whenToEntity_thenValidationFails() {
        // when
        VoucherIssuerEntity entity = VoucherIssuerEntity.create(
                null,
                "test code",
                new DiscountEmbeddable(
                        new BigDecimal("10"),
                        new BigDecimal("10")
                ),
                new ContactInfoEmbeddable(
                        new HomePageEmbeddable("test.com"),
                        new CsCenterEmbeddable("123-456-7890")
                ),
                "test description",
                "test publisher",
                "test note",
                "https://example.com/logo.png",
                new BigDecimal("10")
        );

        // then
        Set<ConstraintViolation<VoucherIssuerEntity>> violations = validator.validate(entity);
        assertThat(violations).hasSize(1);
        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("name"));
    }

    @Test
    @DisplayName("상품권 발급자의 이름이 빈 문자열이면 유효성 검사에 실패한다")
    void givenVoucherIssuerWithEmptyName_whenToEntity_thenValidationFails() {
        // when
        VoucherIssuerEntity entity = VoucherIssuerEntity.create(
                "",
                "test code",
                new DiscountEmbeddable(
                        new BigDecimal("10"),
                        new BigDecimal("10")
                ),
                new ContactInfoEmbeddable(
                        new HomePageEmbeddable("test.com"),
                        new CsCenterEmbeddable("123-456-7890")
                ),
                "test description",
                "test publisher",
                "test note",
                "https://example.com/logo.png",
                new BigDecimal("10")
        );

        // then
        Set<ConstraintViolation<VoucherIssuerEntity>> violations = validator.validate(entity);
        assertThat(violations).hasSize(1);
        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("name"));
    }

    @Test
    @DisplayName("상품권 발급자의 이름이 중복되면 안된다")
    void givenVoucherIssuerWithDuplicateName_whenToEntity_thenValidationFails() {
        // given
        VoucherIssuerEntity existingEntity = VoucherIssuerEntity.create(
                "test name",
                "test code",
                new DiscountEmbeddable(
                        new BigDecimal("10"),
                        new BigDecimal("10")
                ),
                new ContactInfoEmbeddable(
                        new HomePageEmbeddable("test.com"),
                        new CsCenterEmbeddable("123-456-7890")
                ),
                "test description",
                "test publisher",
                "test note",
                "https://example.com/logo.png",
                new BigDecimal("10")
        );
        em.persist(existingEntity);
        em.flush();

        // when, then
        VoucherIssuerEntity entity = VoucherIssuerEntity.create(
                "test name", // 중복된 이름
                "test code 2",
                new DiscountEmbeddable(
                        new BigDecimal("10"),
                        new BigDecimal("10")
                ),
                new ContactInfoEmbeddable(
                        new HomePageEmbeddable("test2.com"),
                        new CsCenterEmbeddable("987-654-3210")
                ),
                "test description 2",
                "test publisher 2",
                "test note 2",
                "https://example.com/logo2.png",
                new BigDecimal("20")
        );

        assertThatThrownBy(() -> {
            em.persist(entity);
            em.flush(); // 여기서 예외 발생
        }).isInstanceOf(PersistenceException.class);
    }

    @Test
    @DisplayName("상품권 발급자의 코드가 null이면 유효성 검사에 실패한다")
    void givenVoucherIssuerWithNullCode_whenToEntity_thenValidationFails() {
        // when
        VoucherIssuerEntity entity = VoucherIssuerEntity.create(
                "test name",
                null,
                new DiscountEmbeddable(
                        new BigDecimal("10"),
                        new BigDecimal("10")
                ),
                new ContactInfoEmbeddable(
                        new HomePageEmbeddable("test.com"),
                        new CsCenterEmbeddable("123-456-7890")
                ),
                "test description",
                "test publisher",
                "test note",
                "https://example.com/logo.png",
                new BigDecimal("10")
        );

        // then
        Set<ConstraintViolation<VoucherIssuerEntity>> violations = validator.validate(entity);
        assertThat(violations).hasSize(1);
        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("code"));
    }

    @Test
    @DisplayName("상품권 발급자의 코드가 빈 문자열이면 유효성 검사에 실패한다")
    void givenVoucherIssuerWithEmptyCode_whenToEntity_thenValidationFails() {
        // when
        VoucherIssuerEntity entity = VoucherIssuerEntity.create(
                "test name",
                "",
                new DiscountEmbeddable(
                        new BigDecimal("10"),
                        new BigDecimal("10")
                ),
                new ContactInfoEmbeddable(
                        new HomePageEmbeddable("test.com"),
                        new CsCenterEmbeddable("123-456-7890")
                ),
                "test description",
                "test publisher",
                "test note",
                "https://example.com/logo.png",
                new BigDecimal("10")
        );

        // then
        Set<ConstraintViolation<VoucherIssuerEntity>> violations = validator.validate(entity);
        assertThat(violations).hasSize(1);
        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("code"));
    }

    @Test
    @DisplayName("상품권 발급자의 코드가 중복되면 안된다")
    void givenVoucherIssuerWithDuplicateCode_whenToEntity_thenValidationFails() {
        // given
        VoucherIssuerEntity existingEntity = VoucherIssuerEntity.create(
                "test name",
                "test code",
                new DiscountEmbeddable(
                        new BigDecimal("10"),
                        new BigDecimal("10")
                ),
                new ContactInfoEmbeddable(
                        new HomePageEmbeddable("test.com"),
                        new CsCenterEmbeddable("123-456-7890")
                ),
                "test description",
                "test publisher",
                "test note",
                "https://example.com/logo.png",
                new BigDecimal("10")
        );
        em.persist(existingEntity);
        em.flush();

        // when, then
        VoucherIssuerEntity entity = VoucherIssuerEntity.create(
                "test name 2",
                "test code", // 중복된 코드
                new DiscountEmbeddable(
                        new BigDecimal("10"),
                        new BigDecimal("10")
                ),
                new ContactInfoEmbeddable(
                        new HomePageEmbeddable("test2.com"),
                        new CsCenterEmbeddable("987-654-3210")
                ),
                "test description 2",
                "test publisher 2",
                "test note 2",
                "https://example.com/logo2.png",
                new BigDecimal("20")
        );

        assertThatThrownBy(() -> {
            em.persist(entity);
            em.flush(); // 여기서 예외 발생
        }).isInstanceOf(PersistenceException.class);
    }

    @Test
    @DisplayName("카프 할인 정보가 null이면 유효성 검사에 실패한다")
    void givenVoucherIssuerWithNullDiscount_whenToEntity_thenValidationFails() {
        // when
        VoucherIssuerEntity entity = VoucherIssuerEntity.create(
                "test name",
                "test code",
                new DiscountEmbeddable(
                        null,
                        new BigDecimal("10")
                ),
                new ContactInfoEmbeddable(
                        new HomePageEmbeddable("test.com"),
                        new CsCenterEmbeddable("123-456-7890")
                ),
                "test description",
                "test publisher",
                "test note",
                "https://example.com/logo.png",
                new BigDecimal("10")
        );

        // then
        assertThatThrownBy(() -> {
            em.persist(entity);
            em.flush(); // 여기서 예외 발생
        }).isInstanceOf(PersistenceException.class);
    }

    @Test
    @DisplayName("휴대폰 할인 정보가 null이면 유효성 검사에 실패한다")
    void givenVoucherIssuerWithNullMobileDiscount_whenToEntity_thenValidationFails() {
        // when
        VoucherIssuerEntity entity = VoucherIssuerEntity.create(
                "test name",
                "test code",
                new DiscountEmbeddable(
                        new BigDecimal("10"),
                        null
                ),
                new ContactInfoEmbeddable(
                        new HomePageEmbeddable("test.com"),
                        new CsCenterEmbeddable("123-456-7890")
                ),
                "test description",
                "test publisher",
                "test note",
                "https://example.com/logo.png",
                new BigDecimal("10")
        );

        // then
        assertThatThrownBy(() -> {
            em.persist(entity);
            em.flush(); // 여기서 예외 발생
        }).isInstanceOf(PersistenceException.class);
    }

    @Test
    @DisplayName("홈페이지 URL이 null이면 유효성 검사에 실패한다")
    void givenVoucherIssuerWithNullHomePageUrl_whenToEntity_thenValidationFails() {
        // when
        String url = null;
        VoucherIssuerEntity entity = VoucherIssuerEntity.create(
                "test name",
                "test code",
                new DiscountEmbeddable(
                        new BigDecimal("10"),
                        new BigDecimal("10")
                ),
                new ContactInfoEmbeddable(
                        new HomePageEmbeddable(url),
                        new CsCenterEmbeddable("123-456-7890")
                ),
                "test description",
                "test publisher",
                "test note",
                "https://example.com/logo.png",
                new BigDecimal("10")
        );

        // then
        Set<ConstraintViolation<VoucherIssuerEntity>> violations = validator.validate(entity);
        assertThat(violations).hasSize(1);
        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("contactInfoEmbeddable.homePageEmbeddable.url"));
    }

    @Test
    @DisplayName("고객센터 전화번호가 null이면 유효성 검사에 실패한다")
    void givenVoucherIssuerWithNullCsCenterTel_whenToEntity_thenValidationFails() {
        // when
        String tel = null;
        VoucherIssuerEntity entity = VoucherIssuerEntity.create(
                "test name",
                "test code",
                new DiscountEmbeddable(
                        new BigDecimal("10"),
                        new BigDecimal("10")
                ),
                new ContactInfoEmbeddable(
                        new HomePageEmbeddable("test.com"),
                        new CsCenterEmbeddable(tel)
                ),
                "test description",
                "test publisher",
                "test note",
                "https://example.com/logo.png",
                new BigDecimal("10")
        );

        // then
        Set<ConstraintViolation<VoucherIssuerEntity>> violations = validator.validate(entity);
        assertThat(violations).hasSize(1);
        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("contactInfoEmbeddable.csCenterEmbeddable.tel"));
    }



}
