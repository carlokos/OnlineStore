package com.javaschool.OnlineStore.dtos;

import lombok.Data;

@Data
public class Client_AddressDto {
    private String country;
    private String city;
    private String postal_code;
    private String street;
    private String home;
    private String apartament;
    private String user_email;
}
