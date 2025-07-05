package com.example.lets_shop_app.service;

import com.example.lets_shop_app.dto.CountryResponse;
import com.example.lets_shop_app.entity.State;

import java.util.List;

public interface AddressService {
    List<CountryResponse> getAllCountries();
    List<State> getStates(int id);
}
