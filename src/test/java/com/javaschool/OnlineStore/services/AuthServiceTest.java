package com.javaschool.OnlineStore.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.javaschool.OnlineStore.dtos.AuthResponseDto;
import com.javaschool.OnlineStore.dtos.CreateNewUserDto;
import com.javaschool.OnlineStore.dtos.LogInDto;
import com.javaschool.OnlineStore.dtos.UserDto;
import com.javaschool.OnlineStore.exceptions.ResourceConflictException;
import com.javaschool.OnlineStore.mappers.RolMapper;
import com.javaschool.OnlineStore.mappers.UserMapper;
import com.javaschool.OnlineStore.models.RoleEntity;
import com.javaschool.OnlineStore.models.UserEntity;
import com.javaschool.OnlineStore.repositories.RolRespository;
import com.javaschool.OnlineStore.repositories.UserRepository;
import com.javaschool.OnlineStore.security.jwt.tokenGenerator;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import java.util.Collections;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

    private final AuthenticationManager authenticationManagerMock = Mockito.mock(AuthenticationManager.class);
    private final UserRepository userRepositoryMock = Mockito.mock(UserRepository.class);
    private final RolRespository rolRespositoryMock = Mockito.mock(RolRespository.class);
    private final UserMapper userMapperMock = Mockito.mock(UserMapper.class);
    private final RolMapper rolMapperMock = Mockito.mock(RolMapper.class);
    private final tokenGenerator tokenGeneratorMock = Mockito.mock(tokenGenerator.class);

    private final AuthService authService = new AuthService(
            authenticationManagerMock,
            userRepositoryMock,
            rolRespositoryMock,
            userMapperMock,
            rolMapperMock,
            tokenGeneratorMock
    );

    @Test
    public void AuthService_Register_SuccessfullyRegistersUser() {
        // Arrange
        CreateNewUserDto newUserDto = CreateNewUserDto.builder()
            .name("name")
            .subname("subname")
            .email("test@example.com")
            .password("password")
            .build();

        UserEntity newUserEntity = UserEntity.builder()
            .id(1L)
            .email("test@example.com")
            .password("hashedPassword")
            .roles(Collections.singletonList(RoleEntity.builder().name("USER").build()))
            .build();

        UserDto userDto = UserDto.builder()
            .id(1L)
            .name("name")
            .subname("subname")
            .email("test@example.com")
            .loginCount(0)
            .build();

        RoleEntity rolDto = RoleEntity.builder()
            .id(1L)
            .name("USER")
            .build();

        when(userRepositoryMock.existsByEmail(anyString())).thenReturn(false);
        when(userMapperMock.mapDtoToEntity(newUserDto, new UserEntity())).thenReturn(newUserEntity);
        when(rolRespositoryMock.findByName(any())).thenReturn(Optional.of(rolDto));
        when(userMapperMock.createUserDto(any())).thenReturn(userDto);

        // Act
        UserDto registeredUser = authService.register(newUserDto);

        // Assert
        assertNotNull(registeredUser);
        assertEquals(newUserDto.getEmail(), registeredUser.getEmail());

        // Verify that save method was called on userRepository
        verify(userRepositoryMock, times(1)).save(any(UserEntity.class));
    }

    @Test
    public void AuthService_Register_EmailTakenThrowsException() {
        // Arrange
        CreateNewUserDto existingUserDto = CreateNewUserDto.builder()
                .email("existing@example.com")
                .password("password")
                .build();

        when(userRepositoryMock.existsByEmail(existingUserDto.getEmail())).thenReturn(true);

        // Act & Assert
        assertThrows(ResourceConflictException.class, () -> authService.register(existingUserDto));

        // Verify that save method was not called on userRepository
        verify(userRepositoryMock, times(0)).save(any(UserEntity.class));
    }

    @Test
    public void AuthService_Login_SuccessfullyLogsInUser() {
        // Arrange
        LogInDto loginDto = LogInDto.builder()
                .email("test@example.com")
                .password("password")
                .build();

        UserEntity userEntity = UserEntity.builder()
                .id(1L)
                .email("test@example.com")
                .password("hashedPassword")
                .roles(Collections.singletonList(RoleEntity.builder().name("USER").build()))
                .loginCount(0)
                .build();

        Authentication authenticationMock = Mockito.mock(Authentication.class);

        when(authenticationManagerMock.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authenticationMock);
        when(userRepositoryMock.findByEmail(loginDto.getEmail())).thenReturn(java.util.Optional.of(userEntity));
        when(tokenGeneratorMock.generateToken(authenticationMock)).thenReturn("generatedToken");

        // Act
        AuthResponseDto authResponseDto = authService.login(loginDto);

        // Assert
        assertNotNull(authResponseDto);
        assertEquals("generatedToken", authResponseDto.getAccessToken());

        // Verify that save method was called on userRepository
        verify(userRepositoryMock, times(1)).save(any(UserEntity.class));
    }
}

