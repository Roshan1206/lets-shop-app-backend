package com.example.lets_shop_app.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Entity
@Table(name = "cart")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Cart {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "product_id")
	private Long productId;
	
	@Column(name = "product_name")
	private String productName;
	
	@Column(name = "product_thumbnail")
	private String productThumbnail;
	
	@Column(name = "product_price")
	private Double productPrice;
	
	@Column(name = "product_quantity")
	private Integer productQuantity;
	
	@Column(name = "total_product_price")
	private Double totalProductPrice;
}
