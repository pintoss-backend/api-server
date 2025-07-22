package com.pintoss.auth.core.user.domain;

import com.pintoss.auth.api.support.exception.client.BadRequestException;
import com.pintoss.auth.support.exception.ErrorCode;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Table(name = "users")
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

    public void resetPassword(String password) {
        this.password = password;
    }

    public void updatePassword(String encodedNewPassword) {
        this.password = encodedNewPassword;
    }
}
