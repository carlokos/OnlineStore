package com.javaschool.OnlineStore.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.javaschool.OnlineStore.dtos.OrderDetailDto;
import com.javaschool.OnlineStore.mappers.OrderDetailMapper;
import com.javaschool.OnlineStore.models.OrderDetailEntity;
import com.javaschool.OnlineStore.models.OrderEntity;
import com.javaschool.OnlineStore.models.ProductEntity;
import com.javaschool.OnlineStore.repositories.OrderDetailRepository;
import com.javaschool.OnlineStore.repositories.OrderRepository;
import com.javaschool.OnlineStore.repositories.ProductRepository;

@ExtendWith(MockitoExtension.class)
public class OrderDetailServiceTest {

    private final OrderDetailRepository orderDetailRepository = Mockito.mock(OrderDetailRepository.class);
    private final OrderDetailMapper orderDetailMapper = Mockito.mock(OrderDetailMapper.class);
    private final OrderRepository orderRepository = Mockito.mock(OrderRepository.class);
    private final ProductRepository productRepository = Mockito.mock(ProductRepository.class);

    private final OrderDetailService orderDetailService = new OrderDetailService(
            orderDetailRepository, orderDetailMapper, orderRepository, productRepository);

    @Test
    public void OrderDetailService_GetOrderDetail_ReturnsOrderDetailDto() {
        // Arrange
        Long orderDetailId = 1L;
        OrderDetailEntity orderDetailEntity = OrderDetailEntity.builder()
                .id(orderDetailId)
                .build();
        OrderDetailDto expectedOrderDetailDto = OrderDetailDto.builder()
                .id(orderDetailId)
                .build();

        when(orderDetailRepository.findById(orderDetailId)).thenReturn(Optional.of(orderDetailEntity));
        when(orderDetailMapper.createOrderDetailDto(orderDetailEntity)).thenReturn(expectedOrderDetailDto);

        // Act
        OrderDetailDto actualOrderDetailDto = orderDetailService.getOrderDetail(orderDetailId);

        // Assert
        assertNotNull(actualOrderDetailDto);
        assertEquals(expectedOrderDetailDto.getId(), actualOrderDetailDto.getId());
    }

    @Test
    public void OrderDetailService_GetAllOrderDetails_ReturnsOrderDetailDtos() {
        // Arrange
        Long orderId = 1L;
        List<OrderDetailEntity> orderDetailEntities = Collections.singletonList(OrderDetailEntity.builder().build());
        List<OrderDetailDto> expectedOrderDetailDtos = Collections.singletonList(OrderDetailDto.builder().build());

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(OrderEntity.builder().build()));
        when(orderDetailRepository.findByOrder(any())).thenReturn(orderDetailEntities);
        when(orderDetailMapper.createOrderDetailDto(any())).thenReturn(expectedOrderDetailDtos.get(0));

        // Act
        List<OrderDetailDto> actualOrderDetailDtos = orderDetailService.getAllOrderDetails(orderId);

        // Assert
        assertNotNull(actualOrderDetailDtos);
        assertEquals(expectedOrderDetailDtos.size(), actualOrderDetailDtos.size());
    }

    @Test
    public void OrderDetailService_CalculateTotalPrice_ReturnsTotalPrice() {
        // Arrange
        Long orderDetailId = 1L;
        OrderDetailEntity orderDetailEntity = OrderDetailEntity.builder()
                .id(orderDetailId)
                .quantity(2)
                .product(ProductEntity.builder().price(10.0).build())
                .build();

        when(orderDetailRepository.findById(orderDetailId)).thenReturn(Optional.of(orderDetailEntity));

        // Act
        double totalPrice = orderDetailService.calculateTotalPrice(orderDetailId);

        // Assert
        assertEquals(20.0, totalPrice);
    }

    @Test
    public void OrderDetailService_GetTopSellingCategories_ReturnsTopSellingCategories() {
        // Arrange
        Object[] category1 = { "Category1", 5L }; // Category name, quantity sold
        Object[] category2 = { "Category2", 3L };
        List<Object[]> topSellingCategories = Arrays.asList(category1, category2);

        when(orderDetailRepository.getTopSellingCategories()).thenReturn(topSellingCategories);

        // Act
        List<Object[]> result = orderDetailService.getTopSellingCategories();

        // Assert
        assertNotNull(result);
        assertEquals(topSellingCategories.size(), result.size());
        assertEquals(topSellingCategories.get(0)[0], result.get(0)[0]); 
        assertEquals(topSellingCategories.get(1)[1], result.get(1)[1]); 
    }

    @Test
    public void createOrderDetails_CreatesOrderDetailsAndReturnsDtoList() {
        // Arrange
        List<OrderDetailDto> cart = Collections.singletonList(OrderDetailDto.builder().build());
        List<OrderDetailEntity> orderDetails = Collections.singletonList(OrderDetailEntity.builder().build());

        for (int i = 0; i < cart.size(); i++) {
            when(orderDetailMapper.mapDtoToEntity(any(), any(), any(), any())).thenReturn(orderDetails.get(i));
            when(orderDetailRepository.save(any())).thenReturn(orderDetails.get(i));
            when(orderDetailMapper.createOrderDetailDto(orderDetails.get(i))).thenReturn(cart.get(i));
            when(orderRepository.findById(any())).thenReturn(Optional.of(OrderEntity.builder().build()));
            when(productRepository.findById(any())).thenReturn(Optional.of(ProductEntity.builder().build()));
        }

        // Act
        List<OrderDetailDto> result = orderDetailService.createOrderDetails(cart);

        // Assert
        assertNotNull(result);
        assertEquals(cart.size(), result.size());
    }

    @Test
    public void updateOrderDetail_UpdatesOrderDetail() {
        // Arrange
        Long orderDetailId = 1L;
        OrderDetailDto dto = OrderDetailDto.builder().orderId(1L).productId(2L).build();
        OrderDetailEntity orderDetailEntity = OrderDetailEntity.builder().build();

        when(orderDetailRepository.findById(orderDetailId)).thenReturn(Optional.of(orderDetailEntity));
        when(orderRepository.findById(any())).thenReturn(Optional.of(OrderEntity.builder().build()));
        when(productRepository.findById(any())).thenReturn(Optional.of(ProductEntity.builder().build()));
        when(orderDetailMapper.mapDtoToEntity(any(), any(), any(), any())).thenReturn(orderDetailEntity);
        when(orderDetailRepository.save(any())).thenReturn(orderDetailEntity);

        // Act
        orderDetailService.updateOrderDetail(orderDetailId, dto);

        // Assert
        verify(orderDetailRepository, times(1)).save(orderDetailEntity);
    }

    @Test
    public void deleteOrderDetail_DeletesOrderDetail() {
        // Arrange
        Long orderDetailId = 1L;

        // Act
        orderDetailService.deleteOrderDetail(orderDetailId);

        // Assert
        verify(orderDetailRepository, times(1)).deleteById(orderDetailId);
    }
}
