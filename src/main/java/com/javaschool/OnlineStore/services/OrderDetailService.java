package com.javaschool.OnlineStore.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.javaschool.OnlineStore.dtos.OrderDetailDto;
import com.javaschool.OnlineStore.exceptions.ResourceNotFoundException;
import com.javaschool.OnlineStore.mappers.OrderDetailMapper;
import com.javaschool.OnlineStore.models.OrderDetailEntity;
import com.javaschool.OnlineStore.models.OrderEntity;
import com.javaschool.OnlineStore.models.ProductEntity;
import com.javaschool.OnlineStore.repositories.OrderDetailRepository;
import com.javaschool.OnlineStore.repositories.OrderRepository;
import com.javaschool.OnlineStore.repositories.ProductRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class OrderDetailService {
    private final OrderDetailRepository orderDetailRepository;
    private final OrderDetailMapper orderDetailMapper;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    @Transactional(readOnly = true)
    public OrderDetailDto getOrderDetail(Long id){
        OrderDetailEntity order = loadOrderDetail(id);
        return createOrderDetailDto(order);
    }

    @Transactional(readOnly = true)
    public List<OrderDetailDto> getAllOrderDetails(Long id){
        return orderDetailRepository.findByOrder(loadOrder(id)).stream()
            .map(this::createOrderDetailDto)
            .toList();
    }

    @Transactional
    public List<OrderDetailDto> createOrderDetails(List<OrderDetailDto> cart){
        List<OrderDetailDto> listOrder = new ArrayList<>();
        for(OrderDetailDto orderDetail : cart){
            OrderDetailEntity orderToAdd = mapDtoToEntity(orderDetail, new OrderDetailEntity(), 
            orderDetail.getOrderId(), orderDetail.getProductId());
            orderDetailRepository.save(orderToAdd);
            listOrder.add(createOrderDetailDto(orderToAdd));
        }
        return listOrder;
    }

    @Transactional
    public void updateOrderDetail(Long id, OrderDetailDto dto){
        OrderDetailEntity orderDetail = loadOrderDetail(id);
        mapDtoToEntity(dto, orderDetail, dto.getOrderId(), dto.getProductId());
        orderDetailRepository.save(orderDetail);
    }

    @Transactional
    public void deleteOrderDetail(Long id){
        orderDetailRepository.deleteById(id);
    }

    private OrderDetailEntity loadOrderDetail(Long id){
        return orderDetailRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("OrderDetail not found"));
    }

    private OrderEntity loadOrder(Long id){
        return orderRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Order not found"));
    }

    private ProductEntity loadProduct(Long id){
        return productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found"));
    }

    private OrderDetailDto createOrderDetailDto(OrderDetailEntity entity){
        return orderDetailMapper.createOrderDetailDto(entity);
    }

    private OrderDetailEntity mapDtoToEntity(OrderDetailDto dto, OrderDetailEntity entity, 
    Long orderId, Long productId){
        return orderDetailMapper.mapDtoToEntity(dto, entity, loadOrder(orderId), loadProduct(productId));
    }
}
