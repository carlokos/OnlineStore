package com.javaschool.OnlineStore.models;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "app_order")
@Getter
@Setter
@NoArgsConstructor
public class OrderEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "payment_status", length=50, nullable = false, unique=false)
	private String paymentStatus;
	
	@Column(name = "order_status", length=50, nullable = false, unique=false)
	private String orderStatus;

	@Column(name = "total_price", nullable = false)
    private double totalPrice;

	@Column(name = "order_date", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date orderDate;
	
	@ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
	private UserEntity user;
	
	@ManyToOne
    @JoinColumn(name = "address_id", nullable = false)
	private ClientAddressEntity client_address;
	
	@ManyToOne
    @JoinColumn(name = "payment_method_id", nullable = false)
	private PaymentMethodEntity payment;
	
	@ManyToOne
    @JoinColumn(name = "delivery_method_id", nullable = false)
	private DeliveryMethodEntity delivery_method;
	
	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
	private List<OrderDetailEntity> order_detail;

	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderEntity that = (OrderEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
