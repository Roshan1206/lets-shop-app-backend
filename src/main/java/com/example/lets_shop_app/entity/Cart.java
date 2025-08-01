package com.example.lets_shop_app.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Entity
@Table(name = "cart")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Cart extends BaseEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "product_id")
	private long productId;
	
	@Column(name = "product_name")
	private String productName;
	
	@Column(name = "product_thumbnail")
	private String productThumbnail;
	
	@Column(name = "product_price")
	private Double productPrice;
	
	@Column(name = "product_quantity")
	private int productQuantity;
	
	@Column(name = "total_product_price")
	private Double totalProductPrice;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;
}
