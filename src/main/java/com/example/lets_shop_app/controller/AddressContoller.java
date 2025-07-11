package com.example.lets_shop_app.controller;

import java.util.List;

import com.example.lets_shop_app.service.AddressService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.lets_shop_app.entity.State;
import com.example.lets_shop_app.dto.CountryResponse;

import lombok.RequiredArgsConstructor;

@Tag(name = "Address Endpoints", description = "Operational REST API endpoints related to Address")
@RestController
@RequestMapping("/address")
@RequiredArgsConstructor
public class AddressContoller {

	private final AddressService addressService;
	
	@GetMapping("/countries")
	public List<CountryResponse> getCountries(){
		return addressService.getAllCountries();
	}
	
	@GetMapping("/states/{id}")
	public List<State> getStates(@PathVariable int id){
		return addressService.getStates(id);
	}
}
