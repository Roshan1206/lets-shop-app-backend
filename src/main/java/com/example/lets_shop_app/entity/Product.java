package com.example.lets_shop_app.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "product")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;

	@Column(name = "name")
	private String name;
	
	@Column(name = "description")
	private String description;

	@Column(name = "thumbnail")
	private String thumbnail;
	
	@Column(name = "price")
	private BigDecimal price;
	
	@Column(name = "stock")
	private long stock;
	
	@Column(name = "brand")
	private String brand;
	
	@ManyToOne
	@JoinColumn(name = "category", nullable = false)
	@JsonIgnoreProperties("products")
	private ProductCategory category;

	@OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<CartItem> cartItems;
}