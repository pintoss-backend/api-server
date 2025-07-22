package com.pintoss.auth.api.cart;

import com.pintoss.auth.api.support.dto.ApiResponse;
import com.pintoss.auth.api.cart.dto.CartItemListResponse;
import com.pintoss.auth.api.cart.dto.CartItemUpdateRequest;
import com.pintoss.auth.core.cart.application.CartItemAddUsecase;
import com.pintoss.auth.core.cart.application.CartItemDeleteUsecase;
import com.pintoss.auth.core.cart.application.CartItemUpdateUsecase;
import com.pintoss.auth.core.cart.application.GetMyCartItemsUsecase;
import com.pintoss.auth.core.cart.application.dto.CartItemAddRequest;
import com.pintoss.auth.core.cart.application.dto.CartItemView;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/carts")
public class CartItemController {

    private final GetMyCartItemsUsecase getMyCartItemsUseCase;
    private final CartItemAddUsecase cartItemAddUseCase;
    private final CartItemUpdateUsecase cartItemUpdateUseCase;
    private final CartItemDeleteUsecase cartItemDeleteUseCase;

    @GetMapping("/items")
    public ApiResponse<List<CartItemListResponse>> getCartItems() {
        List<CartItemView> result = getMyCartItemsUseCase.getCartItems();

        List<CartItemListResponse> response = result.stream()
            .map(item -> CartItemListResponse.of(item))
            .toList();

        return ApiResponse.ok(response);
    }

    @PostMapping("/items")
    public ApiResponse<Void> addCartItem(@RequestBody List<CartItemAddRequest> request) {
        cartItemAddUseCase.addCartItem(request);
        return ApiResponse.ok(null);
    }

    @PatchMapping("/items/{cartItemId}")
    public ApiResponse<Integer> updateCartItem(@PathVariable(value = "cartItemId") Long cartItemId, @RequestBody CartItemUpdateRequest request) {
        cartItemUpdateUseCase.update(cartItemId, request.getQuantity());
        return ApiResponse.ok(null);
    }

    @DeleteMapping("/items/{cartItemId}")
    public ApiResponse<Void> deleteCartItem(@PathVariable(value = "cartItemId") Long cartItemId) {
        cartItemDeleteUseCase.deleteCartItem(cartItemId);
        return ApiResponse.ok(null);
    }
}
