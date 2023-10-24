package com.javaschool.OnlineStore.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.javaschool.OnlineStore.dtos.CreateNewUserDto;
import com.javaschool.OnlineStore.dtos.LogInDto;
import com.javaschool.OnlineStore.dtos.UserDto;
import com.javaschool.OnlineStore.exceptions.ResourceConflictException;
import com.javaschool.OnlineStore.exceptions.ResourceNotFoundException;
import com.javaschool.OnlineStore.mappers.UserMapper;
import com.javaschool.OnlineStore.models.UserEntity;
import com.javaschool.OnlineStore.repositories.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public List<UserDto> getAllUsers(){
        return userRepository.findAll().stream()
            .map(this::createUserDto)
            .toList();
    }

    public UserDto getUserById(Long id){
        UserEntity user = loadUser(id);
        return createUserDto(user);
    }

    public UserDto getUserByLogIn(LogInDto dto){
        UserEntity user = logInUser(dto);
        return createUserDto(user);
    }

    public UserDto createNewUser(CreateNewUserDto dto){
        UserEntity user = mapDtoToEntity(dto, new UserEntity());
        if(userRepository.findByEmail(dto.getEmail()).isPresent()){
            throw new ResourceConflictException("Email already taken");
        }
        //contraseña, si se puede cifrarla 
        userRepository.save(user);
        return createUserDto(user);
    }

    public void updateUser(Long id, CreateNewUserDto dto){
        UserEntity user = loadUser(id);
        mapDtoToEntity(dto, user);
        userRepository.save(user);
    }

    public void deleteUser(Long id){
        userRepository.deleteById(id);
    }

    private UserEntity loadUser(Long id){
        return userRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Cannot find user"));
    }

    private UserEntity logInUser(LogInDto dto){
        return userRepository.findByEmailAndPassword(dto.getEmail(), dto.getPassword())
            .orElseThrow(() -> new ResourceNotFoundException("Email or Password incorrect"));
    }

    private UserDto createUserDto(UserEntity user){
        return userMapper.createUserDto(user);
    } 

    private UserEntity mapDtoToEntity(CreateNewUserDto dto, UserEntity entity){
        return userMapper.mapDtoToEntity(dto, entity);
    }
}
