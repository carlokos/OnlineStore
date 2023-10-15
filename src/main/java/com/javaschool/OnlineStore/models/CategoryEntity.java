package com.javaschool.OnlineStore.models;

import java.util.List;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "categories")
@Getter
@Setter
@NoArgsConstructor
public class CategoryEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(length=50, nullable = false, unique=true)
	private String name;
		
	@OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
	private List<ProductEntity> products;
}
