package pl.websm.calculator.calculator;

import pl.websm.calculator.model.Country;

import javax.money.Monetary;
import javax.money.MonetaryAmount;


public class CalculatorImpl implements Calculator {

    @Override
    public MonetaryAmount convertMonetaryAmountToBaseCountryAmount(Country countryToConvertFrom,
                                                                   MonetaryAmount monetaryAmountToConvertFrom,
                                                                   Country baseCountry) {
        MonetaryAmount amountMultiplied = monetaryAmountToConvertFrom
                .multiply(countryToConvertFrom.getMidExchangeRate());

        return Monetary.getDefaultAmountFactory()
                .setCurrency(Monetary.getCurrency(baseCountry.getCurrencyCode()))
                .setNumber(amountMultiplied.getNumber())
                .create();
    }

    @Override
    public String calculateContractNetMonthlyIncome(Double grossValuePerDay, Double daysPerMonth, Country countryToConvertFrom, Country baseCountry) {
        return null;
    }

    @Override
    public String convertMonetaryAmountToFormattedString(MonetaryAmount monetaryAmount, Country baseCountry) {
        return null;
    }
}