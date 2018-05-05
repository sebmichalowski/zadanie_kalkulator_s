package pl.websm.kalkulator.service.ratesAPI;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RateServiceBeanTestIT {
    private final String currencyCode = "gbp";

    @Autowired
    private RateService rateService;

    @Test
    public void getRate() {
        Optional<RateResponse> rateOptional = rateService.getRateResponse(currencyCode);
        if (rateOptional.isPresent()) {
            assertEquals(currencyCode.toUpperCase(), rateOptional.get().code);
        } else {
            assertFalse(rateOptional.isPresent());
        }
    }
}