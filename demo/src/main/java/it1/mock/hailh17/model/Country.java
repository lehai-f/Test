package it1.mock.hailh17.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Country entity
 * @author HaiLH17
 * @BirthDate: 1994/07/07
 */
@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Country {
    @Id
    @Column(name = "Country_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long countryID;

    @Column(name = "Country_Name", columnDefinition = "Nvarchar(50)")
    @NotBlank(message = "Country Name must be entered")
    private String countryName;

    @OneToMany(mappedBy = "country", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    private Set<Vendor> vendors = new HashSet<Vendor>();

}
