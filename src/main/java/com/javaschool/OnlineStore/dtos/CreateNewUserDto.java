package com.javaschool.OnlineStore.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateNewUserDto {
    private String name;
    private String subname;
    private String password;
    private String email;
}
