package com.javaschool.OnlineStore.dtos;

import com.javaschool.OnlineStore.security.SecurityConstants;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class AuthResponseDto {
    private String accessToken;
    private final String tokenType = SecurityConstants.TOKEN_TYPE;
}
