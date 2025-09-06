package com.bsn.booknetworkapi.user;

import org.springframework.stereotype.Service;

@Service
public class UserMapper {

    public User toUser(UserRequest userRequest ) {
        return User.builder()
                .keycloakId(userRequest.getKeycloakId())
                .firstName(userRequest.getFirstName())
                .lastName(userRequest.getLastName())
                .email(userRequest.getEmail())
                .build();
    }

}
