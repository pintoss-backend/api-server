package com.pintoss.auth.client.nice.store;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class NiceApiSymmetricKey {
    private String key;
    private String iv;
}
