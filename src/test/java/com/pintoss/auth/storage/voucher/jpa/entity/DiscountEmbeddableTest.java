package com.pintoss.auth.storage.voucher.jpa.entity;

import com.pintoss.auth.core.voucher.domain.Discount;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
@ActiveProfiles("test")
class DiscountEmbeddableTest {

    @Test
    @DisplayName("엔티티를 도메인으로 변환할 수 있다.")
    void givenDiscountEmbeddable_whenToDomain_thenReturnDiscount() {
        // given
        BigDecimal cartDiscount = new BigDecimal(1000);
        BigDecimal phoneDiscount = new BigDecimal(100);
        DiscountEmbeddable discountEmbeddable = new DiscountEmbeddable(cartDiscount, phoneDiscount);

        // when
        Discount discount = discountEmbeddable.toDomain();

        // then
        assertThat(discount.getCardDiscount()).isEqualTo(cartDiscount);
        assertThat(discount.getPhoneDiscount()).isEqualTo(phoneDiscount);
    }
}
