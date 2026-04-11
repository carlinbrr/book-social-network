package com.bsn.api.core.port.output;

import com.bsn.api.core.entity.User;

public interface UserRepositoryPort {

    User findById(String id);

    void save(User user);

}
