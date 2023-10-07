package com.javaschool.OnlineStore.models;

import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name = "client_address")
public class Client_address {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "country", length=50, nullable = false, unique=false)
	private String country;
	
	@Column(name = "city", length=50, nullable = false, unique=false)
	private String city;
	
	@Column(name = "postal_code", length=50, nullable = false, unique=false)
	private String postal_code;
	
	@Column(name = "street", length=50, nullable = false, unique=false)
	private String street;
	
	@Column(name = "home", length=50, nullable = false, unique=false)
	private String home;
	
	@Column(name = "apartament", length=50, nullable = false, unique=false)
	private String apartament;
	
	@ManyToOne()
    @JoinColumn(name = "user_id", nullable = false)
	private User user;
	
	@OneToMany(mappedBy = "client_address", cascade = CascadeType.ALL)
	private List<Order> order;
	
	public Long getId() {
		return id;
	}
	
	public String getCountry() {
		return country;
	}
	
	public String getCity() {
		return city;
	}
	
	public String getPostalCode() {
		return postal_code;
	}
	
	public String getStreet() {
		return street;
	}
	
	public String getHome() {
		return country;
	}
	
	public String getApartament() {
		return country;
	}
	
	public void setCountry(String country) {
		this.country = country;
	}
	
	public void setCity(String city) {
		this.city = city;
	}
	
	public void setPostalCode(String postal_code) {
		this.postal_code = postal_code;
	}
	
	public void setHome(String home) {
		this.home = home;
	}
	
	public void setApartament(String ap) {
		this.apartament = ap;
	}
}
