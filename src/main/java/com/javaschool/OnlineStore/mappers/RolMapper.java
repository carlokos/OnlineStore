package com.javaschool.OnlineStore.mappers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.javaschool.OnlineStore.dtos.RolDto;
import com.javaschool.OnlineStore.models.RoleEntity;

@Service
public class RolMapper {
    public List<RolDto> createRolDto(List<RoleEntity> roles){
        List<RolDto> dtos = new ArrayList<>();
        for(RoleEntity rol : roles){
            RolDto dto = new RolDto();
            dto.setId(rol.getId());
            dto.setName(rol.getName());
            dtos.add(dto);
        }
        return dtos;
    }
}
