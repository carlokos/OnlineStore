package com.javaschool.OnlineStore.models;

import java.util.List;
import java.util.Objects;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "delivery_methods")
@Getter
@Setter
@NoArgsConstructor
public class DeliveryMethodEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(length=50, nullable = false, unique=true)
	private String d_method;
	
	@OneToMany(mappedBy = "delivery_method", cascade = CascadeType.ALL)
	private List<OrderEntity> order;

	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DeliveryMethodEntity that = (DeliveryMethodEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
