package com.javaschool.OnlineStore.controllers;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.javaschool.OnlineStore.dtos.CreateNewUserDto;
import com.javaschool.OnlineStore.dtos.UserDto;
import com.javaschool.OnlineStore.services.UserService;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testCreateNewUser() throws Exception {
        CreateNewUserDto createUserDto = CreateNewUserDto.builder()
            .name("newUser")
            .password("password123")
            .email("newUser@example.com")
            .build();
        UserDto userDto = UserDto.builder()
            .id(1L)
            .name("NewUser")
            .email("newUser@example.com")
            .build();

        // Comportamiento esperado del servicio
        when(userService.createNewUser(createUserDto)).thenReturn(userDto);

        // Ejecuta la solicitud MockMvc
        ResultActions result = mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createUserDto)));

        // Verifica el estado de la respuesta y el cuerpo
        result.andExpect(status().isCreated())
                .andExpect(content().string("User created succesfully"));

        // Verifica que el servicio se llamó una vez
        verify(userService, times(1)).createNewUser(createUserDto);
    }

    @Test
    public void testGetAllUsers() throws Exception {
        List<UserDto> userList = Collections.singletonList(
                UserDto.builder()
                        .id(1L)
                        .name("User1")
                        .email("user1@example.com")
                        .build()
        );

        when(userService.getAllUsers()).thenReturn(userList);

        ResultActions result = mockMvc.perform(get("/api/users"));

        result.andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("User1"));
    }

    @Test
    public void testGetUserById() throws Exception {
        Long userId = 1L;
        UserDto user = UserDto.builder()
                .id(userId)
                .name("TestUser")
                .email("testuser@example.com")
                .build();

        when(userService.getUserById(userId)).thenReturn(user);

        ResultActions result = mockMvc.perform(get("/api/users/{id}", userId));

        result.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("TestUser"));
    }

    @Test
    public void testGetCurrentUser() throws Exception {
        UserDto currentUser = UserDto.builder()
                .id(1L)
                .name("CurrentUser")
                .email("currentuser@example.com")
                .build();

        when(userService.getCurrentUser()).thenReturn(currentUser);

        ResultActions result = mockMvc.perform(get("/api/users/current"));

        result.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("CurrentUser"));
    }

    @Test
    public void testGetTopUsers() throws Exception {
        List<UserDto> topUsers = Collections.singletonList(
                UserDto.builder()
                        .id(1L)
                        .name("TopUser")
                        .email("topuser@example.com")
                        .build()
        );

        when(userService.getTopUsers()).thenReturn(topUsers);

        ResultActions result = mockMvc.perform(get("/api/users/top-users"));

        result.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name").value("TopUser"));
    }

    @Test
    public void testUpdateUser() throws Exception {
        Long userId = 1L;
        CreateNewUserDto updateUserDto = CreateNewUserDto.builder()
                .name("UpdatedUser")
                .password("newpassword")
                .email("updateduser@example.com")
                .build();

        // Comportamiento esperado del servicio
        
        doNothing().when(userService).updateUser(userId, updateUserDto);

        // Ejecuta la solicitud MockMvc
        ResultActions result = mockMvc.perform(put("/api/users/{id}", userId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateUserDto)));

        // Verifica el estado de la respuesta y el cuerpo
        result.andExpect(status().isOk())
                .andExpect(content().string("User changed succesfully"));

        // Verifica que el servicio se llamó una vez
        verify(userService, times(1)).updateUser(userId, updateUserDto);
    }

    @Test
    public void testDeleteUser() throws Exception {
        Long userId = 1L;

        // Comportamiento esperado del servicio
        doNothing().when(userService).deleteUser(userId);

        // Ejecuta la solicitud MockMvc
        ResultActions result = mockMvc.perform(delete("/api/users/{id}", userId));

        // Verifica el estado de la respuesta y el cuerpo
        result.andExpect(status().isNoContent())
                .andExpect(content().string("User deleted succesfully"));

        // Verifica que el servicio se llamó una vez
        verify(userService, times(1)).deleteUser(userId);
    }
}
