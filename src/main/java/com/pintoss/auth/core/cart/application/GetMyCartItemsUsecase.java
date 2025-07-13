package com.pintoss.auth.core.cart.application;

import com.pintoss.auth.api.security.SecurityContextUtils;
import com.pintoss.auth.core.cart.application.dto.CartItemView;
import com.pintoss.auth.core.cart.application.flow.reader.CartItemProjectionReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetMyCartItemsUsecase {

    private final CartItemProjectionReader cartItemProjectionReader;

    public List<CartItemView> getCartItems() {
        Long userId = SecurityContextUtils.getUserId();
        return cartItemProjectionReader.getMyCartItems(userId);
    }
}
