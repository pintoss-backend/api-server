package com.pintoss.auth.module.cart.usecase;

import com.pintoss.auth.common.security.SecurityContextUtils;
import com.pintoss.auth.module.cart.usecase.dto.CartItemResult;
import com.pintoss.auth.module.cart.usecase.service.CartItemFetcher;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FetchCartItemUseCase {

    private final CartItemFetcher cartItemFetcher;


    public List<CartItemResult> getCartItems() {
        return cartItemFetcher.fetchCartItems(SecurityContextUtils.getUserId());
    }
}
