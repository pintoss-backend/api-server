package com.pintoss.auth.core.user.storage;


import com.pintoss.auth.core.user.domain.LoginType;
import com.pintoss.auth.core.user.domain.Phone;
import com.pintoss.auth.core.user.domain.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpaRepository extends JpaRepository<User, Long> {

    boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);

    boolean existsByPhone(Phone phone);

    Optional<User> findByNameAndPhone(String name, Phone phone);

    Optional<User> findByEmailAndNameAndPhone(String email, String name, Phone phone);

    Optional<User> findByEmailAndNameAndPhoneAndLoginType(String email, String name, Phone phone, LoginType loginType);
}
