package it1.mock.hailh17.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Schedule entity
 * @author HaiLH17
 * @BirthDate: 1994/07/07
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Schedule_ID")
    private long scheduleID;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    @NotNull(message = "Number of Staff must be entered")
    private int numberStaff;

    @NotNull(message = "Record per day must be entered")
    private int recordPerDay;

    private String contractID;

    private String scheUpTime = String.valueOf(LocalDateTime.now());

    @ManyToOne
    @JoinColumn(name = "Vendor_ID", referencedColumnName = "Vendor_ID")
    private Vendor vendor;

    @Override
    public int hashCode() {
        return Objects.hash(endDate, numberStaff, recordPerDay, scheduleID, startDate);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Schedule other = (Schedule) obj;
        return Objects.equals(endDate, other.endDate) && numberStaff == other.numberStaff
                && recordPerDay == other.recordPerDay && scheduleID == other.scheduleID
                && Objects.equals(startDate, other.startDate);
    }

    @Override
    public String toString() {
        return "Schedule [scheduleID=" + scheduleID + ", startDate=" + startDate + ", endDate=" + endDate
                + ", numberStaff=" + numberStaff + ", recordPerDay=" + recordPerDay + ", scheUpTime=" + scheUpTime
                + "]";
    }

}
