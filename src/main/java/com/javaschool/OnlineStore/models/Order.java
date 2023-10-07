package com.javaschool.OnlineStore.models;

import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name = "app_order")
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
	private List<Order_detail> order_detail;

	public String getPayment_status() {
		return payment_status;
	}

	public void setPayment_status(String payment_status) {
		this.payment_status = payment_status;
	}

	public String getOrder_status() {
		return order_status;
	}

	public void setOrder_status(String order_status) {
		this.order_status = order_status;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Client_address getClient_address() {
		return client_address;
	}

	public void setClient_address(Client_address client_address) {
		this.client_address = client_address;
	}

	public Payment_method getPayment() {
		return payment;
	}

	public void setPayment(Payment_method payment) {
		this.payment = payment;
	}

	public Delivery_method getDelivery_method() {
		return delivery_method;
	}

	public void setDelivery_method(Delivery_method delivery_method) {
		this.delivery_method = delivery_method;
	}

	public List<Order_detail> getOrder_detail() {
		return order_detail;
	}

	public void setOrder_detail(List<Order_detail> order_detail) {
		this.order_detail = order_detail;
	}

	public Long getId() {
		return id;
	}
	
	
}
