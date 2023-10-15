package com.javaschool.OnlineStore.models;

import java.util.List;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "app_order")
@Getter
@Setter
@NoArgsConstructor
public class OrderEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "payment_status", length=50, nullable = false, unique=false)
	private String payment_status;
	
	@Column(name = "order_status", length=50, nullable = false, unique=false)
	private String order_status;
	
	@ManyToOne()
    @JoinColumn(name = "user_id", nullable = false)
	private UserEntity user;
	
	@ManyToOne()
    @JoinColumn(name = "address_id", nullable = false)
	private Client_addressEntity client_address;
	
	@ManyToOne()
    @JoinColumn(name = "payment_method_id", nullable = false)
	private Payment_methodEntity payment;
	
	@ManyToOne()
    @JoinColumn(name = "delivery_method_id", nullable = false)
	private Delivery_methodEntity delivery_method;
	
	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
	private List<Order_detailEntity> order_detail;
}
