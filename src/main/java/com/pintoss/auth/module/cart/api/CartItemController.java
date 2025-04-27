package com.pintoss.auth.module.cart.api;

import com.pintoss.auth.common.dto.ApiResponse;
import com.pintoss.auth.module.cart.api.dto.AddCartItemRequest;
import com.pintoss.auth.module.cart.usecase.AddCartItemUseCase;
import com.pintoss.auth.module.cart.usecase.FetchCartItemUseCase;
import com.pintoss.auth.module.cart.usecase.dto.AddCartItemCommand;
import com.pintoss.auth.module.cart.usecase.dto.CartItemResult;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/carts")
public class CartItemController {

    private final FetchCartItemUseCase fetchCartItemUseCase;
    private final AddCartItemUseCase addCartItemUseCase;

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

}
