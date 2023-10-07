package com.javaschool.OnlineStore.models;

import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name = "delivery_methods")
public class Delivery_method {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(length=50, nullable = false, unique=false)
	private String d_method;
	
	@OneToMany(mappedBy = "delivery_method", cascade = CascadeType.ALL)
	private List<Order> order;
	
	public Long getId() {
		return id;
	}
	
	public String GetDelivey_method(){
		return d_method;
	}
	
	public void setPayment_method(String dm) {
		this.d_method = dm;
	}
	
}
