package pl.websm.calculator.calculator;

import pl.websm.calculator.model.Country;

import javax.money.MonetaryAmount;

public interface Calculator {
    MonetaryAmount convertMonetaryAmountToBaseCountryAmount(Country countryToConvertFrom,
                                                            MonetaryAmount monetaryAmountToConvertFrom,
                                                            Country baseCountry);
    String calculateContractNetMonthlyIncome(Double grossValuePerDay,
                                             Double daysPerMonth,
                                             Country countryToConvertFrom,
                                             Country baseCountry);
    String convertMonetaryAmountToFormattedString(MonetaryAmount monetaryAmount, Country baseCountry);
}
