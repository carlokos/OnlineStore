package com.javaschool.OnlineStore.controllers;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collections;

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
import com.javaschool.OnlineStore.dtos.ClientAddressDto;
import com.javaschool.OnlineStore.services.ClientAddressService;

@WebMvcTest(controllers = ClientAddressController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class ClientAddressControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClientAddressService clientAddressService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testGetAddressById() throws Exception {
        Long addressId = 1L;
        ClientAddressDto address = ClientAddressDto.builder()
            .id(addressId)
            .build();
        // Comportamiento esperado del servicio
        when(clientAddressService.getAddressById(addressId)).thenReturn(address);

        // Ejecuta la solicitud MockMvc
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get("/api/addresses/{id}", addressId));

        // Verifica el estado de la respuesta
        result.andExpect(status().isOk());

        // Verifica que el servicio se llamó una vez
        verify(clientAddressService, times(1)).getAddressById(addressId);
    }

    @Test
    public void testGetAllUserAddresses() throws Exception {
        Long userId = 1L;

        ClientAddressDto address = ClientAddressDto.builder()
            .id(1L)
            .userId(userId)
            .build();
        // Comportamiento esperado del servicio
        when(clientAddressService.getClientAddressbyUser(userId)).thenReturn(Collections.singletonList(address));

        // Ejecuta la solicitud MockMvc
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get("/api/addresses/user/{id}", userId));

        // Verifica el estado de la respuesta
        result.andExpect(status().isOk());

        // Verifica que el servicio se llamó una vez
        verify(clientAddressService, times(1)).getClientAddressbyUser(userId);
    }

    @Test
    public void testCreateNewAddress() throws Exception {
        ClientAddressDto addressDto = new ClientAddressDto(/* proporciona los datos necesarios */);

        // Ejecuta la solicitud MockMvc
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.post("/api/addresses")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(addressDto)));

        // Verifica el estado de la respuesta
        result.andExpect(status().isCreated());

        // Verifica que el servicio se llamó una vez
        verify(clientAddressService, times(1)).createNewAddress(addressDto);
    }

    @Test
    public void testUpdateAddress() throws Exception {
        Long addressId = 1L;
        ClientAddressDto addressDto = new ClientAddressDto(/* proporciona los datos necesarios */);

        // Ejecuta la solicitud MockMvc
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.put("/api/addresses/{id}", addressId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(addressDto)));

        // Verifica el estado de la respuesta
        result.andExpect(status().isOk());

        // Verifica que el servicio se llamó una vez
        verify(clientAddressService, times(1)).updateAddress(addressId, addressDto);
    }

    @Test
    public void testDeleteAddress() throws Exception {
        Long addressId = 1L;

        // Ejecuta la solicitud MockMvc
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.delete("/api/addresses/{id}", addressId));

        // Verifica el estado de la respuesta
        result.andExpect(status().isNoContent());

        // Verifica que el servicio se llamó una vez
        verify(clientAddressService, times(1)).deleteAddress(addressId);
    }
}

