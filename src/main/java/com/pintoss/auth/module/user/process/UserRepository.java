package com.pintoss.auth.module.user.process;

import com.pintoss.auth.module.user.process.domain.Phone;
import com.pintoss.auth.module.user.process.domain.User;
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

    boolean existsByPhone_Phone(String phone);

    Page<User> getUsers(Pageable pageable);
}
