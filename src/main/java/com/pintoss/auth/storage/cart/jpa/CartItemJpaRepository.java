package com.pintoss.auth.storage.cart.jpa;

import com.pintoss.auth.storage.cart.jpa.entity.CartItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartItemJpaRepository extends JpaRepository<CartItemEntity, Long> {

    List<CartItemEntity> findByUserId(Long userId);
}
