package nl.evanheesen.plantenfluisteraars.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "gardens")
public class Garden {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, unique = true, name = "garden_id")
    public long id;

    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "customer_id")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    Employee employee;

//    @ManyToOne
////    @MapsId("employeeId")
//    @JoinColumn(name = "employee_id", insertable = false, updatable = false)
//    Employee employee;

    @NotNull
    private LocalDate submissionDate;

    @Column
    private String status = "Open";

    @Column(length = 150)
    public String street;

    @Column(length = 8)
    public String houseNumber;

    @Column(length = 7)
    public String postalCode;

    @Column(length = 120)
    public String city;

    // Hier nog aparte klasse van maken met one-to-many relatie?
    @Column(length = 1)
    public byte packagePlants;

}
