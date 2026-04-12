package com.bsn.api.core.port.input.command;

public record SaveUserCommand (
        String id,
        String firstName,
        String lastName,
        String email
){
}
