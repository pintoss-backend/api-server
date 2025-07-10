package com.pintoss.auth.core.cart.application;

import com.pintoss.auth.api.security.SecurityContextUtils;
import com.pintoss.auth.core.cart.domain.CartItemResult;
import com.pintoss.auth.core.cart.application.flow.CartItemViewer;
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
