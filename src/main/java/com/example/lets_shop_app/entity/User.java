package com.example.lets_shop_app.entity;


import java.util.Collection;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User implements UserDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private long id;

	@NotBlank(message = "First name cannot be empty")
	@Size(min = 3, max = 10, message = "First name should be in between 3 to 10 characters")
	@Column(name = "first_name")
	private String firstname;

	@NotBlank(message = "Last name cannot be empty")
	@Size(min = 3, max = 10, message = "Last name should be in between 3 to 10 characters")
	@Column(name = "last_name")
	private String lastname;

	@NotBlank(message = "Email cannot be empty")
	@Column(name = "email", nullable = false, unique = true)
	private String email;

	@NotBlank(message = "Password cannot be empty")
	@Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[!@#$%^&*_+-=]).{8,}$")
	@Column(name = "password", nullable = false)
	@JsonIgnore
	private String password;

	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
	private Set<Role> roles;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return roles.stream().map(role ->
				new SimpleGrantedAuthority(role.getName())).toList();
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
