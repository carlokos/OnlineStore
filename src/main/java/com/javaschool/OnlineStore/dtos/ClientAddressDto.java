package com.javaschool.OnlineStore.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClientAddressDto {
    private Long id;
    private String country;
    private String city;
    private String postalCode;
    private String street;
    private String home;
    private String apartament;
    private Long userId;
}
