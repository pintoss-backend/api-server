package com.pintoss.auth.api.cart;

import com.pintoss.auth.api.cart.dto.CartItemListResponse;
import com.pintoss.auth.api.cart.dto.CartItemUpdateRequest;
import com.pintoss.auth.api.support.dto.ApiResponse;
import com.pintoss.auth.api.support.interceptor.AuthorizationRequired;
import com.pintoss.auth.core.cart.application.CartItemAddUsecase;
import com.pintoss.auth.core.cart.application.CartItemDeleteUsecase;
import com.pintoss.auth.core.cart.application.CartItemUpdateUsecase;
import com.pintoss.auth.core.cart.application.GetMyCartItemsUsecase;
import com.pintoss.auth.core.cart.application.dto.CartItemAddRequest;
import com.pintoss.auth.core.cart.application.dto.CartItemView;
import com.pintoss.auth.core.user.domain.UserRoleEnum;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/carts")
public class CartItemController {

    private final GetMyCartItemsUsecase getMyCartItemsUseCase;
    private final CartItemAddUsecase cartItemAddUseCase;
    private final CartItemUpdateUsecase cartItemUpdateUseCase;
    private final CartItemDeleteUsecase cartItemDeleteUseCase;

    @GetMapping("/items")
    @AuthorizationRequired(value = UserRoleEnum.USER)
    public ApiResponse<List<CartItemListResponse>> getCartItems() {
        List<CartItemView> result = getMyCartItemsUseCase.getCartItems();

        List<CartItemListResponse> response = result.stream()
            .map(item -> CartItemListResponse.of(item))
            .toList();

        return ApiResponse.ok(response);
    }

    @PostMapping("/items")
    @AuthorizationRequired(value = UserRoleEnum.USER)
    public ApiResponse<Void> addCartItem(@RequestBody List<CartItemAddRequest> request) {
        cartItemAddUseCase.addCartItem(request);
        return ApiResponse.ok(null);
    }

    @PatchMapping("/items/{cartItemId}")
    @AuthorizationRequired(value = UserRoleEnum.USER)
    public ApiResponse<Integer> updateCartItem(@PathVariable(value = "cartItemId") Long cartItemId, @RequestBody CartItemUpdateRequest request) {
        cartItemUpdateUseCase.update(cartItemId, request.getQuantity());
        return ApiResponse.ok(null);
    }

    @DeleteMapping("/items/{cartItemId}")
    @AuthorizationRequired(value = UserRoleEnum.USER)
    public ApiResponse<Void> deleteCartItem(@PathVariable(value = "cartItemId") Long cartItemId) {
        cartItemDeleteUseCase.deleteCartItem(cartItemId);
        return ApiResponse.ok(null);
    }
}
