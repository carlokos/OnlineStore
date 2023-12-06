package com.javaschool.OnlineStore.controllers;

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
import com.javaschool.OnlineStore.dtos.PaymentMethodDto;
import com.javaschool.OnlineStore.services.PaymentMethodService;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = PaymentMethodController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class PaymentMethodControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PaymentMethodService paymentMethodService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testGetAllPaymentMethods() throws Exception {
        // Datos de prueba
        List<PaymentMethodDto> paymentMethods = Arrays.asList(
                new PaymentMethodDto(1L, "Credit Card"),
                new PaymentMethodDto(2L, "PayPal"));

        // Comportamiento esperado del servicio
        when(paymentMethodService.getPaymentMethods()).thenReturn(paymentMethods);

        // Ejecuta la solicitud MockMvc
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get("/api/payment"));

        // Verifica el estado de la respuesta y el cuerpo
        result.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(paymentMethods.size())))
                .andExpect(jsonPath("$[0].id").value(paymentMethods.get(0).getId()))
                .andExpect(jsonPath("$[0].paymentMethod").value(paymentMethods.get(0).getPaymentMethod()))
                .andExpect(jsonPath("$[1].id").value(paymentMethods.get(1).getId()))
                .andExpect(jsonPath("$[1].paymentMethod").value(paymentMethods.get(1).getPaymentMethod()));

        // Verifica que el servicio se llamó una vez
        verify(paymentMethodService, times(1)).getPaymentMethods();
    }

    @Test
    public void testUpdatePaymentMethod() throws Exception {
        // Supongamos que recibimos una solicitud para actualizar un método de pago
        // específico
        Long paymentMethodId = 1L;
        PaymentMethodDto paymentMethodDto = PaymentMethodDto.builder()
                .id(paymentMethodId)
                .paymentMethod("Updated Payment Method")
                .build();

        // Comportamiento esperado del servicio
        doNothing().when(paymentMethodService).updatePayment(paymentMethodId, paymentMethodDto);

        // Ejecuta la solicitud MockMvc
        ResultActions result = mockMvc.perform(put("/api/payment/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(paymentMethodDto)));

        // Verifica el estado de la respuesta y el cuerpo
        result.andExpect(status().isOk())
                .andExpect(content().string("Payment Method updated succesfully"));

        // Verifica que el servicio se llamó una vez
        verify(paymentMethodService, times(1)).updatePayment(paymentMethodId, paymentMethodDto);
    }

    @Test
    public void testDeletePaymentMethod() throws Exception {
        // Supongamos que recibimos una solicitud para eliminar un método de pago
        // específico
        Long paymentMethodId = 1L;

        // Comportamiento esperado del servicio
        doNothing().when(paymentMethodService).deletePayment(paymentMethodId);

        // Ejecuta la solicitud MockMvc
        ResultActions result = mockMvc.perform(delete("/api/payment/1"));

        // Verifica el estado de la respuesta y el cuerpo
        result.andExpect(status().isNoContent())
                .andExpect(content().string("Payment Method deleted succesfully"));

        // Verifica que el servicio se llamó una vez
        verify(paymentMethodService, times(1)).deletePayment(paymentMethodId);
    }

}
