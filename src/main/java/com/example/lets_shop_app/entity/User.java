package com.example.lets_shop_app.entity;


import java.util.Collection;
import java.util.Date;
import java.util.List;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User implements UserDetails{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;

	@NotEmpty(message = "First name cannot be empty")
	@Size(min = 3, max = 10, message = "First name should be in between 3 to 10 characters long")
	@Column(name = "first_name")
	private String firstname;
	
	@Column(name = "last_name")
	private String lastname;

	@NotEmpty
	@Column(name = "email", nullable = false, unique = true)
	private String email;
	
	@Column(name = "password")
	private String password;

	@Column(name = "created_at", updatable = false)
	@CreationTimestamp
	private Date createdAt;

	@Column(name = "updated_at", insertable = false)
	@UpdateTimestamp
	private Date updatedAt;

	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "user_authorities", joinColumns = @JoinColumn(name = "user_id"))
	private List<Authority> authorities;

	@OneToMany(mappedBy = "user")
	private List<Order> orders;

	@OneToMany(mappedBy = "user")
	private List<Cart> carts;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getUsername() {
		return email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public String getPassword() {
		return password;
	}
}
