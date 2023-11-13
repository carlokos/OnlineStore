package com.javaschool.OnlineStore.services;

import java.util.Collections;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.javaschool.OnlineStore.dtos.AuthResponseDto;
import com.javaschool.OnlineStore.dtos.CreateNewUserDto;
import com.javaschool.OnlineStore.dtos.LogInDto;
import com.javaschool.OnlineStore.dtos.UserDto;
import com.javaschool.OnlineStore.exceptions.ResourceConflictException;
import com.javaschool.OnlineStore.mappers.UserMapper;
import com.javaschool.OnlineStore.models.RolEntity;
import com.javaschool.OnlineStore.models.UserEntity;
import com.javaschool.OnlineStore.repositories.RolRespository;
import com.javaschool.OnlineStore.repositories.UserRepository;
import com.javaschool.OnlineStore.security.jwt.tokenGenerator;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RolRespository rolRespository;
    private final UserMapper userMapper;
    private final tokenGenerator tokenGenerator;

    @Transactional
    public UserDto register(CreateNewUserDto dto){
        if(userRepository.existsByEmail(dto.getEmail())){
            throw new ResourceConflictException("Email is taken");
        }
        UserEntity newUser = mapDtoToEntity(dto, new UserEntity());
        RolEntity roles = rolRespository.findByName("USER").get();
        newUser.setRoles(Collections.singletonList(roles));

        userRepository.save(newUser);
        return createUserDto(newUser);
    }

    @Transactional(readOnly = true)
    public AuthResponseDto login(LogInDto dto){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                dto.getEmail(),
                dto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = tokenGenerator.generateToken(authentication);
        AuthResponseDto AuthDto = new AuthResponseDto(token);
        return AuthDto;
    }

    private UserDto createUserDto(UserEntity user){
        return userMapper.createUserDto(user);
    }

    private UserEntity mapDtoToEntity(CreateNewUserDto dto, UserEntity entity){
        return userMapper.mapDtoToEntity(dto, entity);
    }
}
