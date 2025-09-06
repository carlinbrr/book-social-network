package com.bsn.booknetworkapi.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserRequest {

    @NotEmpty(message = "id is mandatory")
    @NotBlank(message = "id is mandatory")
    private String keycloakId;

    @Email(message = "email is not formatted")
    @NotEmpty(message = "email is mandatory")
    @NotBlank(message = "email is mandatory")
    private String email;

    @NotEmpty(message = "firstName is mandatory")
    @NotBlank(message = "firstName is mandatory")
    private String firstName;

    @NotEmpty(message = "lastName is mandatory")
    @NotBlank(message = "lastName is mandatory")
    private String lastName;

}
