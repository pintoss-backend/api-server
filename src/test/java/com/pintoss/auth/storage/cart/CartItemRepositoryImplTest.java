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
import static org.assertj.core.api.Assertions.tuple;

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
        List<CartItem> savingCartItems = List.of(
                CartItem.create(1L, 1L, 1),
                CartItem.create(1L, 2L, 2));

        // When
        cartItemRepository.saveAll(savingCartItems);

        // Then
        List<CartItemEntity> savedCartItems = cartItemJpaRepository.findAll();
        assertThat(savedCartItems).hasSize(2);
        assertThat(savedCartItems).extracting(
                CartItemEntity::getUserId,
                CartItemEntity::getProductId,
                CartItemEntity::getQuantity)
                .containsExactlyInAnyOrder(
                        tuple(1L, 1L, 1),
                        tuple(1L, 2L, 2)
                );
    }

    @Test
    @DisplayName("장바구니에 새로운 아이템이 정상적으로 추가되어야 한다")
    void givenNewCartItems_whenAdd_thenItemsAreAdded() {
        // Given
        List<CartItem> existingCartItems = List.of(
                CartItem.create(1L, 1L, 1),
                CartItem.create(1L, 2L, 2));
        cartItemRepository.saveAll(existingCartItems);

        // When
        List<CartItem> newCartItems = List.of(CartItem.create(1L, 3L, 3));
        cartItemRepository.saveAll(newCartItems);

        // Then
        List<CartItemEntity> savedCartItems = cartItemJpaRepository.findAll();
        assertThat(savedCartItems).hasSize(3);
        assertThat(savedCartItems).extracting(
                CartItemEntity::getUserId,
                CartItemEntity::getProductId,
                CartItemEntity::getQuantity)
                .containsExactlyInAnyOrder(
                        tuple(1L, 1L, 1),
                        tuple(1L, 2L, 2),
                        tuple(1L, 3L, 3)
                );
    }

    @Test
    @DisplayName("기존 아이템의 개수는 유지되면서 새로운 아이템이 추가되어야 한다")
    void givenExistingCartItems_whenAddNew_thenItemsAreAddedWithoutDuplicates() {
        // Given
        CartItemEntity existingCartItem1 = CartItemEntity.create(1L, 1L, 1);
        CartItemEntity existingCartItem2 = CartItemEntity.create(1L, 2L, 2);
        existingCartItem1 = cartItemJpaRepository.save(existingCartItem1);
        existingCartItem2 = cartItemJpaRepository.save(existingCartItem2);

        // When
        List<CartItem> newCartItems = List.of(
                CartItem.create(existingCartItem2.getId(), 1L, 2L, 5, existingCartItem1.isDeleted()),
                CartItem.create(1L, 3L, 4));
        cartItemRepository.saveAll(newCartItems);

        // Then
        List<CartItemEntity> savedCartItems = cartItemJpaRepository.findAll();
        assertThat(savedCartItems).hasSize(3);
        assertThat(savedCartItems).extracting(
                CartItemEntity::getUserId,
                CartItemEntity::getProductId,
                CartItemEntity::getQuantity)
                .containsExactlyInAnyOrder(
                        tuple(1L, 1L, 1),
                        tuple(1L, 2L, 5), // 기존 아이템의 수량이 업데이트됨
                        tuple(1L, 3L, 4) // 새로운 아이템 추가됨
                );
    }

    @Test
    // TODO : 도메인 엔티티 분리 후 테스트 작성
    @DisplayName("내 장바구니 아이템을 조회할 수 있어야 한다")
    void givenCartItems_whenGetMyCartItems_thenReturnCartItems() {


    }
}
