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

    public UserResponse toUserResponse(User user) {
        return UserResponse.builder()
                .keycloakId( user.getKeycloakId() )
                .firstName( user.getFirstName() )
                .lastName( user.getLastName() )
                .email( user.getEmail() )
                .dateOfBirth( user.getDateOfBirth() )
                .build();
    }

}
