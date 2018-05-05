package pl.websm.kalkulator.service.ratesAPI;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@Service
public class RateServiceBean implements RateService{

    @Value("${api.getOneExchangeRateUrl}")
    private String apiUrl;

    private final RestTemplate restTemplate;

    public RateServiceBean(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    @Override
    public Optional<RateResponse> getRateResponse(String currencyCode) {
        currencyCode = currencyCode.toLowerCase();
        RateResponse rateResponse;

        try {
            log.info("Getting data for " + currencyCode.toUpperCase() + ". from " + apiUrl + " " + LocalDateTime.now().toString());
            rateResponse = restTemplate.getForObject(
                    apiUrl,
                    RateResponse.class,
                    currencyCode);
            log.info("Data retrieved " + LocalDateTime.now().toString());
        } catch (Exception e) {
            rateResponse = null;
            log.info("Connection failed with " + e.toString());
        }
        return Optional.ofNullable(rateResponse);
    }
}
