package com.bsn.api.core.port.output;

import com.bsn.api.core.entity.User;
import com.bsn.api.core.value.UserId;

import java.util.Optional;

public interface UserRepositoryPort {

    Optional<User> findById(UserId id);

    User create(User user);

    User update(User user);

}
