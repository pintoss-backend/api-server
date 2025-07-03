package com.pintoss.auth.core.cart.application;

import com.pintoss.auth.common.security.SecurityContextUtils;
import com.pintoss.auth.core.cart.application.model.CartItem;
import com.pintoss.auth.core.cart.application.dto.CartItemAddRequest;
import com.pintoss.auth.core.cart.application.flow.CartItemAdder;
import com.pintoss.auth.core.cart.application.flow.CartItemReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartItemAddUseCase {

    private final CartItemReader cartItemReader;
    private final CartItemAdder cartItemAdder;

    public void addCartItem(List<CartItemAddRequest> command) {
        List<CartItem> selectedCartItems = cartItemReader.readSelectedCartItems(
            SecurityContextUtils.getUserId(), extractProductIds(command));

        Set<Long> existingProductIds = selectedCartItems.stream()
            .map(CartItem::getProductId)
            .collect(Collectors.toSet());

        selectedCartItems.forEach(cartItem -> {
            Integer quantity = command.stream()
                .filter(item -> item.getProductId().equals(cartItem.getProductId()))
                .findFirst()
                .map(CartItemAddRequest::getQuantity)
                .orElse(0);

            cartItem.increaseQuantity(quantity);
        });

        List<CartItem> newCartItems = command.stream().filter(
            item -> !existingProductIds.contains(item.getProductId()))
            .map(item -> CartItem.create(SecurityContextUtils.getUserId(), item.getProductId(), item.getQuantity()))
            .toList();

        List<CartItem> allCartItems = new ArrayList<>(selectedCartItems);

        allCartItems.addAll(newCartItems);
        cartItemAdder.addAll(allCartItems);
    }

    private Set<Long> extractProductIds(List<CartItemAddRequest> command) {
        return command.stream()
            .map(CartItemAddRequest::getProductId)
            .collect(Collectors.toSet());
    }
}
