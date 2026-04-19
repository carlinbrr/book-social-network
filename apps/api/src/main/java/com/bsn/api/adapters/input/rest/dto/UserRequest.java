package com.bsn.api.adapters.input.rest.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

public record UserRequest (
    @NotEmpty(message = "keycloakId can't be missing")
    @NotBlank(message = "keycloakId cant' be blank")
    String keycloakId,
    @Email(message = "email is not in the correct format")
    @NotEmpty(message = "email can't be missing")
    @NotBlank(message = "email can't be blank")
    String email,
    @NotEmpty(message = "firstName can't be missing")
    @NotBlank(message = "firstName can't be blank")
    String firstName,
    @NotEmpty(message = "lastName can't be missing")
    @NotBlank(message = "lastName can't be blank")
    String lastName
){

}
