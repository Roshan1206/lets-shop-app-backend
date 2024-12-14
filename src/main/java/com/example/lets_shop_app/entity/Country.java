package com.example.lets_shop_app.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "country")
@Data
public class Country {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "code")
	private String code;
	
	@Column(name = "name")
	private String name;
	
//	@OneToMany(mappedBy = "country")
//	@JsonIgnore
//	private List<State> states;
}
