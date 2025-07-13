package com.pintoss.auth.storage.cart;

import com.pintoss.auth.core.cart.domain.CartItem;
import com.pintoss.auth.storage.cart.jpa.CartItemJpaRepository;
import com.pintoss.auth.storage.cart.jpa.entity.CartItemEntity;
import com.pintoss.auth.storage.cart.querydsl.CartItemQueryDslRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
@ActiveProfiles("test")
class CartItemRepositoryImplTest {

    @Autowired
    CartItemRepositoryImpl cartItemRepository;

    @Autowired
    CartItemJpaRepository cartItemJpaRepository;

    @Autowired
    CartItemQueryDslRepository cartItemQueryDslRepository;

    @AfterEach
    void tearDown() {
        cartItemJpaRepository.deleteAll();
    }

    @Test
    @DisplayName("장바구니 아이템이 정상적으로 저장되어야 한다")
    void givenCartItems_whenSaveAll_thenItemsAreSaved() {
        // Given
        List<CartItem> savingCartItems = List.of(CartItem.create(1L, 1L, 1),
                CartItem.create(1L, 2L, 2));

        // When
        cartItemRepository.saveAll(savingCartItems);

        // Then
        List<CartItemEntity> savedCartItems = cartItemJpaRepository.findAll();
        assertThat(savedCartItems).hasSize(2);
        assertThat(savedCartItems.get(0).getUserId()).isEqualTo(1L);
        assertThat(savedCartItems.get(0).getProductId()).isEqualTo(1L);
        assertThat(savedCartItems.get(0).getQuantity()).isEqualTo(1);

        assertThat(savedCartItems.get(1).getUserId()).isEqualTo(1L);
        assertThat(savedCartItems.get(1).getProductId()).isEqualTo(2L);
        assertThat(savedCartItems.get(1).getQuantity()).isEqualTo(2);
    }

    @Test
    // TODO : 도메인 엔티티 분리 후 테스트 작성
    @DisplayName("내 장바구니 아이템을 조회할 수 있어야 한다")
    void givenCartItems_whenGetMyCartItems_thenReturnCartItems() {


    }
}
