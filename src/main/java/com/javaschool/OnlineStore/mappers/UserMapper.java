package com.javaschool.OnlineStore.mappers;

import org.springframework.stereotype.Service;

import com.javaschool.OnlineStore.dtos.CreateNewUserDto;
import com.javaschool.OnlineStore.dtos.UserDto;
import com.javaschool.OnlineStore.models.UserEntity;

@Service
public class UserMapper {
    public UserDto createUserDto(UserEntity userEntity){
        UserDto dto = new UserDto();
        dto.setName(userEntity.getName());
        dto.setSubname(userEntity.getSubname());
        dto.setEmail(userEntity.getEmail());
        dto.setDate_of_birth(userEntity.getDate_of_birth());
        return dto;
    }

    public UserEntity mapDtoToEntity(CreateNewUserDto dto, UserEntity entity){
        entity.setName(dto.getName());
        entity.setSubname(dto.getSubname());
        entity.setEmail(dto.getEmail());
        entity.setPassword(dto.getPassword());
        entity.setDate_of_birth(dto.getDate_of_birth());
        return entity;
    }
}
