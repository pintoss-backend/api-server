package com.pintoss.auth.module.cart.application.flow;

import com.pintoss.auth.module.cart.application.model.CartItemResult;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/*
 * Viewer 클래스의 관심사는 UI를 위한 조회에만 집중한다.
 * Viewer 클래스의 역할은 단순히 데이터를 조회하는 것이며, 비즈니스 로직을 포함하지 않는다.
 * Viewer 클래스의 역할은
 * */
@Component
@RequiredArgsConstructor
public class CartItemViewer {

    private final CartItemRepository cartItemRepository;

    public List<CartItemResult> getMyCartItems(Long userId) {
        return cartItemRepository.getMyCartItems(userId);
    }
}
