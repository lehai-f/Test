package it1.mock.hailh17.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it1.mock.hailh17.model.Schedule;
import it1.mock.hailh17.repositories.ScheduleRepositories;

@Service
public class ScheduleServices {

	@Autowired
	ScheduleRepositories scheduleRepo;

	/**
	 * xóa schedule theo idschedule
	 * @author HaiLH17
	 * @BirthDate: 1994/07/07
	 * @param id
	 */
	public void delScheduleById(long id) {
		scheduleRepo.deleteById(id);
	}

	/**
	 * lấy thông tinh schedule theo id
	 * @author HaiLH17
	 * @BirthDate: 1994/07/07
	 * @param id
	 * @return
	 */
	public Optional<Schedule>getScheduleById(long id) {
		return scheduleRepo.findById(id);
	}
	
	/**
	 * lấy tất cả thông tin schedule theo id vendor
	 * @author HaiLH17
	 * @BirthDate: 1994/07/07
	 * @param id
	 * @return
	 */
	public List<Schedule> getAllScheduleByVendorid(String id){
		return scheduleRepo.findAllByVendorID(id);
	}
	
	
	

}
