package it1.mock.hailh17.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Vendor {
	@Id
	@Column(name = "Vendor_ID")
	private String vendorID;

	@Column(name = "Vendor_Name",columnDefinition = "Nvarchar(50)",unique = true)
	@NotBlank(message = "Vendor Name must be entered")
	private String vendorName;
	
	@Column(name = "Staff_Strength")
	@Max(value = 999999999)
	@Min(value = 1)
	@NotNull(message = "Number of Staff must be entered")
	private int staffStrength;
	
	@Column(name = "Number_WorkingDays")
	@Max(value = 7, message = "Number of Working Days must be a number >0 and <= 7")
	@Min(value = 1, message = "Number of Working Days must be a number >0 and <= 7")
	@NotNull(message = "Number of Working Days must be entered")
	private int numberOfWorkingDays;

	@OneToMany(mappedBy = "vendor",cascade = CascadeType.REMOVE,fetch = FetchType.EAGER)
	private List<Schedule> schedules = new ArrayList<Schedule>();
	
	@ManyToOne
	@JoinColumn(name = "Country_ID",referencedColumnName = "Country_ID")
	private Country country;
	
	private String vdUpTime = String.valueOf(LocalDateTime.now());

	@Override
	public String toString() {
		return "Vendor [vendorID=" + vendorID + ", vendorName=" + vendorName + ", staffStrength=" + staffStrength
				+ ", numberOfWorkingDays=" + numberOfWorkingDays + ", vdUpTime=" + vdUpTime + "]";
	}
		
	
	
}
