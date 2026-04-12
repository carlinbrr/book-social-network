package com.bsn.api.adapters.output.presitence.mapper;

import com.bsn.api.adapters.output.presitence.entity.User;

public class UserMapper {

    public static com.bsn.api.core.entity.User toUser(User jpaUser) {
        return new com.bsn.api.core.entity.User(jpaUser.getKeycloakId(), jpaUser.getFirstName(),
                jpaUser.getLastName(), jpaUser.getEmail());
    }

    public static User toJpaUser(com.bsn.api.core.entity.User user) {
        return User.builder()
                .keycloakId(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .build();
    }

    public static void mergeUser(User currentJpaUser, com.bsn.api.core.entity.User updatedUser) {
        currentJpaUser.setFirstName(updatedUser.getFirstName());
        currentJpaUser.setLastName(updatedUser.getLastName());
    }

}
