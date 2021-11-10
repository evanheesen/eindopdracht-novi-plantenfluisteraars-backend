package nl.evanheesen.plantenfluisteraars.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "customers")
public class Customer {

    // attributen
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false, name = "customer_id")
    public long id;

    //    Dit toegevoegd:
    @NotNull
    private LocalDate submissionDate;

    @Column(nullable = false)
    private String status = "Open";

    @Column(length = 80, nullable = false)
    public String firstName;

    @Column(length = 80, nullable = false)
    public String lastName;

    @Column(length = 150)
    public String street;

    @Column(length = 8)
    public String houseNumber;

    @Column(length = 7)
    public String postalCode;

    @Column(length = 120)
    public String city;

//    @Column(length = 120, nullable = false, unique = true)
//    public String email;

    @Column(length = 15)
    public String phone;
//
//    @OneToOne
//    @JoinColumn(name = "user_id", referencedColumnName = "id")
//    private User user;
//
    @OneToOne
    @JoinColumn(name = "garden_id", referencedColumnName = "garden_id")
    private Garden garden;

}
