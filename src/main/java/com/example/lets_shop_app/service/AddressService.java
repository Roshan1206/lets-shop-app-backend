package com.example.lets_shop_app.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.lets_shop_app.dao.CountryRepository;
import com.example.lets_shop_app.dao.StateRepository;
import com.example.lets_shop_app.entity.Country;
import com.example.lets_shop_app.entity.State;
import com.example.lets_shop_app.dto.CountryResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AddressService {

	private final CountryRepository countryRepository;
	private final StateRepository stateRepository;
	
	public List<CountryResponse> getAllCountries(){
		List<Country> countries = countryRepository.findAll();
		List<CountryResponse> countriesList = new ArrayList<CountryResponse>();
		for (Country country : countries) {
			CountryResponse countriesResponse = 
					new CountryResponse(country.getId() ,country.getCode(), country.getName());
			countriesList.add(countriesResponse);
		}
		return countriesList;
	}
	
	public List<State> getStates(Integer id){
		System.out.println(id);
		return stateRepository.findByCountryId(id);
	}
	
}
