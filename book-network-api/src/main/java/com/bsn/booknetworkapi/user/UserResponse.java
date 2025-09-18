package com.bsn.booknetworkapi.user;

import jakarta.persistence.Column;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {

    private String keycloakId;

    private String firstName;

    private String lastName;

    private LocalDate dateOfBirth;

    private String email;

}
