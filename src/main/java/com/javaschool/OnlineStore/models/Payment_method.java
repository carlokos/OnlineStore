package com.javaschool.OnlineStore.models;

import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name = "payment_methods")
public class Payment_method {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(length=50, nullable = false, unique=false)
	private String p_method;
	
	@OneToMany(mappedBy = "payment", cascade = CascadeType.ALL)
	private List<Order> order;
	
	public Long getId() {
		return id;
	}
	
	public String GetPayment_method(){
		return p_method;
	}
	
	public void setPayment_method(String pm) {
		this.p_method = pm;
	}
}
