package com.javaschool.OnlineStore.controllers;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
import com.javaschool.OnlineStore.dtos.OrderDto;
import com.javaschool.OnlineStore.services.OrderService;

@WebMvcTest(controllers = OrderController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testGetAllOrders() throws Exception {
        OrderDto order = OrderDto.builder()
            .id(1L)
            .build();
        when(orderService.getAllOrders()).thenReturn(Collections.singletonList(order));

        // Ejecuta la solicitud MockMvc
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get("/api/order"));

        // Verifica el estado de la respuesta
        result.andExpect(status().isOk());

        // Verifica que el servicio se llamó una vez
        verify(orderService, times(1)).getAllOrders();
    }

    @Test
    public void testGetMonthlyRevenue() throws Exception {
        int month = 1;
        int year = 2023;

        // Comportamiento esperado del servicio
        when(orderService.getMonthlyRevenue(month, year)).thenReturn(1000.0); // Ajusta según tu necesidad

        // Ejecuta la solicitud MockMvc
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get("/api/order/montly-revenue/{month}/{year}", month, year));

        // Verifica el estado de la respuesta
        result.andExpect(status().isOk());

        // Verifica que el servicio se llamó una vez
        verify(orderService, times(1)).getMonthlyRevenue(month, year);
    }

    @Test
    public void testGetUserOrder() throws Exception {
        Long userId = 1L;

        OrderDto userOrder = OrderDto.builder()
            .id(1L)
            .userId(userId)
            .build();
        // Comportamiento esperado del servicio
        when(orderService.getAllUserOrders(userId)).thenReturn(Collections.singletonList(userOrder));

        // Ejecuta la solicitud MockMvc
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get("/api/order/{id}", userId));

        // Verifica el estado de la respuesta
        result.andExpect(status().isOk());

        // Verifica que el servicio se llamó una vez
        verify(orderService, times(1)).getAllUserOrders(userId);
    }

    @Test
    public void testGetWeeklyRevenueByMonth() throws Exception {
        int month = 1;
        int year = 2023;

        List<Map<String, Object>> weeklyRevenue = new ArrayList<>();

        when(orderService.getWeeklyRevenue(month, year)).thenReturn(weeklyRevenue);
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get("/api/order/weekly-revenue/{month}/{year}", month, year));

        // Verifica el estado de la respuesta
        result.andExpect(status().isOk());

        // Verifica que el servicio se llamó una vez
        verify(orderService, times(1)).getWeeklyRevenue(month, year);
    }

    @Test
    public void testCreateOrder() throws Exception {
        // Crea un objeto OrderDto para usar como datos de prueba
        OrderDto orderDto = OrderDto.builder()
            .id(2L)
            .userId(1L)
            .paymentStatus("paid")
            .orderStatus("delivered")
            .addressId(1L)
            .orderDate(new Date())
            .deliveryMethodId(1L)
            .totalPrice(10)
            .paymentId(1L)
            .build();

        // Comportamiento esperado del servicio
        when(orderService.newOrder(orderDto)).thenReturn(orderDto);

        // Ejecuta la solicitud MockMvc
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.post("/api/order")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(orderDto)));

        // Verifica el estado de la respuesta y el cuerpo
        result.andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(orderDto.getId().intValue())))
                .andExpect(jsonPath("$.paymentStatus", is(orderDto.getPaymentStatus())))
                .andExpect(jsonPath("$.orderStatus", is(orderDto.getOrderStatus())))
                .andExpect(jsonPath("$.totalPrice", is(orderDto.getTotalPrice())))
                .andExpect(jsonPath("$.orderDate", notNullValue()))
                .andExpect(jsonPath("$.userId", is(orderDto.getUserId().intValue())))
                .andExpect(jsonPath("$.addressId", is(orderDto.getAddressId().intValue())))
                .andExpect(jsonPath("$.paymentId", is(orderDto.getPaymentId().intValue())))
                .andExpect(jsonPath("$.deliveryMethodId", is(orderDto.getDeliveryMethodId().intValue())));

        // Verifica que el servicio se llamó una vez
        verify(orderService, times(1)).newOrder(orderDto);
    }

    @Test
    public void testUpdateOrder() throws Exception {
        Long orderId = 1L;
        OrderDto orderDto = OrderDto.builder()
            .id(orderId)
            .addressId(2L)
            .build();

        // Comportamiento esperado del servicio
        doNothing().when(orderService).updateOrder(orderId, orderDto);

        // Ejecuta la solicitud MockMvc
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.put("/api/order/{id}", orderId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(orderDto)));

        // Verifica el estado de la respuesta y el cuerpo
        result.andExpect(status().isOk())
                .andExpect(content().string("Order updated succesfully"));

        // Verifica que el servicio se llamó una vez
        verify(orderService, times(1)).updateOrder(orderId, orderDto);
    }

    @Test
    public void testDeleteOrder() throws Exception {
        Long orderId = 1L;

        // Comportamiento esperado del servicio
        doNothing().when(orderService).deleteCart(orderId);

        // Ejecuta la solicitud MockMvc
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.delete("/api/order/{id}", orderId));

        // Verifica el estado de la respuesta y el cuerpo
        result.andExpect(status().isNoContent())
                .andExpect(content().string("Order deleted succesfully"));

        // Verifica que el servicio se llamó una vez
        verify(orderService, times(1)).deleteCart(orderId);
    }
}

