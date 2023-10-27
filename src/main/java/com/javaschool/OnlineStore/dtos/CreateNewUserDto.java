package com.javaschool.OnlineStore.dtos;

import lombok.Data;

@Data
public class CreateNewUserDto {
    private String name;
    private String subname;
    private String password;
    private String email;
}
