package pl.websm.kalkulator.sheduler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pl.websm.kalkulator.model.Country;
import pl.websm.kalkulator.service.CountryService;
import pl.websm.kalkulator.service.ratesAPI.Rate;
import pl.websm.kalkulator.service.ratesAPI.RateResponse;
import pl.websm.kalkulator.service.ratesAPI.RateService;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
@Slf4j
public class UpdateDatabaseScheduler implements ApplicationListener<ContextRefreshedEvent> {
    private final RateService rateService;
    private final CountryService countryService;

    @Value("${country.baseCountry}")
    private String baseCountry;
    private boolean justAfterRestart = true;

    public UpdateDatabaseScheduler(RateService rateService, CountryService countryService) {
        this.rateService = rateService;
        this.countryService = countryService;
    }

    @Scheduled(cron = "0 0 9,12 * * *")
    private void updateDatabase(){
        log.info("Updating database " + LocalDateTime.now());
        List<Country> allCountries = countryService.getAllCountries();
        DayOfWeek dayOfTheWeek = LocalDate.now().getDayOfWeek();
        if (dayOfTheWeek.getValue() < 6 || justAfterRestart) {
            for (Country country : allCountries) {
                if (country.getCountryCode().equals(baseCountry)) {
                    continue;
                }

                Optional<RateResponse> rateResponse = rateService.getRateResponse(
                        country.getCurrencyCode().toLowerCase());
                if (rateResponse.isPresent()) {
                    Rate rate = rateResponse.get().getRates().iterator().next();
                    countryService.update(country, rate);
                    log.info(country.getCountryCode() + " updated at: " + LocalDateTime.now());
                }
            }
        }
        log.info("Update completed at: " + LocalDateTime.now());
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        updateDatabase();
        justAfterRestart = false;
    }
}
