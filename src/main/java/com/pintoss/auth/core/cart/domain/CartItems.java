package com.pintoss.auth.core.cart.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class CartItems {

    private final List<CartItem> cartItems;

    public CartItems(List<CartItem> cartItems) {
        this.cartItems = List.copyOf(cartItems);
    }

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public CartItems addCartItems(Long userId, Map<Long, Integer> productQuantityMap) {
        List<CartItem> merged = new ArrayList<>();

        // 기존에 있던 장바구니 개수 갱신
        increaseCartQuantities(productQuantityMap, merged);

        // 장바구니에 없던 새로운 품목 추가
        addNewCartItems(productQuantityMap, userId, merged);

        return new CartItems(merged);
    }

    private void increaseCartQuantities(Map<Long, Integer> productQuantityMap, List<CartItem> merged) {
        merged.addAll(getCartItems().stream()
                .map(cartItem -> {
                    if (productQuantityMap.containsKey(cartItem.getProductId())) {
                        cartItem.increaseQuantity(productQuantityMap.get(cartItem.getProductId()));
                    }
                    return cartItem;
                })
                .toList());
    }

    private void addNewCartItems(Map<Long, Integer> productQuantityMap, Long userId, List<CartItem> merged) {
        // 기존에 존재했던 장바구니 목록 아이디들
        Set<Long> existingProductIds = cartItems.stream()
                .map(CartItem::getProductId)
                .collect(Collectors.toSet());

        merged.addAll(productQuantityMap.entrySet().stream()
                .filter(entry -> !existingProductIds.contains(entry.getKey()))
                .map(e -> CartItem.create(
                        userId,
                        e.getKey(),
                        e.getValue()))
                .toList());
    }
}
