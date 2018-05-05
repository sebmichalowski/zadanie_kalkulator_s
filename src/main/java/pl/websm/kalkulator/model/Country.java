package pl.websm.kalkulator.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
public class Country {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String countryCode;
    @Column(unique = true)
    private String languageCode;
    @Column(unique = true)
    private String currencyCode;
    private Double taxValue;
    private Double fixedCosts;
    private LocalDate exchangeRateDateLastUpdate;
    private Double midExchangeRate;
}