package com.bsn.api.adapters.output.presitence;

import com.bsn.api.adapters.output.presitence.entity.User;
import com.bsn.api.adapters.output.presitence.repository.JpaUserRepository;
import com.bsn.api.core.port.output.UserRepositoryPort;
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

        if( jpaUserOptional.isPresent() ) {
            User jpaUser = jpaUserOptional.get();
            return Optional.of(new com.bsn.api.core.entity.User(jpaUser.getKeycloakId(), jpaUser.getFirstName(),
                    jpaUser.getLastName(), jpaUser.getEmail()));
        }

        return Optional.empty();
    }

    @Override
    public void save(com.bsn.api.core.entity.User user) {
        User jpaUser = User.builder()
                .keycloakId(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .build();
        jpaUserRepository.save(jpaUser);
    }

}
