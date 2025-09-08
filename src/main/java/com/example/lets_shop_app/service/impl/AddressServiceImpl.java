package com.example.lets_shop_app.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.example.lets_shop_app.service.AddressService;
import org.springframework.stereotype.Service;

import com.example.lets_shop_app.repository.CountryRepository;
import com.example.lets_shop_app.repository.StateRepository;
import com.example.lets_shop_app.entity.Country;
import com.example.lets_shop_app.entity.State;
import com.example.lets_shop_app.dto.response.CountryResponse;


/**
 * Service class for Address related operations
 *
 * @author Roshan
 */
@Service
public class AddressServiceImpl implements AddressService {


	/**
	 * Repository responsible for handling Country related operations
	 */
	private final CountryRepository countryRepository;


	/**
	 * Repository responsible for handling State related operations
	 */
	private final StateRepository stateRepository;


	/**
	 * Constructor for injecting {@link CountryRepository} and {@link StateRepository} interface
	 */
    public AddressServiceImpl(CountryRepository countryRepository, StateRepository stateRepository) {
        this.countryRepository = countryRepository;
        this.stateRepository = stateRepository;
    }


    /**
	 * Get all countries with their ID, Code and Name
	 *
	 * @return List<CountryResponse> - List of CountryResponse
	 */
	@Override
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


	/**
	 * Get all states with their ID, Code and Country
	 *
	 * @param id - country id
	 * @return List<State> - List of CountryResponse
	 */
	@Override
	public List<State> getStates(int id){
		return stateRepository.findByCountryId(id);
	}
	
}
