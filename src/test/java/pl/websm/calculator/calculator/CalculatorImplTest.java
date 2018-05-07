package pl.websm.calculator.calculator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.websm.calculator.model.Country;

import javax.money.Monetary;
import javax.money.MonetaryAmount;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CalculatorImplTest {

    @Value("#{new Double('${calculator.daysPerMonth}')}")
    private Double daysPerMonth;

    private Calculator calculator;
    private Country baseCountry;

    @Before
    public void setUp() throws Exception {
        calculator = new CalculatorImpl();
        baseCountry = new Country();
        baseCountry.setId(1l);
        baseCountry.setCountryCode("PL");
        baseCountry.setLanguageCode("pl");
        baseCountry.setCurrencyCode("PLN");
    }

    @Test
    public void convertMonetaryAmountToPLN() {
        Country germany = new Country();
        germany.setId(2l);
        germany.setCountryCode("DE");
        germany.setLanguageCode("de");
        germany.setCurrencyCode("EUR");
        germany.setMidExchangeRate(4.2771);

        MonetaryAmount amount = Monetary.getDefaultAmountFactory()
                .setCurrency(Monetary.getCurrency(germany.getCurrencyCode()))
                .setNumber(new Double(5000.9897))
                .create();

        MonetaryAmount baseCountryAmount = calculator
                .convertMonetaryAmountToBaseCountryAmount(germany, amount, baseCountry);

        assertEquals("21 389,73 PLN",
                calculator.convertMonetaryAmountToFormattedString(baseCountryAmount, baseCountry));
    }

    @Test
    public void calculateContractNetMonthlyIncome() {
        Country uk = new Country();
        uk.setId(3l);
        uk.setCountryCode("UK");
        uk.setLanguageCode("uk");
        uk.setCurrencyCode("GBP");
        uk.setMidExchangeRate(4.843);
        uk.setFixedCosts(600.0);
        uk.setTaxValue(0.25);

        assertEquals("8 717,4 PLN",
                    calculator.calculateContractNetMonthlyIncome(100.00,
                        daysPerMonth,
                        uk,
                        baseCountry));

        assertEquals("62 816,13 PLN",
                calculator.calculateContractNetMonthlyIncome(777.00,
                        daysPerMonth,
                        uk,
                        baseCountry));

        assertEquals("62 842,48 PLN",
                calculator.calculateContractNetMonthlyIncome(777.33,
                        daysPerMonth,
                        uk,
                        baseCountry));
    }
}