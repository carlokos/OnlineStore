package com.javaschool.OnlineStore.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.javaschool.OnlineStore.dtos.CreateNewUserDto;
import com.javaschool.OnlineStore.dtos.PasswordDto;
import com.javaschool.OnlineStore.dtos.UserDto;
import com.javaschool.OnlineStore.exceptions.ResourceConflictException;
import com.javaschool.OnlineStore.mappers.UserMapper;
import com.javaschool.OnlineStore.models.UserEntity;
import com.javaschool.OnlineStore.repositories.UserRepository;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    private final UserRepository userRepository = Mockito.mock(UserRepository.class);
    private final UserMapper userMapper = Mockito.mock(UserMapper.class);
    private final PasswordEncoder passwordEncoder = Mockito.mock(PasswordEncoder.class);

    private final UserService userService = new UserService(userRepository, userMapper, passwordEncoder);

    @Test
    public void UserService_GetAllUsers_ReturnsUserDtos() {
        // Arrange
        List<UserEntity> users = Collections.singletonList(UserEntity.builder().build());
        List<UserDto> expectedUserDtos = Collections.singletonList(UserDto.builder().build());

        when(userRepository.findAll()).thenReturn(users);
        when(userMapper.createUserDto(any())).thenReturn(expectedUserDtos.get(0));

        // Act
        List<UserDto> actualUserDtos = userService.getAllUsers();

        // Assert
        assertNotNull(actualUserDtos);
        assertEquals(expectedUserDtos.size(), actualUserDtos.size());
    }

    @Test
    public void UserService_GetTopUsers_ReturnsTopUserDtos() {
        // Arrange
        List<UserEntity> users = Collections.singletonList(UserEntity.builder().build());
        List<UserDto> expectedUserDtos = Collections.singletonList(UserDto.builder().build());

        when(userRepository.findAllByOrderByLoginCountDesc()).thenReturn(users);
        when(userMapper.createUserDto(any())).thenReturn(expectedUserDtos.get(0));

        // Act
        List<UserDto> actualUserDtos = userService.getTopUsers();

        // Assert
        assertNotNull(actualUserDtos);
        assertEquals(expectedUserDtos.size(), actualUserDtos.size());
    }

    @Test
    public void UserService_GetUserById_ReturnsUserDto() {
        // Arrange
        Long userId = 1L;
        UserEntity userEntity = UserEntity.builder().build();
        UserDto expectedUserDto = UserDto.builder().build();

        when(userRepository.findById(userId)).thenReturn(Optional.of(userEntity));
        when(userMapper.createUserDto(userEntity)).thenReturn(expectedUserDto);

        // Act
        UserDto actualUserDto = userService.getUserById(userId);

        // Assert
        assertNotNull(actualUserDto);
        assertEquals(expectedUserDto, actualUserDto);
    }

     @Test
    public void UserService_CreateNewUser_CreatesAndReturnsUserDto() {
        // Arrange
        CreateNewUserDto dto = CreateNewUserDto.builder().build();
        UserEntity userEntity = UserEntity.builder().build();
        UserDto expectedUserDto = UserDto.builder().build();

        //Mocks
        when(userMapper.mapDtoToEntity(dto, new UserEntity())).thenReturn(userEntity);
        when(userRepository.findByEmail(dto.getEmail())).thenReturn(Optional.empty());
        when(userRepository.save(userEntity)).thenReturn(userEntity);
        when(userMapper.createUserDto(userEntity)).thenReturn(expectedUserDto);

        // Act
        UserDto result = userService.createNewUser(dto);

        // Assert
        assertNotNull(result);
        assertEquals(expectedUserDto, result);
    }

    @Test
    public void UserService_CreateNewUser_ThrowsResourceConflictException() {
        // Arrange
        CreateNewUserDto dto = CreateNewUserDto.builder().email("existing.email@example.com").build();
        UserEntity existingUserEntity = UserEntity.builder().build();

        //Mocks
        when(userRepository.findByEmail(dto.getEmail())).thenReturn(Optional.of(existingUserEntity));

        // Act & Assert
        assertThrows(ResourceConflictException.class, () -> userService.createNewUser(dto));
    }

    @Test
    public void UserService_UpdateUser_UpdatesUser() {
        // Arrange
        Long userId = 1L;
        CreateNewUserDto dto = CreateNewUserDto.builder().build();
        UserEntity userEntity = UserEntity.builder().id(userId).build();

        //Mocks
        when(userRepository.findById(userId)).thenReturn(Optional.of(userEntity));
        when(userRepository.save(userEntity)).thenReturn(userEntity);
        when(userRepository.findByEmail(dto.getEmail())).thenReturn(Optional.of(userEntity));

        // Act
        userService.updateUser(userId, dto);

        // Assert
        verify(userRepository, times(1)).save(userEntity);
    }

    @Test
    public void UserService_UpdateUser_ThrowsResourceConflictException() {
        //Arrange
        Long userId = 1L;
        CreateNewUserDto userDto = CreateNewUserDto.builder()
            .email("existing email")
            .build();

        UserEntity userToUpdated = UserEntity.builder()
            .id(userId)
            .email("old email")
            .build();

        UserEntity userWithExistingEmail = UserEntity.builder()
            .id(2L)
            .email("existing email")
            .build();

        //Mocks
        when(userRepository.findById(userId)).thenReturn(Optional.of(userToUpdated));
        when(userRepository.findByEmail(userDto.getEmail())).thenReturn(Optional.of(userWithExistingEmail));

        //Assert
        assertThrows(ResourceConflictException.class, () -> userService.updateUser(userId, userDto));
    }

    @Test
    public void UserService_UpdatePassword_UpdatesPassword() {
        // Arrange
        Long userId = 1L;
        PasswordDto passwordDto = PasswordDto.builder().password("newPassword").build();
        UserEntity userEntity = UserEntity.builder().build();

        //Mocks
        when(userRepository.findById(userId)).thenReturn(Optional.of(userEntity));
        when(passwordEncoder.encode(passwordDto.getPassword())).thenReturn("encodedPassword");
        when(userRepository.save(userEntity)).thenReturn(userEntity);

        // Act
        userService.updatePassword(userId, passwordDto);

        // Assert
        verify(userRepository, times(1)).save(userEntity);
    }

    @Test
    public void UserService_DeleteUser_DeletesUser() {
        // Arrange
        Long userId = 1L;

        // Act
        userService.deleteUser(userId);

        // Assert
        verify(userRepository, times(1)).deleteById(userId);
    }
}

