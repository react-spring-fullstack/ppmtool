package com.aromero.exception;

import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class InvalidLoginResponse {

    private String username;
    private String password;

    public InvalidLoginResponse() {
        this.username = "Invalid username.";
        this.password = "Invalid password.";
    }
}
