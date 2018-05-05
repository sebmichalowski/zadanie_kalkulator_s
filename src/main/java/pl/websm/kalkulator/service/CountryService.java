package pl.websm.kalkulator.service;

import pl.websm.kalkulator.model.Country;
import pl.websm.kalkulator.service.ratesAPI.Rate;

import java.util.List;

public interface CountryService {
    List<Country> getAllCountries();
    Country findById(Long id);
    void update(Country country, Rate rate);

}
