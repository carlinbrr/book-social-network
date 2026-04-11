package com.bsn.api.core.port.input;

public record SaveUserCommand (
        String id,
        String email,
        String firstName,
        String lastName
){

}
