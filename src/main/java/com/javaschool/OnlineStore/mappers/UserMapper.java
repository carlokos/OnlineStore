package com.javaschool.OnlineStore.mappers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.javaschool.OnlineStore.dtos.CreateNewUserDto;
import com.javaschool.OnlineStore.dtos.UserDto;
import com.javaschool.OnlineStore.models.UserEntity;

@Service
public class UserMapper {
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserMapper(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public UserDto createUserDto(UserEntity userEntity){
        UserDto dto = new UserDto();
        dto.setId(userEntity.getId());
        dto.setName(userEntity.getName());
        dto.setSubname(userEntity.getSubname());
        dto.setEmail(userEntity.getEmail());
        return dto;
    }

    public UserEntity mapDtoToEntity(CreateNewUserDto dto, UserEntity entity){
        entity.setName(dto.getName());
        entity.setSubname(dto.getSubname());
        entity.setEmail(dto.getEmail());
        entity.setPassword(passwordEncoder.encode(dto.getPassword()));
        return entity;
    }
}
