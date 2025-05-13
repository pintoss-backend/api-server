package com.pintoss.auth.module.cart.store;

import com.pintoss.auth.module.cart.application.model.CartItem;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemJpaRepository extends JpaRepository<CartItem, Long> {

    List<CartItem> findByUserId(Long userId);
}
