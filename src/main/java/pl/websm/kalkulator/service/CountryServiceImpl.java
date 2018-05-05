package pl.websm.kalkulator.service;

import org.springframework.stereotype.Service;
import pl.websm.kalkulator.model.Country;
import pl.websm.kalkulator.repository.CountryRepository;
import pl.websm.kalkulator.service.ratesAPI.Rate;

import java.util.*;

@Service
public class CountryServiceImpl implements CountryService {
    private final CountryRepository countryRepository;

    public CountryServiceImpl(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    @Override
    public List<Country> getAllCountries() {
        Iterator<Country> countryIterable = countryRepository.findAll().iterator();
        List<Country> countries = new ArrayList<>();
        countryIterable.forEachRemaining(countries::add);
        return countries;
    }

    @Override
    public Country findById(Long id) {
        Optional<Country> optionalCountry = countryRepository.findById(id);
        if (optionalCountry.isPresent()){
            return optionalCountry.get();
        }
        return null;
    }

    @Override
    public void update(Country country, Rate rate) {
        if (country.getExchangeRateDateLastUpdate() != null) {
            if (!country.getExchangeRateDateLastUpdate().isEqual(rate.getEffectiveDate())) {
                afterValidationUpdate(country, rate);
            }
        } else {
            afterValidationUpdate(country,rate);
        }
    }
    private void afterValidationUpdate(Country country, Rate rate){
        country.setMidExchangeRate(rate.getMid());
        country.setExchangeRateDateLastUpdate(rate.getEffectiveDate());
        countryRepository.save(country);
    }
}
