package com.pintoss.auth.module.cart.application;

import com.pintoss.auth.common.security.SecurityContextUtils;
import com.pintoss.auth.module.cart.application.model.CartItemResult;
import com.pintoss.auth.module.cart.application.flow.CartItemViewer;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartItemQueryUseCase {

    private final CartItemViewer cartItemViewer;

    public List<CartItemResult> getCartItems() {
        Long userId = SecurityContextUtils.getUserId();
        return cartItemViewer.getMyCartItems(userId);
    }
}
