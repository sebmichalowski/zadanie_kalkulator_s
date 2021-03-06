package pl.websm.calculator.web.api.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pl.websm.calculator.calculator.Calculator;
import pl.websm.calculator.calculator.CalculatorImpl;
import pl.websm.calculator.model.Country;
import pl.websm.calculator.service.CountryService;

@RestController
@RequestMapping(value = "/")
public class CalculatorController {
    private final CountryService countryService;


    @Value("#{new Double('${calculator.daysPerMonth}')}")
    private Double daysPerMonth;

    @Value("${country.baseCountry}")
    private String baseCountryCode;
    private Calculator calculator;

    public CalculatorController(CountryService countryService) {
        this.countryService = countryService;
        this.calculator = new CalculatorImpl();
    }

    @RequestMapping(
            value = "{grossValuePerDay}/{countryCodeToConvertFrom}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getCalculatedIncomeWithCountryCode(@PathVariable Double grossValuePerDay,
                                                                     @PathVariable String countryCodeToConvertFrom){
        Country baseCountry = countryService.findByCountryCode(baseCountryCode);
        Country countryToConvertFrom = countryService.findByCountryCode(countryCodeToConvertFrom.toUpperCase());
        if (baseCountry == null || countryToConvertFrom == null) {
            return new ResponseEntity<>("No such country", HttpStatus.BAD_REQUEST);
        }
        String calculatedContractNetMonthlyIncome = calculator
                .calculateContractNetMonthlyIncome(grossValuePerDay, daysPerMonth, countryToConvertFrom, baseCountry);
        return new ResponseEntity<>(calculatedContractNetMonthlyIncome, HttpStatus.OK);
    }
}
