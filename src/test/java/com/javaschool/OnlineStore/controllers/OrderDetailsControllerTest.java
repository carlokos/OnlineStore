package com.javaschool.OnlineStore.controllers;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

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
import com.javaschool.OnlineStore.dtos.OrderDetailDto;
import com.javaschool.OnlineStore.services.OrderDetailService;

@WebMvcTest(controllers = OrderDetailsController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class OrderDetailsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderDetailService orderDetailService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testGetOrderDetail() throws Exception {
        // Supongamos que tienes un objeto OrderDetailDto con el ID 1L
        OrderDetailDto orderDetailDto = new OrderDetailDto();
        orderDetailDto.setId(1L);

        // Comportamiento esperado del servicio
        when(orderDetailService.getOrderDetail(1L)).thenReturn(orderDetailDto);

        // Ejecuta la solicitud MockMvc
        ResultActions result = mockMvc.perform(get("/api/orderDetail/1"));

        // Verifica el estado de la respuesta y el cuerpo
        result.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(orderDetailDto.getId().intValue()));

        // Verifica que el servicio se llamó una vez
        verify(orderDetailService, times(1)).getOrderDetail(1L);
    }

    @Test
    public void testGetAllOrderDetails() throws Exception {
        // Supongamos que tienes una lista de OrderDetailDto asociados al ID de una
        // orden
        OrderDetailDto orderDetail = OrderDetailDto.builder()
                .id(1L)
                .orderId(1L)
                .build();

        List<OrderDetailDto> orderDetails = Arrays.asList(orderDetail);

        // Comportamiento esperado del servicio
        when(orderDetailService.getAllOrderDetails(1L)).thenReturn(orderDetails);

        // Ejecuta la solicitud MockMvc
        ResultActions result = mockMvc.perform(get("/api/orderDetail/order/1"));

        // Verifica el estado de la respuesta y el cuerpo
        result.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(orderDetails.size())));

        // Verifica que el servicio se llamó una vez
        verify(orderDetailService, times(1)).getAllOrderDetails(1L);
    }

    @Test
    public void testGetOrderCount() throws Exception {
        // Supongamos que el servicio devuelve un recuento (Long) asociado al ID de una
        // orden
        Long orderCount = 10L;

        // Comportamiento esperado del servicio
        when(orderDetailService.getOrderDetailsCount(1L)).thenReturn(orderCount);

        // Ejecuta la solicitud MockMvc
        ResultActions result = mockMvc.perform(get("/api/orderDetail/count/1"));

        // Verifica el estado de la respuesta y el cuerpo
        result.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").value(orderCount.intValue()));

        // Verifica que el servicio se llamó una vez
        verify(orderDetailService, times(1)).getOrderDetailsCount(1L);
    }

    @Test
    public void testGetTopSellingCategories() throws Exception {
        // Supongamos que el servicio devuelve una lista de Object[] para las categorías
        // más vendidas
        List<Object[]> topCategories = Arrays.asList(/* tus Object[] aquí */);

        // Comportamiento esperado del servicio
        when(orderDetailService.getTopSellingCategories()).thenReturn(topCategories);

        // Ejecuta la solicitud MockMvc
        ResultActions result = mockMvc.perform(get("/api/orderDetail/top-categories"));

        // Verifica el estado de la respuesta y el cuerpo
        result.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(topCategories.size())));

        // Verifica que el servicio se llamó una vez
        verify(orderDetailService, times(1)).getTopSellingCategories();
    }

    @Test
    public void testGetTotalPrice() throws Exception {
        // Supongamos que el servicio devuelve un precio total (double) asociado al ID
        // de detalles de orden
        double totalPrice = 50.0;

        // Comportamiento esperado del servicio
        when(orderDetailService.calculateTotalPrice(1L)).thenReturn(totalPrice);

        // Ejecuta la solicitud MockMvc
        ResultActions result = mockMvc.perform(get("/api/orderDetail/totalPrice/1"));

        // Verifica el estado de la respuesta y el cuerpo
        result.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").value(totalPrice));

        // Verifica que el servicio se llamó una vez
        verify(orderDetailService, times(1)).calculateTotalPrice(1L);
    }

    @Test
    public void testGetTotalOrderPrice() throws Exception {
        // Supongamos que el servicio devuelve un precio total (double) asociado al ID
        // de la orden
        double totalOrderPrice = 100.0;

        // Comportamiento esperado del servicio
        when(orderDetailService.calculateOrderTotalPrice(1L)).thenReturn(totalOrderPrice);

        // Ejecuta la solicitud MockMvc
        ResultActions result = mockMvc.perform(get("/api/orderDetail/totalOrderPrice/1"));

        // Verifica el estado de la respuesta y el cuerpo
        result.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").value(totalOrderPrice));

        // Verifica que el servicio se llamó una vez
        verify(orderDetailService, times(1)).calculateOrderTotalPrice(1L);
    }

    @Test
    public void testUpdateOrderDetail() throws Exception {
        // Supongamos que recibimos una solicitud para actualizar un detalle de orden
        // específico
        Long orderDetailId = 1L;
        OrderDetailDto orderDetailDto = OrderDetailDto.builder()
                .id(orderDetailId)
                .orderId(1L)
                .build();

        // Comportamiento esperado del servicio
        doNothing().when(orderDetailService).updateOrderDetail(orderDetailId, orderDetailDto);

        // Ejecuta la solicitud MockMvc
        ResultActions result = mockMvc.perform(put("/api/orderDetail/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(orderDetailDto)));

        // Verifica el estado de la respuesta y el cuerpo
        result.andExpect(status().isCreated())
                .andExpect(content().string("Order details updated succesfully"));

        // Verifica que el servicio se llamó una vez
        verify(orderDetailService, times(1)).updateOrderDetail(orderDetailId, orderDetailDto);
    }

    @Test
    public void testDeleteOrderDetail() throws Exception {
        // Supongamos que recibimos una solicitud para eliminar un detalle de orden
        // específico
        Long orderDetailId = 1L;

        // Comportamiento esperado del servicio
        doNothing().when(orderDetailService).deleteOrderDetail(orderDetailId);

        // Ejecuta la solicitud MockMvc
        ResultActions result = mockMvc.perform(delete("/api/orderDetail/1"));

        // Verifica el estado de la respuesta y el cuerpo
        result.andExpect(status().isNoContent())
                .andExpect(content().string("Order detail deleted succesfully"));

        // Verifica que el servicio se llamó una vez
        verify(orderDetailService, times(1)).deleteOrderDetail(orderDetailId);
    }
}
