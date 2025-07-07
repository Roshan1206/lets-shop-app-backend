package com.example.lets_shop_app.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "product")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;

	@Column(name = "name")
	private String name;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "price")
	private Double price;
	
	@Column(name = "stock")
	private long stock;
	
	@Column(name = "brand")
	private String brand;
	
	@ManyToOne
	@JoinColumn(name = "category", nullable = false)
	private ProductCategory category;
	
//	@Column(name = "category")
//	private Long category;
	
	@Column(name = "thumbnail")
	private String thumbnail;
	
}