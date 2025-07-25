package com.pintoss.auth.storage.user.jpa;


import com.pintoss.auth.storage.user.jpa.entity.LoginType;
import com.pintoss.auth.storage.user.jpa.entity.Phone;
import com.pintoss.auth.storage.user.jpa.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserJpaRepository extends JpaRepository<User, Long> {

    boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);

    boolean existsByPhone(Phone phone);

    Optional<User> findByNameAndPhone(String name, Phone phone);

    Optional<User> findByEmailAndNameAndPhone(String email, String name, Phone phone);

    Optional<User> findByEmailAndNameAndPhoneAndLoginType(String email, String name, Phone phone, LoginType loginType);
}
