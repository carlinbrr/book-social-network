package com.bsn.api.adapters.output.presitence.mapper;

import com.bsn.api.adapters.output.presitence.entity.User;
import com.bsn.api.core.value.Email;
import com.bsn.api.core.value.FirstName;
import com.bsn.api.core.value.LastName;
import com.bsn.api.core.value.UserId;

public class UserMapper {

    public static com.bsn.api.core.entity.User toUser(User jpaUser) {
        return com.bsn.api.core.entity.User.restore(new UserId(jpaUser.getKeycloakId()), new FirstName(jpaUser.getFirstName()),
                new LastName(jpaUser.getLastName()), new Email(jpaUser.getEmail()));
    }

    public static User toJpaUser(com.bsn.api.core.entity.User user) {
        return User.builder()
                .keycloakId(user.getId().getValue())
                .firstName(user.getFirstName().getValue())
                .lastName(user.getLastName().getValue())
                .email(user.getEmail().getValue())
                .build();
    }

    public static void mergeUser(User currentJpaUser, com.bsn.api.core.entity.User updatedUser) {
        currentJpaUser.setFirstName(updatedUser.getFirstName().getValue());
        currentJpaUser.setLastName(updatedUser.getLastName().getValue());
    }

}
