package com.pintoss.auth.common.security.jwt;

import com.pintoss.auth.common.security.UserRoleEnum;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.Getter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@Getter
public class JwtAuthenticationToken extends AbstractAuthenticationToken {

    private Long id;
    private String email;
    private String name;
    private String phone;

    public JwtAuthenticationToken(Long id, String email, String name, String phone, Set<UserRoleEnum> roles) {
        super(toAuthorities(roles));
        this.id = id;
        this.email = email;
        this.name = name;
        this.phone = phone;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return this.id;
    }

    private static Collection<? extends GrantedAuthority> toAuthorities(Set<UserRoleEnum> roles) {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.toString()))
                .collect(Collectors.toSet());
    }
}
