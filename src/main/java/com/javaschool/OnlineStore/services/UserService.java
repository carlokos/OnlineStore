package com.javaschool.OnlineStore.services;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.javaschool.OnlineStore.dtos.CreateNewUserDto;
import com.javaschool.OnlineStore.dtos.UserDto;
import com.javaschool.OnlineStore.dtos.passwordDto;
import com.javaschool.OnlineStore.exceptions.ResourceConflictException;
import com.javaschool.OnlineStore.exceptions.ResourceNotFoundException;
import com.javaschool.OnlineStore.mappers.UserMapper;
import com.javaschool.OnlineStore.models.UserEntity;
import com.javaschool.OnlineStore.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public List<UserDto> getAllUsers(){
        return userRepository.findAll().stream()
            .map(this::createUserDto)
            .toList();
    }

    @Transactional(readOnly = true)
    public List<UserDto> getTopUsers(){
        return userRepository.findAllByOrderByLoginCountDesc().stream()
            .map(this::createUserDto)
            .toList();
    }

    @Transactional(readOnly = true)
    public UserDto getUserById(Long id){
        UserEntity user = loadUser(id);
        return createUserDto(user);
    }

    @Transactional(readOnly = true)
    public UserDto getCurrentUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if( authentication == null || !authentication.isAuthenticated()){
            throw new ResourceNotFoundException("User not authenticated");
        }
        String email = authentication.getName();
        UserEntity user = userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return userMapper.createUserDto(user);
    }

    @Transactional
    public UserDto createNewUser(CreateNewUserDto dto){
        UserEntity user = mapDtoToEntity(dto, new UserEntity());
        if(userRepository.findByEmail(dto.getEmail()).isPresent()){
            throw new ResourceConflictException("Email already taken");
        }
        userRepository.save(user);
        return createUserDto(user);
    }

    @Transactional
    public void updateUser(Long id, CreateNewUserDto dto){
        UserEntity user = loadUser(id);
        user.setEmail(dto.getEmail());
        user.setName(dto.getName());
        user.setSubname(dto.getSubname());
        userRepository.save(user);
    }

    @Transactional
    public void updatePassword(Long id, passwordDto dto){
        UserEntity user = loadUser(id);
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        userRepository.save(user);
    }

    @Transactional
    public void deleteUser(Long id){
        userRepository.deleteById(id);
    }

    private UserEntity loadUser(Long id){
        return userRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Cannot find user"));
    }

    private UserDto createUserDto(UserEntity user){
        return userMapper.createUserDto(user);
    } 

    private UserEntity mapDtoToEntity(CreateNewUserDto dto, UserEntity entity){
        return userMapper.mapDtoToEntity(dto, entity);
    }
}
