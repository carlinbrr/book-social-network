package com.bsn.booknetworkapi.email;

public enum EmailTemplateName {

    ACTIVATE_ACCOUNT("activate account");

    private final String name;


    EmailTemplateName(String name) {
        this.name = name;
    }
}
