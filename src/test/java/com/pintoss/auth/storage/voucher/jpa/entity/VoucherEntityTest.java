package com.pintoss.auth.storage.voucher.jpa.entity;

import com.pintoss.auth.core.voucher.domain.Voucher;
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
class VoucherEntityTest {

    @Autowired
    EntityManager em;

    private Validator validator;

    @BeforeEach
    void setup() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    @DisplayName("도메인을 엔티티로 변환할 때 시간 정보가 저장되어야 한다")
    void givenVoucher_whenToEntity_thenMustContainsTimeInfo() {
        // given
        Voucher voucher = Voucher.create(1L, "test voucher", "test issuer", 1000L);

        // when
        VoucherEntity entity = VoucherEntity.from(voucher);
        em.persist(entity);
        em.flush();
        em.clear();

        // then
        VoucherEntity foundVoucher = em.find(VoucherEntity.class, 1L);
        assertThat(foundVoucher).isNotNull();
        assertThat(foundVoucher).extracting(VoucherEntity::getCreatedAt, VoucherEntity::getUpdatedAt)
                .doesNotContainNull();
    }

    @Test
    @DisplayName("상품권 발급자 id가 비어있으면 유효성 검사에 실패한다")
    void givenVoucherWithNullIssuerId_whenToEntity_thenValidationFails() {
        // when
        VoucherEntity voucherEntity = VoucherEntity.create(null, "test voucher", "test issuer", 1000L);

        // then
        Set<ConstraintViolation<VoucherEntity>> violations = validator.validate(voucherEntity);
        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("voucherIssuerId"));
    }

    @Test
    @DisplayName("상품권 이름이 비어있으면 유효성 검사에 실패한다")
    void givenVoucherWithEmptyName_whenToEntity_thenValidationFails() {
        // when
        VoucherEntity voucherEntity = VoucherEntity.create(1L, "", "test issuer", 1000L);

        // then
        Set<ConstraintViolation<VoucherEntity>> violations = validator.validate(voucherEntity);
        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("name"));
    }

    @Test
    @DisplayName("상품권 발급자 이름이 비어있으면 유효성 검사에 실패한다")
    void givenVoucherWithEmptyIssuerName_whenToEntity_thenValidationFails() {
        // when
        VoucherEntity voucherEntity = VoucherEntity.create(1L, "test voucher", "", 1000L);

        // then
        Set<ConstraintViolation<VoucherEntity>> violations = validator.validate(voucherEntity);
        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("issuerName"));
    }

    @Test
    @DisplayName("가격이 비어있으면 유효성 검사에 실패한다")
    void givenVoucherWithNullPrice_whenToEntity_thenValidationFails() {
        // when
        VoucherEntity voucherEntity = VoucherEntity.create(1L, "test voucher", "test issuer", null);

        // then
        Set<ConstraintViolation<VoucherEntity>> violations = validator.validate(voucherEntity);
        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("price"));
    }

    @Test
    @DisplayName("가격이 음수이면 유효성 검사에 실패한다")
    void givenVoucherWithNegativePrice_whenToEntity_thenValidationFails() {
        // when
        VoucherEntity voucherEntity = VoucherEntity.create(1L, "test voucher", "test issuer", -1000L);

        // then
        Set<ConstraintViolation<VoucherEntity>> violations = validator.validate(voucherEntity);
        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("price"));
    }

}
