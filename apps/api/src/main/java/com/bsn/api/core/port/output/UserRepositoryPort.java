package com.bsn.api.core.port.output;

import com.bsn.api.core.entity.User;

import java.util.Optional;

public interface UserRepositoryPort {

    Optional<User> findById(String id);

    void save(User user);

}
