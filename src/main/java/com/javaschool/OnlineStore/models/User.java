package com.javaschool.OnlineStore.models;


import java.util.Date;
import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name = "app_users")
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
	private List<Client_address> client_address;
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<Order> order;
	
	public Long getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public String getSubname() {
		return subname;
	}
	
	public String getPassword() {
		return password;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public void setPassword(String pw) {
		this.password = pw;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setSubname(String subname) {
		this.subname = subname;
	}	
}
