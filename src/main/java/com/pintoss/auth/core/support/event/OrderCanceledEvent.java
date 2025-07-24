package com.pintoss.auth.core.support.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OrderCanceledEvent {

    private final String orderNo;

}
