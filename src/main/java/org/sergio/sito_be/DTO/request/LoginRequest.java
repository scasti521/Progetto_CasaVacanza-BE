package org.sergio.sito_be.DTO.request;

import lombok.Data;

@Data
public class LoginRequest  {
    private String username;
    private String password;
}
