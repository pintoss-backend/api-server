package com.pintoss.auth.storage.user.jpa.entity;

import com.pintoss.auth.storage.user.jpa.utils.EncryptConverter;
import com.pintoss.auth.support.exception.BadRequestException;
import com.pintoss.auth.support.exception.ErrorCode;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Table(name = "users")
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Convert(converter = EncryptConverter.class)
    private String email;

    @NotBlank
    private String password;

    @NotBlank
    private String name;

    @Embedded
    private Phone phone;

    private LoginType loginType;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "user_id", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Set<UserRole> roles = new HashSet<>();

    @Convert(converter = EncryptConverter.class)
    @Column(columnDefinition = "TEXT")
    private String refreshToken;

    public static User register(String email, String password, String name, Phone phone, LoginType loginType, Set<UserRole> roles) {
        return User.builder()
                .email(email)
                .password(password)
                .name(name)
                .phone(phone)
                .loginType(loginType)
                .roles(roles)
                .build();
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
