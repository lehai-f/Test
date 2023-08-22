package it1.mock.hailh17.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import it1.mock.hailh17.model.Vendor;

public interface VendorRepositories extends JpaRepository<Vendor, String> {
	
	@Query("SELECT v From Vendor v "
			+ " INNER JOIN v.country c "
			+ " WHERE (:vdName ='' OR :vdName ='All' OR v.vendorName =:vdName) AND (:ctry ='' OR :ctry = 'All' OR c.countryName LIKE %:ctry%) ")
	public Page<Vendor> findByNameOrCountryName(Pageable page,@Param("vdName") String vdName, @Param("ctry") String ctry);
	
	
	@Query("SELECT v.vendorName From Vendor v")
	public List<String> findAllVendorName();
	
	boolean existsByVendorName(String vendorName);

	@Query("SELECT v FROM Vendor v ORDER BY v.vendorName")
	public Page<Vendor> findAllWithPage(Pageable pages);
}
