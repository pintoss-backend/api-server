package com.pintoss.auth.module.user.infra.persistence;

import com.pintoss.auth.module.user.usecase.service.UserRepository;
import com.pintoss.auth.module.user.model.Phone;
import com.pintoss.auth.module.user.model.User;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final UserJpaRepository jpaRepository;

    @Override
    public Long save(User user) {
        jpaRepository.save(user);
        return user.getId();
    }

    @Override
    public boolean existsByEmail(String email) {
        return jpaRepository.existsByEmail(email);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return jpaRepository.findByEmail(email);
    }

    @Override
    public Optional<User> findById(long id) {
        return jpaRepository.findById(id);
    }

    @Override
    public Optional<User> findByNameAndPhone(String name, Phone phone) {
        return jpaRepository.findByNameAndPhone(name, phone);
    }

    @Override
    public Optional<User> findByIdWithRoles(long id) {
        return Optional.empty();
    }

    @Override
    public boolean existsByPhone(Phone phone) {
        return jpaRepository.existsByPhone(phone);
    }

    @Override
    public Page<User> getUsers(Pageable pageable) {
        return null;
    }
}
