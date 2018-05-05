package pl.websm.kalkulator.service.ratesAPI;

import java.util.Optional;

public interface RateService {
    Optional<RateResponse> getRateResponse(String currencyCode);
}
