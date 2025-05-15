package com.pintoss.auth.module.user.usecase.service;

import com.nimbusds.openid.connect.sdk.claims.UserInfo;
import com.pintoss.auth.module.user.model.Phone;
import com.pintoss.auth.module.user.model.User;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository {

    Long save(User user);

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    Optional<User> findById(long id);

    Optional<User> findByNameAndPhone(String name, Phone phone);

    Optional<User> findByIdWithRoles(long id);

    boolean existsByPhone(Phone phone);

    Page<User> getUsers(Pageable pageable);

    Optional<User> findByEmailAndNameAndPhone(String email, String name, Phone phone);
}
