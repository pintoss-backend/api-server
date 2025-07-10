package com.pintoss.auth.core.cart.application;

import com.pintoss.auth.api.security.SecurityContextUtils;
import com.pintoss.auth.core.cart.application.dto.CartItemView;
import com.pintoss.auth.core.cart.application.flow.viewer.CartItemProjectionReader;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class GetMyCartItemsUsecase {

    private final CartItemProjectionReader cartItemProjectionReader;

    public GetMyCartItemsUsecase(CartItemProjectionReader cartItemProjectionReader) {
        this.cartItemProjectionReader = cartItemProjectionReader;
    }

    public List<CartItemView> getCartItems() {
        Long userId = SecurityContextUtils.getUserId();
        return cartItemProjectionReader.getMyCartItems(userId);
    }
}
