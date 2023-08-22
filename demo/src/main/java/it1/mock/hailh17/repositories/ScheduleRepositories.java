package it1.mock.hailh17.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import it1.mock.hailh17.model.Schedule;

public interface ScheduleRepositories extends JpaRepository<Schedule, Long> {

	@Query("SELECT s FROM Schedule s WHERE s.vendor.vendorID =:id")
	public List<Schedule> findAllByVendorID(@Param("id") String id);
}
