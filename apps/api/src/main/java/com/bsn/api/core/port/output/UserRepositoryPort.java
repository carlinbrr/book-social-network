package com.bsn.api.core.port.output;

import com.bsn.api.core.entity.User;

import java.util.Optional;

public interface UserRepositoryPort {

    Optional<User> findById(String id);

    User create(User user);

    User update(User user);

}
