package com.javaschool.OnlineStore.models;

import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name = "products")
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(length=50, nullable = false, unique=false)
	private String title;
	
	@Column(nullable = false, unique=false)
	private double price;
	
	@Column(length=50, nullable = false, unique=false)
	private String brand;
	
	@Column(length=50, nullable = false, unique=false)
	private String color;
	
	@Column(nullable = false, unique=false)
	private double weight;

	@Column(length=50, nullable = false, unique=false)
	private String volume;
	
	@Column(nullable = false, unique=false)
	private int stock;
	
	@ManyToOne()
    @JoinColumn(name = "category_id", nullable = false)
	private Category category;
	
	@OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
	private List<Order_detail> Order_detail;
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public String getVolume() {
		return volume;
	}

	public void setVolume(String volume) {
		this.volume = volume;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Long getId() {
		return id;
	}
	
}
