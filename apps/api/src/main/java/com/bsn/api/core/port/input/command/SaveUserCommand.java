package com.bsn.api.core.port.input.command;

public record SaveUserCommand (
        String id,
        String email,
        String firstName,
        String lastName
){
}
