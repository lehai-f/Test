package it1.mock.hailh17.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import it1.mock.hailh17.model.Country;

public interface CountryRepositories extends JpaRepository<Country, Long> {

	boolean existsByCountryName(String countryName);

	Country findByCountryName(String countryName);
}
