package com.bsn.api.adapters.output.presitence;

import com.bsn.api.adapters.output.presitence.entity.User;
import com.bsn.api.adapters.output.presitence.mapper.UserMapper;
import com.bsn.api.adapters.output.presitence.repository.JpaUserRepository;
import com.bsn.api.core.port.output.UserRepositoryPort;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserRepositoryAdapter implements UserRepositoryPort {

    private final JpaUserRepository jpaUserRepository;


    public UserRepositoryAdapter(JpaUserRepository jpaUserRepository) {
        this.jpaUserRepository = jpaUserRepository;
    }

    @Override
    public Optional<com.bsn.api.core.entity.User> findById(String id) {
        Optional<User> jpaUserOptional = jpaUserRepository.findById(id);
        return jpaUserOptional.map(UserMapper::toUser);
    }

    @Override
    public void create(com.bsn.api.core.entity.User user) {
        jpaUserRepository.save(UserMapper.toJpaUser(user));
    }

    @Override
    public void update(com.bsn.api.core.entity.User user) {
        User jpaUser = jpaUserRepository.findById(user.getId())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        UserMapper.mergeUser(jpaUser, user);
        jpaUserRepository.save(jpaUser);
    }

}
