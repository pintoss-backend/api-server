package com.pintoss.auth.core.cart.application;

import com.pintoss.auth.api.support.security.SecurityContextUtils;
import com.pintoss.auth.core.cart.domain.CartItem;
import com.pintoss.auth.core.cart.application.dto.CartItemAddRequest;
import com.pintoss.auth.core.cart.application.flow.reader.CartItemReader;
import com.pintoss.auth.core.cart.application.flow.writer.CartItemAdder;
import com.pintoss.auth.core.cart.domain.CartItems;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartItemAddUsecase {

    private final CartItemReader cartItemReader;
    private final CartItemAdder cartItemAdder;

    @Transactional
    public void addCartItem(List<CartItemAddRequest> command) {
        Long userId = SecurityContextUtils.getUserId();
        CartItems cartItems = new CartItems(cartItemReader.readSelectedCartItems(userId, extractProductIds(command)));
        Map<Long, Integer> productQuantityMap = toOrderedQuantityMap(command);

        CartItems mergedCartItems = cartItems.addCartItems(userId, productQuantityMap);

        cartItemAdder.addAll(mergedCartItems.getCartItems());
    }

    private Set<Long> extractProductIds(List<CartItemAddRequest> command) {
        return command.stream()
            .map(CartItemAddRequest::getProductId)
            .collect(Collectors.toSet());
    }

    private static LinkedHashMap<Long, Integer> toOrderedQuantityMap(List<CartItemAddRequest> command) {
        return command.stream()
                .collect(Collectors.toMap(
                        CartItemAddRequest::getProductId,
                        CartItemAddRequest::getQuantity,
                        (existing, replacement) -> replacement,
                        LinkedHashMap::new
                ));
    }

}
