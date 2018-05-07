package pl.websm.calculator.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.websm.calculator.model.Country;

import java.util.Optional;

@Repository
public interface CountryRepository extends CrudRepository<Country, Long> {
    Optional<Country> findById(Long id);
    Optional<Country> findByCountryCode(String baseCountryCode);
}
