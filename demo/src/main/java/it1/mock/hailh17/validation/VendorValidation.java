package it1.mock.hailh17.validation;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it1.mock.hailh17.model.Country;
import it1.mock.hailh17.model.Schedule;
import it1.mock.hailh17.model.Vendor;
import it1.mock.hailh17.services.CountryServices;
import it1.mock.hailh17.services.ScheduleServices;
import it1.mock.hailh17.services.VendorServices;

/**
 * Vendor validation
 * @author HaiLH17
 * @BirthDate: 1994/07/07
 */
@Component
public class VendorValidation implements Validator {

    @Autowired
    VendorServices vdServices;

    @Autowired
    CountryServices ctrServices;

    @Autowired
    ScheduleServices scServices;

    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }

    public static List<Schedule> agreedList = new ArrayList<Schedule>();

    @Override
    /**
     * @author HaiLH17
     * @BirthDate: 1994/07/07 validator
     */
    public void validate(Object target, Errors errors) {
        if (!(target instanceof Vendor)) {
            return;
        }
        Vendor newVendor = (Vendor) target;
        String newCtrName = newVendor.getCountry().getCountryName();
        Optional<Vendor> opOldVendor = vdServices.getVendorByID(newVendor.getVendorID());
        Vendor oldVendor = opOldVendor.get();
        String oldCtrName = oldVendor.getCountry().getCountryName();
        List<Schedule> oldScheList = scServices.getAllScheduleByVendorid(newVendor.getVendorID());
        List<Schedule> newScheList = newVendor.getSchedules();
        int staffTotal = newVendor.getStaffStrength();

        if (!newVendor.getVendorName().equals(oldVendor.getVendorName())
                && vdServices.checkExistsVendorByName(newVendor.getVendorName())) {
            errors.rejectValue("vendorName", null,
                    "The vendor name exists in the system. Please enter a new vendor name");
        }

        if (!newCtrName.equals(oldCtrName) && !newVendor.getSchedules().isEmpty()) {
            errors.rejectValue("country.countryName", null, "Invalid");
            return;
        }

        if (ctrServices.checkCountryByName(newVendor.getCountry().getCountryName()) == false) {
            errors.rejectValue("country.countryName", null,
                    "The Country name doesn't exists in the system. Please select a new country");
        } else {
            Country u = ctrServices.getCountryByName(newVendor.getCountry().getCountryName());
            newVendor.setCountry(u);
        }

        for (int i = 0; i < newScheList.size(); i++) {
            // validator new schude
            if (!oldScheList.contains(newScheList.get(i)) && newScheList.get(i).getScheduleID() == 0) {
                newSchedule(errors, newVendor, oldScheList, newScheList, staffTotal, i);
                // validator change schude
            } else if (!oldScheList.contains(newScheList.get(i)) && newScheList.get(i).getScheduleID() != 0) {
                changeSchedule(errors, newVendor, newScheList, staffTotal, i);
            }
        }
    }

    /**
     * Check and validator new Schedule
     * 
     * @author HaiLH17
     * @BirthDate: 1994/07/07
     * @param errors
     * @param newVendor
     * @param newScheList
     * @param staffTotal
     * @param i
     */
    public void changeSchedule(Errors errors, Vendor newVendor, List<Schedule> newScheList, int staffTotal, int i) {
        boolean flag = true;

        Optional<Schedule> opSche = scServices.getScheduleById(newScheList.get(i).getScheduleID());
        Schedule oldSche = opSche.get();

        if (newScheList.get(i).getStartDate() == null) {
            errors.rejectValue("schedules[" + i + "].startDate", null, "Start Date must be entered.");
            flag = false;
        }

        if (newScheList.get(i).getEndDate() == null) {
            errors.rejectValue("schedules[" + i + "].endDate", null, "End Date must be entered.");
            flag = false;
        }

        if (newScheList.get(i).getNumberStaff() < 1) {
            errors.rejectValue("schedules[" + i + "].numberStaff", null, "Number of Staff must be a number and > 0");
            flag = false;
        }

        if (newScheList.get(i).getNumberStaff() > 9999) {
            errors.rejectValue("schedules[" + i + "].numberStaff", null, "Number of Staff must be a number and < 9999");
            flag = false;
        }

        if (newScheList.get(i).getRecordPerDay() < 1) {
            errors.rejectValue("schedules[" + i + "].recordPerDay", null, "Records Per Day must be a number and > 0");
            flag = false;
        }

        if (newScheList.get(i).getRecordPerDay() > 9999) {
            errors.rejectValue("schedules[" + i + "].recordPerDay", null,
                    "Records Per Day must be a number and < 9999");
            flag = false;
        }

        if (newScheList.get(i).getStartDate() != null && newScheList.get(i).getEndDate() != null
                && newScheList.get(i).getStartDate().isAfter(newScheList.get(i).getEndDate())) {
            errors.rejectValue("schedules[" + i + "].startDate", null,
                    "Start Date must be earlier than or equals to End Date");
            flag = false;
        }

        if (flag) {
            newScheList.get(i).setVendor(newVendor);
            if (!newScheList.get(i).getStartDate().equals(oldSche.getStartDate())) {
                startDateNew(newScheList.get(i), errors, newScheList, i, staffTotal);
            }

            if (!newScheList.get(i).getEndDate().equals(oldSche.getEndDate())) {
                valiEndDateChange(newScheList.get(i), errors, newScheList, staffTotal, i);
            }

            if (newScheList.get(i).getNumberStaff() != oldSche.getNumberStaff()) {
                valiSchudeNew(newScheList.get(i), errors, newScheList, staffTotal, i);
            }
        }
    }

    /**
     * Check validator for change Schedule
     * 
     * @author HaiLH17
     * @BirthDate: 1994/07/07
     * @param errors
     * @param newVendor
     * @param oldScheList
     * @param newScheList
     * @param staffTotal
     * @param i
     */
    public void newSchedule(Errors errors, Vendor newVendor, List<Schedule> oldScheList, List<Schedule> newScheList,
            int staffTotal, int i) {
        boolean flag = true;
        if (newScheList.get(i).getStartDate() == null) {
            errors.rejectValue("schedules[" + i + "].startDate", null, "Start Date must be entered.");
            flag = false;
        }

        if (newScheList.get(i).getEndDate() == null) {
            errors.rejectValue("schedules[" + i + "].endDate", null, "End Date must be entered.");
            flag = false;
        }

        if (newScheList.get(i).getNumberStaff() < 1) {
            errors.rejectValue("schedules[" + i + "].numberStaff", null, "Number of Staff must be a number and > 0");
            flag = false;
        }

        if (newScheList.get(i).getNumberStaff() > 9999) {
            errors.rejectValue("schedules[" + i + "].numberStaff", null, "Number of Staff must be a number and < 9999");
            flag = false;
        }

        if (newScheList.get(i).getRecordPerDay() < 1) {
            errors.rejectValue("schedules[" + i + "].recordPerDay", null, "Records Per Day must be a number and > 0");
            flag = false;
        }

        if (newScheList.get(i).getRecordPerDay() > 9999) {
            errors.rejectValue("schedules[" + i + "].recordPerDay", null,
                    "Records Per Day must be a number and < 9999");
            flag = false;
        }

        if (newScheList.get(i).getStartDate() != null && newScheList.get(i).getEndDate() != null
                && newScheList.get(i).getStartDate().isAfter(newScheList.get(i).getEndDate())) {
            errors.rejectValue("schedules[" + i + "].startDate", null,
                    "Start Date must be earlier than or equals to End Date");
            flag = false;
        }

        if (flag) {
            newScheList.get(i).setVendor(newVendor);
            if (oldScheList.isEmpty()) {
                valiSchudeNew(newScheList.get(i), errors, newScheList, staffTotal, i);
            } else {
                LocalDate minStart = oldScheList.stream().map(Schedule::getStartDate).min(LocalDate::compareTo).get();
                LocalDate maxEnd = oldScheList.stream().map(Schedule::getEndDate).max(LocalDate::compareTo).get();
                if (!newScheList.get(i).getEndDate().isAfter(minStart)
                        || !newScheList.get(i).getStartDate().isBefore(maxEnd)) {
                    if (newScheList.get(i).getNumberStaff() > staffTotal) {
                        errors.rejectValue("schedules[" + i + "].numberStaff", null,
                                "Staff productivity defined is more than staff strength of vendor. Please check.");
                    } else if (newScheList.get(i).getNumberStaff() < staffTotal) {
                        errors.rejectValue("schedules[" + i + "].numberStaff", null,
                                "Staff productivity is not fully defined. Please check.");
                    } else {
                        newScheList.get(i).setScheUpTime(String.valueOf(LocalDateTime.now()));
                        // xử lí với các Contrast có ngày bắt đầu sau ngày update
                    }
                } else {
                    valiSchudeNew(newScheList.get(i), errors, newScheList, staffTotal, i);
                }
            }
        }
    }

    /**
     * validato endDate changed
     * 
     * @author HaiLH17
     * @BirthDate: 1994/07/07
     * @param schedule
     * @param errors
     * @param newScheList
     * @param staffTotal
     * @param i
     */
    private void valiEndDateChange(Schedule schedule, Errors errors, List<Schedule> newScheList, int staffTotal,
            int i) {
        int sum = 0;
        for (Schedule sche : newScheList) {
            if (schedule.getStartDate().isBefore(sche.getEndDate())) {
                sum = sum + sche.getNumberStaff();
            }
        }
        if (sum > staffTotal) {
            errors.rejectValue("schedules[" + i + "].numberStaff", null,
                    "Staff productivity defined is more than staff strength of vendor. Please check");
        } else if (sum < staffTotal) {
            errors.rejectValue("schedules[" + i + "].numberStaff", null,
                    "Staff productivity is not fully defined. Please check");
        } else {
            schedule.setScheUpTime(String.valueOf(LocalDateTime.now()));
            // xử lí với các Contrast có ngày bắt đầu sau ngày update
        }
    }

    /**
     * validato startDate changed
     * 
     * @author HaiLH17
     * @BirthDate: 1994/07/07
     * @param sche
     * @param errors
     * @param newScheList
     * @param i
     * @param total
     */
    private void startDateNew(Schedule sche, Errors errors, List<Schedule> newScheList, int i, int total) {
        int sum = 0;
        for (Schedule schedule : newScheList) {
            if (!schedule.getStartDate().isAfter(sche.getEndDate())
                    && !schedule.getEndDate().isBefore(sche.getStartDate())) {
                sum = sum + schedule.getNumberStaff();
            }
        }

        if (sum > total) {
            errors.rejectValue("schedules[" + i + "].numberStaff", null,
                    "Staff productivity defined is more than staff strength of vendor. Please check");
        } else if (sum < total) {
            errors.rejectValue("schedules[" + i + "].numberStaff", null,
                    "Staff productivity is not fully defined. Please check");
        } else {
            sche.setScheUpTime(String.valueOf(LocalDateTime.now()));
            // xử lí với các Contrast có ngày bắt đầu sau ngày update
        }
    }

    /**
     * validator add more schedule
     * 
     * @author HaiLH17
     * @BirthDate: 1994/07/07
     * @param schedule
     * @param errors
     * @param nSche
     * @param staffTotal
     * @param i
     */
    private void valiSchudeNew(Schedule schedule, Errors errors, List<Schedule> nSche, int staffTotal, int i) {
        int sum = 0;
        Schedule flagSche = flag(nSche, schedule);
        LocalDate flagST = flagSche.getStartDate();
        LocalDate flagEnd = flagSche.getEndDate();
        for (int j = 0; j < nSche.size(); j++) {
            if (!(!schedule.getStartDate().isBefore(nSche.get(j).getEndDate())
                    && !schedule.getEndDate().isBefore(nSche.get(j).getEndDate()))
                    && !(!schedule.getStartDate().isAfter(nSche.get(j).getStartDate())
                            && !schedule.getEndDate().isAfter(nSche.get(j).getStartDate()))) {
                sum = sum + nSche.get(j).getNumberStaff();
                if ((nSche.get(j).getStartDate().isAfter(flagEnd) && nSche.get(j).getEndDate().isAfter(flagEnd))
                        || (nSche.get(j).getStartDate().isBefore(flagST)
                                && nSche.get(j).getEndDate().isBefore(flagST))) {
                    sum = sum - nSche.get(j).getNumberStaff();
                    flagEnd = nSche.get(j).getEndDate();
                    flagST = nSche.get(j).getStartDate();
                }
            }
        }
        if (sum > staffTotal) {
            errors.rejectValue("schedules[" + i + "].numberStaff", null,
                    "Staff productivity defined is more than staff strength of vendor. Please check");
        } else if (sum < staffTotal) {
            errors.rejectValue("schedules[" + i + "].numberStaff", null,
                    "Staff productivity is not fully defined. Please check");
        } else {
            schedule.setScheUpTime(String.valueOf(LocalDateTime.now()));
            // xử lí với các Contrast có ngày bắt đầu sau ngày update
        }
    }

    /**
     * lấy hợp đồng có thời gian ngắn nhất trong list
     * @author HaiLH17
     * @BirthDate: 1994/07/07
     * @param sche
     * @param schedule
     * @return
     */
    public Schedule flag(List<Schedule> sche, Schedule schedule) {
        Schedule shortestContract = schedule; // Giả sử hợp đồng đầu tiên là hợp đồng có khoảng thời gian ngắn nhất
        for (Schedule s : sche) {
            if (!(!schedule.getStartDate().isBefore(s.getEndDate()) && !schedule.getEndDate().isBefore(s.getEndDate()))
                    && !(!schedule.getStartDate().isAfter(s.getStartDate())
                            && !schedule.getEndDate().isAfter(s.getStartDate()))) {
                long durationCurrent = s.getEndDate().toEpochDay() - s.getStartDate().toEpochDay();
                long durationShortest = shortestContract.getEndDate().toEpochDay()
                        - shortestContract.getStartDate().toEpochDay();
                if (durationCurrent < durationShortest) {
                    shortestContract = s; // Cập nhật hợp đồng có khoảng thời gian ngắn nhất nếu tìm thấy độ dài ngắn
                                          // hơn
                }
            }
        }
        return shortestContract;
    }

}
