package com.pintoss.auth.module.user.infra.persistence;


import com.pintoss.auth.module.user.model.Phone;
import com.pintoss.auth.module.user.model.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpaRepository extends JpaRepository<User, Long> {

    boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);

    boolean existsByPhone(Phone phone);

    Optional<User> findByNameAndPhone(String name, Phone phone);

    Optional<User> findByEmailAndNameAndPhone(String email, String name, Phone phone);
}
