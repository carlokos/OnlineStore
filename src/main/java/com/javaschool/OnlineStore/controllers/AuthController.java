package com.javaschool.OnlineStore.controllers;

import java.util.Collections;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.javaschool.OnlineStore.dtos.AuthResponseDto;
import com.javaschool.OnlineStore.dtos.CreateNewUserDto;
import com.javaschool.OnlineStore.dtos.LogInDto;
import com.javaschool.OnlineStore.mappers.UserMapper;
import com.javaschool.OnlineStore.models.RolEntity;
import com.javaschool.OnlineStore.models.UserEntity;
import com.javaschool.OnlineStore.repositories.RolRespository;
import com.javaschool.OnlineStore.repositories.UserRepository;
import com.javaschool.OnlineStore.security.jwt.tokenGenerator;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RolRespository rolRespository;
    private final UserMapper userMapper;
    private final tokenGenerator tokenGenerator;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody CreateNewUserDto dto){
        if(userRepository.existsByEmail(dto.getEmail())){
            return new ResponseEntity<>("Email is taken", HttpStatus.BAD_REQUEST);
        }
        UserEntity newUser = userMapper.mapDtoToEntity(dto, new UserEntity());

        RolEntity roles = rolRespository.findByName("USER").get();
        newUser.setRoles(Collections.singletonList(roles));

        userRepository.save(newUser);
        return new ResponseEntity<>("User register success", HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@RequestBody LogInDto dto){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                dto.getEmail(),
                dto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = tokenGenerator.generateToken(authentication);
        return new ResponseEntity<>(new AuthResponseDto(token), HttpStatus.OK);
    }
}
