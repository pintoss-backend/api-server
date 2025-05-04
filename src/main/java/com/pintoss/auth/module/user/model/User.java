package com.pintoss.auth.module.user.model;

import com.pintoss.auth.common.exception.ErrorCode;
import com.pintoss.auth.common.exception.client.BadRequestException;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String password;

    private String name;

    @Embedded
    private Phone phone;

    private LoginType loginType;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "user_id", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Set<UserRole> roles = new HashSet<>();

    @Column(columnDefinition = "TEXT")
    private String refreshToken;

    private User(String email, String password, String name, Phone phone, LoginType loginType, Set<UserRole> roles) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.phone = phone;
        this.loginType = loginType;
        this.roles = roles;
    }

    public static User register(String email, String password, String name, Phone phone, LoginType loginType, Set<UserRole> roles) {
        return new User(email, password, name, phone, loginType, roles);
    }

    public void storeRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public void validateSameLoginType(LoginType loginType) {
        if(!this.loginType.equals(loginType)){
            throw new BadRequestException(ErrorCode.DUPLICATE_RESOURCE);
        };
    }
}
