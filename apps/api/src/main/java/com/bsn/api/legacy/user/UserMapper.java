package com.bsn.api.legacy.user;

import com.bsn.api.adapters.input.UserRequest;
import com.bsn.api.adapters.output.presitence.entity.User;
import org.springframework.stereotype.Service;

@Service
public class UserMapper {

    public User toUser(UserRequest userRequest ) {
        return User.builder()
                .keycloakId(userRequest.keycloakId())
                .firstName(userRequest.firstName())
                .lastName(userRequest.lastName())
                .email(userRequest.email())
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
