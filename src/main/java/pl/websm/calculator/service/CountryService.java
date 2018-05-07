package pl.websm.calculator.service;

import pl.websm.calculator.model.Country;
import pl.websm.calculator.service.ratesAPI.Rate;

import java.util.List;

public interface CountryService {
    List<Country> getAllCountries();
    Country findById(Long id);
    Country findByCountryCode(String countryCode);
    void update(Country country, Rate rate);

}
