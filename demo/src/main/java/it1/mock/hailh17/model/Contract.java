package it1.mock.hailh17.model;

import java.time.LocalDate;

import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Contract entity
 * @author HaiLH17
 * @BirthDate: 1994/07/07
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Contract {

    private String contractID;

    private LocalDate actualStartDate;

    private LocalDate actualEndDate;

    @OneToOne
    private Schedule schedule;
}
