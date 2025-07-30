package com.example.lets_shop_app.service;

import com.example.lets_shop_app.dto.CountryResponse;
import com.example.lets_shop_app.entity.State;

import java.util.List;

/**
 * Service class for Address related function
 *
 * @author Roshan
 */
public interface AddressService {


    /**
     * Get all countries with their ID, Code and Name
     *
     * @return List<CountryResponse> - List of CountryResponse
     */
    List<CountryResponse> getAllCountries();

    /**
     * Get all states with their ID, Code and Country
     *
     * @param id - country id
     * @return List<State> - List of CountryResponse
     */
    List<State> getStates(int id);
}
