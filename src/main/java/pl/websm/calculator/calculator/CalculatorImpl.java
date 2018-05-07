package pl.websm.calculator.calculator;

import pl.websm.calculator.model.Country;

import javax.money.Monetary;
import javax.money.MonetaryAmount;
import javax.money.format.MonetaryAmountFormat;
import javax.money.format.MonetaryFormats;
import java.util.Locale;


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
    public String calculateContractNetMonthlyIncome(Double grossValuePerDay,
                                                    Double daysPerMonth,
                                                    Country countryToConvertFrom,
                                                    Country baseCountry) {
        MonetaryAmount grossAmountPerDay = Monetary.getDefaultAmountFactory()
                .setCurrency(Monetary.getCurrency(countryToConvertFrom.getCurrencyCode()))
                .setNumber(grossValuePerDay)
                .create();

        MonetaryAmount grossAmountPerMonth = multiplyDaysOfTheMonthWithGrossValuePerDay(daysPerMonth, grossAmountPerDay);
        MonetaryAmount taxAmountPerMonth = calculateTaxAmount(grossAmountPerMonth, countryToConvertFrom);
        MonetaryAmount netAmountPerMonth = grossAmountPerMonth.subtract(taxAmountPerMonth);

        MonetaryAmount netAmountInBaseCountryCurrency = convertMonetaryAmountToBaseCountryAmount(
                countryToConvertFrom,
                netAmountPerMonth,
                baseCountry);

        return convertMonetaryAmountToFormattedString(netAmountInBaseCountryCurrency, baseCountry);
    }

    @Override
    public String convertMonetaryAmountToFormattedString(MonetaryAmount monetaryAmount, Country baseCountry) {
        MonetaryAmountFormat baseCountryFormat = MonetaryFormats
                .getAmountFormat(new Locale(baseCountry.getLanguageCode(), baseCountry.getCountryCode()));
        return baseCountryFormat.format(monetaryAmount);
    }

    private MonetaryAmount multiplyDaysOfTheMonthWithGrossValuePerDay(Double daysPerMonth,
                                                                      MonetaryAmount grossAmountPerDay) {
        return grossAmountPerDay.multiply(daysPerMonth);
    }

    private MonetaryAmount calculateTaxAmount(MonetaryAmount grossAmountPerMonth, Country countryToPayTaxIn) {
        MonetaryAmount fixedCosts = Monetary.getDefaultAmountFactory()
                .setCurrency(Monetary.getCurrency(countryToPayTaxIn.getCurrencyCode()))
                .setNumber(countryToPayTaxIn.getFixedCosts())
                .create();


        MonetaryAmount income = grossAmountPerMonth.subtract(fixedCosts);

        if (income.isNegativeOrZero()) {
            return Monetary.getDefaultAmountFactory()
                    .setCurrency(Monetary.getCurrency(countryToPayTaxIn.getCurrencyCode()))
                    .setNumber(0)
                    .create();
        } else {
            MonetaryAmount tax = income.multiply(countryToPayTaxIn.getTaxValue());
            return tax.with(Monetary.getDefaultRounding());
        }
    }
}