package pl.websm.kalkulator.service.ratesAPI;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.time.LocalDate;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class Rate {
    LocalDate effectiveDate;
    Double mid;
}
