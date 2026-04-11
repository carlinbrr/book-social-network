package com.bsn.api.core.port.output;

import com.bsn.api.core.entity.User;

public interface UserRepositoryPort {

    public void save(User user);

}
