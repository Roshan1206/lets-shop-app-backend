package com.example.lets_shop_app.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Order extends BaseEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column
	private long productId;

	@Column
	private BigDecimal productPrice;

	@Column
	private int productQuantity;

	@Column
	private BigDecimal totalPrice;

}
