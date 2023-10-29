package com.example.spring.CafeManagerApplication.dto;

import lombok.Data;

@Data
public class AuthResponseDTO {
    private String accessToken;
    private String tokenType = "Bearer ";
    private String username;

    public AuthResponseDTO(String accessToken,String username) {
        this.accessToken = accessToken;
        this.username = username;
    }
}
