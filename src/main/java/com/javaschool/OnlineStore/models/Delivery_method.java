package com.javaschool.OnlineStore.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "delivery_methods")
@Getter
@Setter
@NoArgsConstructor
public class Delivery_method {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(length=50, nullable = false, unique=true)
	private String d_method;
	
	@OneToMany(mappedBy = "delivery_method", cascade = CascadeType.ALL)
	@JsonIgnore
	private List<Order> order;
}
