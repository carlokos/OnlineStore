package com.javaschool.OnlineStore.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "order_detail")
@Getter
@Setter
@NoArgsConstructor
public class OrderDetailEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
    @Column(name = "quantity", length=50, nullable = false, unique=false)
    private String quantity;
	
    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private OrderEntity order;
	
    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private ProductEntity product;
}
