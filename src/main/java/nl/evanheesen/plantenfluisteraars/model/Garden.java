package nl.evanheesen.plantenfluisteraars.model;

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
    @OneToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
//    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "employee_id")
    private Employee employee;

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

    @Column(length = 80)
    public String packagePlants;

}
