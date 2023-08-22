package it1.mock.hailh17.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import it1.mock.hailh17.model.Schedule;
import it1.mock.hailh17.model.Vendor;
import it1.mock.hailh17.repositories.ScheduleRepositories;
import it1.mock.hailh17.repositories.VendorRepositories;
import it1.mock.hailh17.validation.Util;


@Service
public class VendorServices {
	
	@Autowired
	VendorRepositories vendorRepo;
	
	@Autowired
	ScheduleRepositories scRepo;
	
	//Get All Vendor with Page
	/**
	 * lấy tất cả thông tin vendor theo page
	 * @author HaiLH17
	 * @BirthDate: 1994/07/07
	 * @param pages
	 * @return
	 */
	public Page<Vendor> getAllVendorWithPage(Pageable pages){
		return vendorRepo.findAllWithPage(pages);
	}
	
	//Get Vendor by name or country
	/**
	 * filter lấy thông tinh theo vendorName và countryName theo page
	 * @author HaiLH17
	 * @BirthDate: 1994/07/07
	 * @param page
	 * @param vdName
	 * @param ctry
	 * @return
	 */
	public Page<Vendor> getAllVendorByNameOrCountry(Pageable page, String vdName, String ctry){
		return vendorRepo.findByNameOrCountryName(page, vdName, ctry);
	}
	
	//Get Vendor by ID
	/**
	 * @author HaiLH17
	 * @BirthDate: 1994/07/07
	 * @param id
	 * @return
	 */
	public Optional<Vendor> getVendorByID(String id) {
		return vendorRepo.findById(id);
	}
	
	//Get ALL Vendor 
	/**
	 * 
	 * @author HaiLH17
	 * @BirthDate: 1994/07/07
	 * @return
	 */
	public List<Vendor> getAllVendor(){
		return vendorRepo.findAll(Sort.by(Sort.Direction.ASC, "vendorName"));
	}
	
//	//Get All vendorName
//	public List<String> getAllVendorName(){
//		return vendorRepo.findAllVendorName();
//	}
//	
	/**
	 * @author HaiLH17
	 * @BirthDate: 1994/07/07
	 * @param name
	 * @return
	 */
	public boolean checkExistsVendorByName(String name) {
		return vendorRepo.existsByVendorName(name);
	}
	
	//Get currenPage update
	/**
	 * @author HaiLH17
	 * @BirthDate: 1994/07/07
	 * @param id
	 * @return
	 */
	public int getPageUpdateByIDVendor(String id) {
		List<Vendor> vendors = vendorRepo.findAll(Sort.by(Sort.Direction.ASC, "vendorName"));
		int page = -1;
		for(int i = 0; i<vendors.size(); i++) {
			if(vendors.get(i).getVendorID().equals(id)) {
				page = i;
				break;
			}
		}
		return page/Util.PAGE_SIZE;
	}
	
	
	//Update vendor
	/**
	 * @author HaiLH17
	 * @BirthDate: 1994/07/07
	 * @param vendor
	 */
	public void saveVendor(Vendor vendor) {
		vendor.setVdUpTime(String.valueOf(LocalDateTime.now()));
		vendorRepo.save(vendor);
		for (Schedule s : vendor.getSchedules()) {
			s.setVendor(vendor);
			scRepo.save(s);
		}
	}
	
}
