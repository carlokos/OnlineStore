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
import com.javaschool.OnlineStore.dtos.CartDto;
import com.javaschool.OnlineStore.services.CartService;

@WebMvcTest(controllers = CartController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class CartControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CartService cartService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testGetUserCart() throws Exception {
        Long userId = 1L;
        CartDto userCart = CartDto.builder()
            .id(1L)
            .productId(3L)
            .userId(userId)
            .quantity(5)
            .build();

        // Comportamiento esperado del servicio
        when(cartService.getUserCart(userId)).thenReturn(Collections.singletonList(userCart));

        // Ejecuta la solicitud MockMvc
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get("/api/cart/{id}", userId));

        // Verifica el estado de la respuesta
        result.andExpect(status().isOk());

        // Verifica que el servicio se llamó una vez
        verify(cartService, times(1)).getUserCart(userId);
    }

    @Test
    public void testCreateNewCart() throws Exception {
        CartDto cartDto = CartDto.builder()
            .id(3L)
            .userId(1L)
            .productId(1L)
            .quantity(2)
            .build();

        // Ejecuta la solicitud MockMvc
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.post("/api/cart")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(cartDto)));

        // Verifica el estado de la respuesta
        result.andExpect(status().isCreated());

        // Verifica que el servicio se llamó una vez
        verify(cartService, times(1)).newCart(cartDto);
    }

    @Test
    public void testUpdateCart() throws Exception {
        Long cartId = 1L;
        CartDto cartDto = new CartDto(/* proporciona los datos necesarios */);

        // Ejecuta la solicitud MockMvc
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.put("/api/cart/{id}", cartId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(cartDto)));

        // Verifica el estado de la respuesta
        result.andExpect(status().isOk());

        // Verifica que el servicio se llamó una vez
        verify(cartService, times(1)).updateCart(cartId, cartDto);
    }

    @Test
    public void testAddQuantityToProduct() throws Exception {
        Long userId = 1L;
        Long productId = 3L;

        // Ejecuta la solicitud MockMvc
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.put("/api/cart/{userId}/{productId}", userId, productId));

        // Verifica el estado de la respuesta
        result.andExpect(status().isOk());

        // Verifica que el servicio se llamó una vez
        verify(cartService, times(1)).modifyProductCart(userId, productId);
    }

    @Test
    public void testDeleteCart() throws Exception {
        Long cartId = 1L;

        // Ejecuta la solicitud MockMvc
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.delete("/api/cart/{id}", cartId));

        // Verifica el estado de la respuesta
        result.andExpect(status().isNoContent());

        // Verifica que el servicio se llamó una vez
        verify(cartService, times(1)).deleteCart(cartId);
    }

    @Test
    public void testClearUserCart() throws Exception {
        Long userId = 1L;

        // Ejecuta la solicitud MockMvc
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.delete("/api/cart/user/{id}", userId));

        // Verifica el estado de la respuesta
        result.andExpect(status().isNoContent());

        // Verifica que el servicio se llamó una vez
        verify(cartService, times(1)).clearUserCart(userId);
    }
}
