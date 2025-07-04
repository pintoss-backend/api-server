package com.pintoss.auth.api.cart;

import com.pintoss.auth.common.dto.ApiResponse;
import com.pintoss.auth.api.cart.dto.CartItemListResponse;
import com.pintoss.auth.api.cart.dto.CartItemUpdateRequest;
import com.pintoss.auth.core.cart.application.CartItemAddUseCase;
import com.pintoss.auth.core.cart.application.CartItemDeleteUseCase;
import com.pintoss.auth.core.cart.application.CartItemUpdateUseCase;
import com.pintoss.auth.core.cart.application.CartItemQueryUseCase;
import com.pintoss.auth.core.cart.application.dto.CartItemAddRequest;
import com.pintoss.auth.core.cart.application.model.CartItemResult;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/carts")
public class CartItemController {

    private final CartItemQueryUseCase cartItemQueryUseCase;
    private final CartItemAddUseCase cartItemAddUseCase;
    private final CartItemUpdateUseCase cartItemUpdateUseCase;
    private final CartItemDeleteUseCase cartItemDeleteUseCase;

    @GetMapping("/items")
    @PreAuthorize("hasAuthority('USER')")
    public ApiResponse<List<CartItemListResponse>> getCartItems() {
        List<CartItemResult> result = cartItemQueryUseCase.getCartItems();

        List<CartItemListResponse> response = result.stream()
            .map(item -> CartItemListResponse.of(item))
            .toList();

        return ApiResponse.ok(response);
    }

    @PostMapping("/items")
    @PreAuthorize("hasAuthority('USER')")
    public ApiResponse<Void> addCartItem(@RequestBody List<CartItemAddRequest> request) {
        cartItemAddUseCase.addCartItem(request);
        return ApiResponse.ok(null);
    }

    @PutMapping("/items/{cartItemId}")
    @PreAuthorize("hasAuthority('USER')")
    public ApiResponse<Integer> updateCartItem(@PathVariable(value = "cartItemId") Long cartItemId, @RequestBody CartItemUpdateRequest request) {
        cartItemUpdateUseCase.update(cartItemId, request.getQuantity());
        return ApiResponse.ok(null);
    }

    @DeleteMapping("/items/{cartItemId}")
    @PreAuthorize("hasAuthority('USER')")
    public ApiResponse<Void> deleteCartItem(@PathVariable(value = "cartItemId") Long cartItemId) {
        cartItemDeleteUseCase.deleteCartItem(cartItemId);
        return ApiResponse.ok(null);
    }
}
