package com.javaschool.OnlineStore.models;

import jakarta.persistence.*;

@Entity
@Table(name = "order_detail")
public class Order_detail {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "quantity", length=50, nullable = false, unique=false)
	private String quantity;
	
	@ManyToOne()
    @JoinColumn(name = "order_id", nullable = false)
	private Order order;
	
	@ManyToOne()
    @JoinColumn(name = "product_id", nullable = false)
	private Product product;

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Long getId() {
		return id;
	}
	
	
}
