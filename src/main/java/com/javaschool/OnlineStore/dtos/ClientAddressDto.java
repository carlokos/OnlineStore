package com.javaschool.OnlineStore.dtos;

import lombok.Data;

@Data
public class ClientAddressDto {
    private Long id;
    private String country;
    private String city;
    private String postal_code;
    private String street;
    private String home;
    private String apartament;
    private Long user_id;
}
