package com.aromero.exception;

import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class UsernameAlreadyExistsResponse {

    private String username;

    public UsernameAlreadyExistsResponse(String username) {
        this.username = username;
    }
}
