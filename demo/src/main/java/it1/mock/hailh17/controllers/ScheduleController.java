package it1.mock.hailh17.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import it1.mock.hailh17.model.Schedule;
import it1.mock.hailh17.services.ScheduleServices;

@Controller
@RequestMapping("/schedule")
public class ScheduleController {

	@Autowired
	ScheduleServices scheduleSv;
/**
 * delete Schedule controller
 * @author HaiLH17
 * @BirthDate: 1994/07/07
 * @param id
 * @param vdID
 * @param rd
 * @return
 */
	@GetMapping("/delete")
	public String deleteSchedule(@RequestParam("id") long id, @RequestParam("vdID") String vdID,
			RedirectAttributes rd) {
		Optional<Schedule> a = scheduleSv.getScheduleById(id);
		if (!a.isPresent()) {
			rd.addFlashAttribute("udError", "The selected record has been removed by someone");
			return "redirect:/vendor/update/" + vdID;
		}
		Schedule sche = a.get();
		
		if (sche.getContractID() != null) {
			rd.addFlashAttribute("udError",
					"Some contracts have been executing or scheduling in this period of time, record can’t be removed");
			return "redirect:/vendor/update/" + vdID;
		}
		
		if (sche.getVendor().getVendorID().equals(vdID)) {
			scheduleSv.delScheduleById(id);
			return "redirect:/vendor/update/" + sche.getVendor().getVendorID();
		}
		
		rd.addFlashAttribute("udError", "Invalid selected");
		return "redirect:/vendor/update/" + vdID;

	}
}
