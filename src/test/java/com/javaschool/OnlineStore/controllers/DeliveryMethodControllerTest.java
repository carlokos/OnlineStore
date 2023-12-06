package com.javaschool.OnlineStore.controllers;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.javaschool.OnlineStore.dtos.DeliveryMethodDto;
import com.javaschool.OnlineStore.services.DeliveryMethodService;

@WebMvcTest(controllers = DeliveryMethodController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class DeliveryMethodControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DeliveryMethodService deliveryMethodService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testGetAllDeliveryMethods() throws Exception {
        DeliveryMethodDto deliveryM1 = new DeliveryMethodDto(1L, "DM1");
        DeliveryMethodDto deliveryM2 = new DeliveryMethodDto(2L, "DM2");

        List<DeliveryMethodDto> deliveryMethods = Arrays.asList(deliveryM1, deliveryM2);
        when(deliveryMethodService.getDeliveryMethods()).thenReturn(deliveryMethods);

        // Ejecuta la solicitud MockMvc
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get("/api/delivery"));

        // Verifica el estado de la respuesta
        result.andExpect(status().isOk());

        // Verifica que el servicio se llamó una vez
        verify(deliveryMethodService, times(1)).getDeliveryMethods();
    }

    @Test
    public void testCreateNewDeliveryMethod() throws Exception {
        DeliveryMethodDto deliveryMethodDto = new DeliveryMethodDto(/* proporciona los datos necesarios */);

        // Ejecuta la solicitud MockMvc
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.post("/api/delivery")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(deliveryMethodDto)));

        // Verifica el estado de la respuesta
        result.andExpect(status().isCreated());

        // Verifica que el servicio se llamó una vez
        verify(deliveryMethodService, times(1)).newDeliveryMethod(deliveryMethodDto);
    }

    @Test
    public void testUpdateDeliveryMethod() throws Exception {
        Long deliveryMethodId = 1L;
        DeliveryMethodDto deliveryMethodDto = new DeliveryMethodDto(deliveryMethodId, "newName");

        // Ejecuta la solicitud MockMvc
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.put("/api/delivery/{id}", deliveryMethodId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(deliveryMethodDto)));

        // Verifica el estado de la respuesta
        result.andExpect(status().isOk());

        // Verifica que el servicio se llamó una vez
        verify(deliveryMethodService, times(1)).updateDelivery(deliveryMethodId, deliveryMethodDto);
    }

    @Test
    public void testDeleteDeliveryMethod() throws Exception {
        Long deliveryMethodId = 1L;

        // Ejecuta la solicitud MockMvc
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.delete("/api/delivery/{id}", deliveryMethodId));

        // Verifica el estado de la respuesta
        result.andExpect(status().isNoContent());

        // Verifica que el servicio se llamó una vez
        verify(deliveryMethodService, times(1)).deleteDeliveryMethod(deliveryMethodId);
    }
}

