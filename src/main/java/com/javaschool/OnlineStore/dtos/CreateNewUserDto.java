package com.javaschool.OnlineStore.dtos;

import java.util.Date;

import lombok.Data;

@Data
public class CreateNewUserDto {
    private String name;
    private String subname;
    private String password;
    private Date date_of_birth;
    private String email;
}
