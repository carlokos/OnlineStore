package com.javaschool.OnlineStore.models;


import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "app_users")
@Getter
@Setter
@NoArgsConstructor
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "name", length=50, nullable = false, unique=false)
	private String name;
	
	@Column(name = "subname", length=50, nullable = false, unique=false)
	private String subname;
	
	@Temporal(TemporalType.DATE)
	private Date date_of_birth;
	
	@Column(name = "email", length=50, nullable = false, unique=true)
	private String email;
	
	@Column(name = "password", length=50, nullable = false, unique = false)
	private String password;
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	@JsonIgnore
	private List<Client_address> client_address;
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	@JsonIgnore
	private List<Order> order;
}
