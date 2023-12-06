package com.javaschool.OnlineStore.controllers;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
import com.javaschool.OnlineStore.dtos.CategoryDto;
import com.javaschool.OnlineStore.services.CategoryService;

@WebMvcTest(controllers = CategoriesController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CategoryService categoryService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testGetAllCategories() throws Exception {
        CategoryDto category = CategoryDto.builder()
            .id(1L)
            .name("Adidas")
            .build();
        // Comportamiento esperado del servicio

        when(categoryService.getAllCategories()).thenReturn(Collections.singletonList(category));

        // Ejecuta la solicitud MockMvc
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get("/api/categories"));

        // Verifica el estado de la respuesta
        result.andExpect(status().isOk());

        // Verifica que el servicio se llamó una vez
        verify(categoryService, times(1)).getAllCategories();
    }

    @Test
    public void testGetCategoryById() throws Exception {
        Long categoryId = 1L;

        CategoryDto category = CategoryDto.builder()
            .id(categoryId)
            .name("Adidas")
            .build();

        // Comportamiento esperado del servicio
        when(categoryService.getCategoryById(categoryId)).thenReturn(category);

        // Ejecuta la solicitud MockMvc
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get("/api/categories/{id}", categoryId));

        // Verifica el estado de la respuesta
        result.andExpect(status().isOk());

        // Verifica que el servicio se llamó una vez
        verify(categoryService, times(1)).getCategoryById(categoryId);
    }

    @Test
    public void testCreateNewCategory() throws Exception {
        CategoryDto categoryDto = new CategoryDto(/* proporciona los datos necesarios */);

        // Ejecuta la solicitud MockMvc
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.post("/api/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(categoryDto)));

        // Verifica el estado de la respuesta
        result.andExpect(status().isCreated());

        // Verifica que el servicio se llamó una vez
        verify(categoryService, times(1)).createNewCategory(categoryDto);
    }

    @Test
    public void testUpdateCategory() throws Exception {
        Long categoryId = 1L;
        CategoryDto categoryDto = new CategoryDto(/* proporciona los datos necesarios */);

        // Ejecuta la solicitud MockMvc
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.put("/api/categories/{id}", categoryId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(categoryDto)));

        // Verifica el estado de la respuesta
        result.andExpect(status().isOk());

        // Verifica que el servicio se llamó una vez
        verify(categoryService, times(1)).updateCategory(categoryId, categoryDto);
    }

    @Test
    public void testDeleteCategory() throws Exception {
        Long categoryId = 1L;

        // Ejecuta la solicitud MockMvc
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.delete("/api/categories/{id}", categoryId));

        // Verifica el estado de la respuesta
        result.andExpect(status().isNoContent());

        // Verifica que el servicio se llamó una vez
        verify(categoryService, times(1)).deleteCategory(categoryId);
    }
}

