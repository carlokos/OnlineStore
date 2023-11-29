package com.javaschool.OnlineStore.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
	
	@Column(name = "email", length=120, nullable = false, unique=true)
	private String email;
	
	@Column(name = "password", length=120, nullable = false, unique = false)
	private String password;
	
	@Column
	private int loginCount;
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<ClientAddressEntity> clientAddress;
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<OrderEntity> order;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<CartEntity> cart;

	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private List<RoleEntity> roles = new ArrayList<>();

	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserEntity that = (UserEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
