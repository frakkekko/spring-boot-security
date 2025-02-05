package com.luv2code.cruddemo.config.security;

public enum UserRoles {
    EMPLOYEE("EMPLOYEE"),
    MANAGER("MANAGER"),
    ADMIN("ADMIN");

    private final String value;

    private UserRoles(String value){
        this.value = value;
    }

    public String getValue(){
        return value;
    }
}
