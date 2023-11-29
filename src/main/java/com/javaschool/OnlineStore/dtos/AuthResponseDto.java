package com.javaschool.OnlineStore.dtos;

import com.javaschool.OnlineStore.security.SecurityConstants;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResponseDto {
    private String accessToken;
    private final String tokenType = SecurityConstants.TOKEN_TYPE;
}
