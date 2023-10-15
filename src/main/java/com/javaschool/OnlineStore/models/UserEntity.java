package com.javaschool.OnlineStore.models;


import java.util.Date;
import java.util.List;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "app_users")
public class UserEntity {
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
	private List<Client_addressEntity> client_address;
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<OrderEntity> order;
}
