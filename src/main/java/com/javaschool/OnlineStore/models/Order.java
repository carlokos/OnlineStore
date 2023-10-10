package com.javaschool.OnlineStore.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "app_order")
@Getter
@Setter
@NoArgsConstructor
public class Order {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "payment_status", length=50, nullable = false, unique=false)
	private String payment_status;
	
	@Column(name = "order_status", length=50, nullable = false, unique=false)
	private String order_status;
	
	@ManyToOne()
    @JoinColumn(name = "user_id", nullable = false)
	private User user;
	
	@ManyToOne()
    @JoinColumn(name = "address_id", nullable = false)
	private Client_address client_address;
	
	@ManyToOne()
    @JoinColumn(name = "payment_method_id", nullable = false)
	private Payment_method payment;
	
	@ManyToOne()
    @JoinColumn(name = "delivery_method_id", nullable = false)
	private Delivery_method delivery_method;
	
	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
	@JsonIgnore
	private List<Order_detail> order_detail;
}
