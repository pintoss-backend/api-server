package com.pintoss.auth.module.cart.application;

import com.pintoss.auth.common.security.SecurityContextUtils;
import com.pintoss.auth.module.cart.application.model.CartItem;
import com.pintoss.auth.module.cart.application.dto.AddCartItemCommand;
import com.pintoss.auth.module.cart.application.flow.CartItemAdder;
import com.pintoss.auth.module.cart.application.flow.CartItemReader;
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

    public void addCartItem(List<AddCartItemCommand> command) {
        List<CartItem> existingCartItems = cartItemReader.readByUserIdAndProductIdIn(
            SecurityContextUtils.getUserId(), extractProductIds(command));

        Set<Long> existingProductIds = existingCartItems.stream()
            .map(CartItem::getProductId)
            .collect(Collectors.toSet());

        existingCartItems.forEach(cartItem -> {
            Integer quantity = command.stream()
                .filter(item -> item.getProductId().equals(cartItem.getProductId()))
                .findFirst()
                .map(AddCartItemCommand::getQuantity)
                .orElse(0);

            cartItem.increaseQuantity(quantity);
        });

        List<CartItem> newCartItems = command.stream().filter(
            item -> !existingProductIds.contains(item.getProductId()))
            .map(item -> CartItem.create(SecurityContextUtils.getUserId(), item.getProductId(), item.getQuantity()))
            .toList();

        List<CartItem> allCartItems = new ArrayList<>(existingCartItems);
        allCartItems.addAll(newCartItems);

        cartItemAdder.addAll(allCartItems);
    }

    private Set<Long> extractProductIds(List<AddCartItemCommand> command) {
        return command.stream()
            .map(AddCartItemCommand::getProductId)
            .collect(Collectors.toSet());
    }
}
