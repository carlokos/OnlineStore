package com.javaschool.OnlineStore.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.javaschool.OnlineStore.dtos.OrderDto;
import com.javaschool.OnlineStore.mappers.OrderMapper;
import com.javaschool.OnlineStore.models.ClientAddressEntity;
import com.javaschool.OnlineStore.models.DeliveryMethodEntity;
import com.javaschool.OnlineStore.models.OrderEntity;
import com.javaschool.OnlineStore.models.PaymentMethodEntity;
import com.javaschool.OnlineStore.models.UserEntity;
import com.javaschool.OnlineStore.repositories.ClientAddressRepository;
import com.javaschool.OnlineStore.repositories.DeliveryMethodRepository;
import com.javaschool.OnlineStore.repositories.OrderRepository;
import com.javaschool.OnlineStore.repositories.PaymentMethodRepository;
import com.javaschool.OnlineStore.repositories.UserRepository;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    private final OrderRepository orderRepository = Mockito.mock(OrderRepository.class);

    private final UserRepository userRepository = Mockito.mock(UserRepository.class);

    private final ClientAddressRepository clientAddressRepository = Mockito.mock(ClientAddressRepository.class);

    private final PaymentMethodRepository paymentMethodRepository = Mockito.mock(PaymentMethodRepository.class);

    private final DeliveryMethodRepository deliveryMethodRepository = Mockito.mock(DeliveryMethodRepository.class);

    private final OrderMapper orderMapper = Mockito.mock(OrderMapper.class);

    private final OrderService orderService = new OrderService(
            orderRepository, userRepository, clientAddressRepository, paymentMethodRepository,
            deliveryMethodRepository, orderMapper);

    @Test
    public void OrderService_GetAllUserOrders_ReturnsUserOrders() {
        // Arrange
        Long userId = 1L;
        List<OrderEntity> orders = Collections.singletonList(OrderEntity.builder().build());
        List<OrderDto> expectedOrderDtos = Collections.singletonList(OrderDto.builder().build());

        when(userRepository.findById(userId)).thenReturn(Optional.of(UserEntity.builder().build()));
        when(orderRepository.findByUser(any())).thenReturn(orders);
        when(orderMapper.createOrderDto(any())).thenReturn(expectedOrderDtos.get(0));

        // Act
        List<OrderDto> actualOrderDtos = orderService.getAllUserOrders(userId);

        // Assert
        assertNotNull(actualOrderDtos);
        assertEquals(expectedOrderDtos.size(), actualOrderDtos.size());
    }

    @Test
    public void OrderService_GetAllOrders_ReturnsAllOrders() {
        // Arrange
        List<OrderEntity> orders = Collections.singletonList(OrderEntity.builder().build());
        List<OrderDto> expectedOrderDtos = Collections.singletonList(OrderDto.builder().build());

        when(orderRepository.findAll()).thenReturn(orders);
        when(orderMapper.createOrderDto(any())).thenReturn(expectedOrderDtos.get(0));

        // Act
        List<OrderDto> actualOrderDtos = orderService.getAllOrders();

        // Assert
        assertNotNull(actualOrderDtos);
        assertEquals(expectedOrderDtos.size(), actualOrderDtos.size());
    }

    @Test
    public void OrderService_GetMonthlyRevenue_ReturnsMonthlyRevenue() {
        // Arrange
        int month = 1;
        int year = 2023;
        double expectedRevenue = 100.0;

        when(orderRepository.getTotalRevenueByMonth(month, year)).thenReturn(expectedRevenue);

        // Act
        double actualRevenue = orderService.getMonthlyRevenue(month, year);

        // Assert
        assertEquals(expectedRevenue, actualRevenue);
    }

    @Test
    public void OrderService_GetWeeklyRevenue_ReturnsWeeklyRevenue() {
        // Arrange
        int month = 1;
        int year = 2023;
        List<Map<String, Object>> expectedWeeklyRevenue = Collections.singletonList(Collections.emptyMap());

        when(orderRepository.getWeeklyRevenueByMonth(month, year)).thenReturn(expectedWeeklyRevenue);

        // Act
        List<Map<String, Object>> actualWeeklyRevenue = orderService.getWeeklyRevenue(month, year);

        // Assert
        assertNotNull(actualWeeklyRevenue);
        assertEquals(expectedWeeklyRevenue.size(), actualWeeklyRevenue.size());
    }

    @Test
    public void OrderService_NewOrder_CreatesAndReturnsOrderDto() {
        // Arrange
        OrderDto dto = OrderDto.builder().build();
        OrderEntity orderEntity = OrderEntity.builder().build();

        //Mocks
        when(orderMapper.mapDtoToEntity(any(), any(), any(), any(), any(), any())).thenReturn(orderEntity);
        when(orderRepository.save(orderEntity)).thenReturn(orderEntity);
        when(orderMapper.createOrderDto(orderEntity)).thenReturn(dto);

        when(userRepository.findById(any())).thenReturn(Optional.of(UserEntity.builder().build()));
        when(clientAddressRepository.findById(any())).thenReturn(Optional.of(ClientAddressEntity.builder().build()));
        when(paymentMethodRepository.findById(any())).thenReturn(Optional.of(PaymentMethodEntity.builder().build()));
        when(deliveryMethodRepository.findById(any())).thenReturn(Optional.of(DeliveryMethodEntity.builder().build()));

        // Act
        OrderDto result = orderService.newOrder(dto);

        // Assert
        assertNotNull(result);
        assertEquals(dto, result);
    }

    @Test
    public void OrderService_UpdateOrder_UpdatesOrder() {
        // Arrange
        Long orderId = 1L;
        OrderDto dto = OrderDto.builder().build();
        OrderEntity orderEntity = OrderEntity.builder().build();

        //Mocks
        when(orderRepository.findById(orderId)).thenReturn(java.util.Optional.of(orderEntity));
        when(orderMapper.mapDtoToEntity(any(), any(), any(), any(), any(), any())).thenReturn(orderEntity);
        when(orderRepository.save(orderEntity)).thenReturn(orderEntity);

        when(userRepository.findById(any())).thenReturn(Optional.of(UserEntity.builder().build()));
        when(clientAddressRepository.findById(any())).thenReturn(Optional.of(ClientAddressEntity.builder().build()));
        when(paymentMethodRepository.findById(any())).thenReturn(Optional.of(PaymentMethodEntity.builder().build()));
        when(deliveryMethodRepository.findById(any())).thenReturn(Optional.of(DeliveryMethodEntity.builder().build()));


        // Act
        orderService.updateOrder(orderId, dto);

        // Assert
        verify(orderRepository, times(1)).save(orderEntity);
    }

    @Test
    public void OrderService_DeleteCart_DeletesOrder() {
        // Arrange
        Long orderId = 1L;

        // Act
        orderService.deleteCart(orderId);

        // Assert
        verify(orderRepository, times(1)).deleteById(orderId);
    }
}
