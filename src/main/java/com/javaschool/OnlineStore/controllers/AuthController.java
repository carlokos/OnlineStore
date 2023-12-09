package com.javaschool.OnlineStore.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.javaschool.OnlineStore.dtos.AuthResponseDto;
import com.javaschool.OnlineStore.dtos.RolDto;
import com.javaschool.OnlineStore.dtos.CreateNewUserDto;
import com.javaschool.OnlineStore.dtos.LogInDto;
import com.javaschool.OnlineStore.services.AuthService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody CreateNewUserDto dto){
        authService.register(dto);
        return new ResponseEntity<>("User register success", HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@RequestBody LogInDto dto){
        AuthResponseDto result = authService.login(dto);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/newAdmin")
    public ResponseEntity<String> newAdmin(@RequestBody CreateNewUserDto dto){
        authService.newAdmin(dto);
        return new ResponseEntity<>("New admin added", HttpStatus.OK);
    }

    @GetMapping("/roles")
    public ResponseEntity<List<RolDto>> getUserRoles(){
        List<RolDto> result = authService.getUserRoles();
        return ResponseEntity.ok(result);
    }
}
