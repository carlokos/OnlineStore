package com.javaschool.OnlineStore.models;

import java.util.List;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "client_address")
@Getter
@Setter
@NoArgsConstructor
public class Client_addressEntity {

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
	private UserEntity user;
	
	@OneToMany(mappedBy = "client_address", cascade = CascadeType.ALL)
	private List<OrderEntity> order;
}
