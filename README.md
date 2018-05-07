# zadanie_kalkulator_s

## Running

The project uses [Maven] and [Java 8]

To run the application go to app folder and run command

```
mvn spring-boot:run
```


## Usage

This is rest app so you have to communicate with your server using get methods

### http://localhost:8080/{grossValuePerDay}/{countryCodeToConvertFrom}


Examples:

[http://localhost:8080/222/de](http://localhost:8080/222/de)

```
http://localhost:8080/222.33/de
```


[http://localhost:8080/777.33/pl](http://localhost:8080/777.33/pl)

```
http://localhost:8080/777.33/pl
```

[http://localhost:8080/2/uk](http://localhost:8080/2/uk)

```
http://localhost:8080/2/uk
```

### Database link and insertions example:

you can navigate to database manager in your browser by:

[http://localhost:8080/h2-console](http://localhost:8080/h2-console)
Setting Name: Generic H2 (Embedded)
Driver Class: org.h2.Driver
Jdbc-url: jdbc:h2:mem:testdb
User: sa

SQL:

INSERT INTO
Country (country_code, language_code, currency_code, tax_value, fixed_costs)
VALUES
('PL', 'pl', 'PLN', 0.19, 1200);


### Url to check your entries in the database without logging into manager (Json format)
[http://localhost:8080/countries](http://localhost:8080/countries)

