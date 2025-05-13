package com.pintoss.auth.module.cart.api;

import com.pintoss.auth.common.dto.ApiResponse;
import com.pintoss.auth.module.cart.api.dto.AddCartItemRequest;
import com.pintoss.auth.module.cart.api.dto.CartItemUpdateRequest;
import com.pintoss.auth.module.cart.usecase.AddCartItemUseCase;
import com.pintoss.auth.module.cart.usecase.CartItemDeleteUseCase;
import com.pintoss.auth.module.cart.usecase.CartItemUpdateUseCase;
import com.pintoss.auth.module.cart.usecase.FetchCartItemUseCase;
import com.pintoss.auth.module.cart.usecase.dto.AddCartItemCommand;
import com.pintoss.auth.module.cart.usecase.dto.CartItemResult;
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

    private final FetchCartItemUseCase fetchCartItemUseCase;
    private final AddCartItemUseCase addCartItemUseCase;
    private final CartItemUpdateUseCase cartItemUpdateUseCase;
    private final CartItemDeleteUseCase cartItemDeleteUseCase;

    @GetMapping("/items")
    @PreAuthorize("hasAuthority('USER')")
    public ApiResponse<List<CartItemResult>> getCartItems() {
        List<CartItemResult> response = fetchCartItemUseCase.getCartItems();
        return ApiResponse.ok(response);
    }

    @PostMapping("/items")
    @PreAuthorize("hasAuthority('USER')")
    public ApiResponse<Void> addCartItem(@RequestBody List<AddCartItemRequest> request) {
        List<AddCartItemCommand> command = request.stream().map(
            item -> item.to()
        ).toList();
        addCartItemUseCase.addCartItem(command);
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
