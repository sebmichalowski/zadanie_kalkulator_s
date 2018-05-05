package pl.websm.kalkulator.service.ratesAPI;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.Set;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class RateResponse {
    String code;
    Set<Rate> rates;
}