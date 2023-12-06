package com.javaschool.OnlineStore.controllers;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.javaschool.OnlineStore.dtos.ProductDto;
import com.javaschool.OnlineStore.services.ProductService;

@WebMvcTest(controllers = ProductController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class ProductControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testGetAllProducts() throws Exception {
        // Datos de prueba
        List<ProductDto> products = Arrays.asList(
                ProductDto.builder().title("product1").build(),
                ProductDto.builder().title("product2").build());

        // Comportamiento esperado del servicio
        when(productService.getAllProducts()).thenReturn(products);

        // Ejecuta la solicitud MockMvc
        ResultActions result = mockMvc.perform(get("/api/products"));

        // Verifica el estado de la respuesta y el cuerpo
        result.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(products.size())))
                .andExpect(jsonPath("$[0].title").value(products.get(0).getTitle()))
                .andExpect(jsonPath("$[1].title").value(products.get(1).getTitle()));

        // Verifica que el servicio se llamó una vez
        verify(productService, times(1)).getAllProducts();
    }

    @Test
    public void testGetProductById() throws Exception {
        // Datos de prueba
        Long productId = 1L;
        ProductDto product = ProductDto.builder()
                .id(productId)
                .title("Product1")
                .price(10.0)
                .build();

        // Comportamiento esperado del servicio
        when(productService.getProductById(productId)).thenReturn(product);

        // Ejecuta la solicitud MockMvc
        ResultActions result = mockMvc.perform(get("/api/products/1"));

        // Verifica el estado de la respuesta y el cuerpo
        result.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(product.getId()))
                .andExpect(jsonPath("$.title").value(product.getTitle()))
                .andExpect(jsonPath("$.price").value(product.getPrice()));

        // Verifica que el servicio se llamó una vez
        verify(productService, times(1)).getProductById(productId);
    }

    @Test
    public void testGetAllProductsByCategory() throws Exception {
        // Datos de prueba
        Long categoryId = 1L;
        List<ProductDto> products = Arrays.asList(
                ProductDto.builder().title("product1").build(),
                ProductDto.builder().title("product2").build());

        // Comportamiento esperado del servicio
        when(productService.getAllProductsByCategory(categoryId)).thenReturn(products);

        // Ejecuta la solicitud MockMvc
        ResultActions result = mockMvc.perform(get("/api/products/category/1"));

        // Verifica el estado de la respuesta y el cuerpo
        result.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(products.size())))
                .andExpect(jsonPath("$[0].title").value(products.get(0).getTitle()))
                .andExpect(jsonPath("$[1].title").value(products.get(1).getTitle()));

        // Verifica que el servicio se llamó una vez
        verify(productService, times(1)).getAllProductsByCategory(categoryId);
    }

    @Test
    public void testGetTopSellingProducts() throws Exception {
        // Datos de prueba
        List<Object[]> topSellingProducts = Arrays.asList(
                new Object[] { 1L, "Product1", 20.0, 100 },
                new Object[] { 2L, "Product2", 30.0, 80 });

        // Comportamiento esperado del servicio
        when(productService.getTopSellingProducts()).thenReturn(topSellingProducts);

        // Ejecuta la solicitud MockMvc
        ResultActions result = mockMvc.perform(get("/api/products/top-selling"));

        // Verifica el estado de la respuesta y el cuerpo
        result.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(topSellingProducts.size())))
                .andExpect(jsonPath("$[0][0]").value(topSellingProducts.get(0)[0]))
                .andExpect(jsonPath("$[1][0]").value(topSellingProducts.get(1)[0]));

        // Verifica que el servicio se llamó una vez
        verify(productService, times(1)).getTopSellingProducts();
    }

    @Test
    public void testCreateNewProduct() throws Exception {
        // Datos de prueba
        ProductDto productDto = ProductDto.builder()
                .title("NewProduct")
                .price(25.0)
                .build();

        // Comportamiento esperado del servicio
        when(productService.createNewProduct(productDto)).thenReturn(productDto);

        // Ejecuta la solicitud MockMvc
        ResultActions result = mockMvc.perform(post("/api/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productDto)));

        // Verifica el estado de la respuesta y el cuerpo
        result.andExpect(status().isCreated())
                .andExpect(content().string("Product created succesfully"));

        // Verifica que el servicio se llamó una vez
        verify(productService, times(1)).createNewProduct(productDto);
    }

    @Test
    public void testDeleteProduct() throws Exception {
        // Supongamos que recibimos una solicitud para eliminar un producto específico
        Long productId = 1L;

        // Comportamiento esperado del servicio
        doNothing().when(productService).deleteProduct(productId);

        // Ejecuta la solicitud MockMvc
        ResultActions result = mockMvc.perform(delete("/api/products/1"));

        // Verifica el estado de la respuesta y el cuerpo
        result.andExpect(status().isNoContent())
                .andExpect(content().string("Product deleted succesfully"));

        // Verifica que el servicio se llamó una vez
        verify(productService, times(1)).deleteProduct(productId);
    }

}
