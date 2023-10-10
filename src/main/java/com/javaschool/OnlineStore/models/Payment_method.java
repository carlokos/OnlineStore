package com.javaschool.OnlineStore.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "payment_methods")
@Getter
@Setter
@NoArgsConstructor
public class Payment_method {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(length=50, nullable = false, unique=true)
	private String p_method;
	
	@OneToMany(mappedBy = "payment", cascade = CascadeType.ALL)
	@JsonIgnore
	private List<Order> order;
}
