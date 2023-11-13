package com.javaschool.OnlineStore.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.javaschool.OnlineStore.dtos.OrderDto;
import com.javaschool.OnlineStore.exceptions.ResourceNotFoundException;
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

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ClientAddressRepository clientAddressRepository;
    private final PaymentMethodRepository paymentMethodRepository;
    private final DeliveryMethodRepository deliveryMethodRepository;
    private final OrderMapper orderMapper;

    @Transactional(readOnly = true)
    public List<OrderDto> getAllUserOrders(Long userId){
        return orderRepository.findByUser(loadUser(userId)).stream()
            .map(this::createOrderDto)
            .toList();
    }

    @Transactional
    public OrderDto newOrder(OrderDto dto){
        OrderEntity order = mapDtoToEntity(dto, new OrderEntity());

        orderRepository.save(order);
        return createOrderDto(order);
    }

    @Transactional
    public void updateOrder(Long id, OrderDto dto){
        OrderEntity order = loadOrder(id);
        mapDtoToEntity(dto, order);

        orderRepository.save(order);
    }

    @Transactional
    public void deleteCart(Long id){
        orderRepository.deleteById(id);
    }

    private OrderDto createOrderDto(OrderEntity entity){
        return orderMapper.createOrderDto(entity);
    }

    private OrderEntity mapDtoToEntity(OrderDto dto, OrderEntity entity){
        return orderMapper.mapDtoToEntity(dto, entity, loadUser(dto.getUserId()), 
        loadAddress(dto.getAddressId()), loadPayment(dto.getPaymentId()),
         loadDeliveryMethod(dto.getDeliveryMethodId()));
    }

    private OrderEntity loadOrder(Long id){
        return orderRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Order not found"));
    }

    private UserEntity loadUser(Long id){
        return userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    private ClientAddressEntity loadAddress(Long id){
        return clientAddressRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Address not found"));
    }

    private PaymentMethodEntity loadPayment(Long id){
        return paymentMethodRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Payment not found"));
    }

    private DeliveryMethodEntity loadDeliveryMethod(Long id){
        return deliveryMethodRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Delivery Method not found"));
    }
}
