package com.javaschool.OnlineStore.controllers;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.hasSize;

import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.javaschool.OnlineStore.dtos.AuthResponseDto;
import com.javaschool.OnlineStore.dtos.CreateNewUserDto;
import com.javaschool.OnlineStore.dtos.LogInDto;
import com.javaschool.OnlineStore.dtos.RolDto;
import com.javaschool.OnlineStore.services.AuthService;

@WebMvcTest(controllers = AuthController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class AuthControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private AuthService authService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testRegister() throws Exception {
        // Mock data
        CreateNewUserDto userDto = CreateNewUserDto.builder()
                .name("name")
                .subname("subname")
                .email("email@email.com")
                .password("password")
                .build();

        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDto)));

        result.andExpect(status().isOk())
                .andExpect(content().string("User register success"));

        verify(authService, times(1)).register(userDto);
    }

    @Test
    public void testLogin() throws Exception {
        // Mock data
        LogInDto loginDto = LogInDto.builder()
                .email("email@email.com")
                .password("some password")
                .build();

        AuthResponseDto authResponseDto = AuthResponseDto.builder()
                .accessToken("someToken")
                .build();

        when(authService.login(loginDto)).thenReturn(authResponseDto);

        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginDto)));

        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").value(authResponseDto.getAccessToken()))
                .andExpect(jsonPath("$.tokenType").value(authResponseDto.getTokenType()));

        // Verifica que el servicio se llamó una vez
        verify(authService, times(1)).login(loginDto);
    }

    @Test
    public void testGetUserRoles() throws Exception {
        // Some mock data
        List<RolDto> roles = Arrays.asList(new RolDto(2L, "USER"));

        // What we expect to return
        when(authService.getUserRoles()).thenReturn(roles);

        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get("/api/auth/roles"));

        // Chekc if the info is correct
        result.andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(1)))
            .andExpect(jsonPath("$[0].id").value(roles.get(0).getId()))
            .andExpect(jsonPath("$[0].name").value(roles.get(0).getName()));

        verify(authService, times(1)).getUserRoles();
    }

}
