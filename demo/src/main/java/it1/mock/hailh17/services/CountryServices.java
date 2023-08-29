package it1.mock.hailh17.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import it1.mock.hailh17.model.Country;
import it1.mock.hailh17.repositories.CountryRepositories;

/**
 * country services
 * @author HaiLH17
 * @BirthDate: 1994/07/07
 */
@Service
public class CountryServices {
    @Autowired
    CountryRepositories countryRepo;

    // Get All Country
    /**
     * lấy dử liệu tất cả country
     * 
     * @author HaiLH17
     * @BirthDate: 1994/07/07
     * @return list Country
     */
    public List<Country> getAllCountry() {
        return countryRepo.findAll(Sort.by(Sort.Direction.ASC, "countryName"));
    }

    // Check Exists
    /**
     * kiểm tra tôn tại của country theo name
     * 
     * @author HaiLH17
     * @BirthDate: 1994/07/07
     * @param name
     * @return
     */
    public boolean checkCountryByName(String name) {
        return countryRepo.existsByCountryName(name);
    }

    // get country by nme
    /**
     * lây thông tinh của 1 country theo country name
     * 
     * @author HaiLH17
     * @BirthDate: 1994/07/07
     * @param name
     * @return
     */
    public Country getCountryByName(String name) {
        return countryRepo.findByCountryName(name);
    }
}
