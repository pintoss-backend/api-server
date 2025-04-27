package com.pintoss.auth.module.cart.usecase.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddCartItemCommand {

    private Long productId;
    private int quantity;

}
