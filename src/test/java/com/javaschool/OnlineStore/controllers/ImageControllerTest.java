package com.javaschool.OnlineStore.controllers;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.javaschool.OnlineStore.models.ImageEntity;
import com.javaschool.OnlineStore.services.ImageService;

@WebMvcTest(controllers = ImageController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class ImageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ImageService imageService;

    @Test
    public void testUploadImage() throws Exception {
        Long productId = 1L;
        MockMultipartFile file = new MockMultipartFile("image", "test.png", MediaType.IMAGE_PNG_VALUE, "image content".getBytes());

        // Comportamiento esperado del servicio
        when(imageService.uploadImage(file, productId)).thenReturn("Image uploaded successfully");

        // Ejecuta la solicitud MockMvc
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.multipart("/api/image")
                .file(file)
                .param("productId", productId.toString()));

        // Verifica el estado de la respuesta
        result.andExpect(status().isOk());

        // Verifica que el servicio se llamó una vez
        verify(imageService, times(1)).uploadImage(file, productId);
    }

    @Test
    public void testGetImageInfoByName() throws Exception {
        String imageName = "test.png";
        ImageEntity imageEntity = new ImageEntity(/* proporciona los datos necesarios */);

        // Comportamiento esperado del servicio
        when(imageService.getInfoByImageByName(imageName)).thenReturn(imageEntity);

        // Ejecuta la solicitud MockMvc
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get("/api/image/info/{name}", imageName));

        // Verifica el estado de la respuesta
        result.andExpect(status().isOk());

        // Verifica que el servicio se llamó una vez
        verify(imageService, times(1)).getInfoByImageByName(imageName);
    }

    @Test
    public void testGetImageByName() throws Exception {
        String imageName = "test.png";
        byte[] imageData = "image content".getBytes();

        // Comportamiento esperado del servicio
        when(imageService.getImage(imageName)).thenReturn(imageData);

        // Ejecuta la solicitud MockMvc
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get("/api/image/{name}", imageName));

        // Verifica el estado de la respuesta
        result.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.IMAGE_PNG_VALUE));

        // Verifica que el servicio se llamó una vez
        verify(imageService, times(1)).getImage(imageName);
    }
}

