package com.javaschool.OnlineStore.dtos;

import java.util.Date;


import lombok.Data;

@Data
public class UserDto {
    private String name;
    private String subname;
    private Date date_of_birth;
    private String email;
}
