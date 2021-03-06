package nl.evanheesen.plantenfluisteraars.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import javax.persistence.OneToOne;

@Getter
@Setter
@Entity
@Table(name = "employees")
public class Employee {

    // attributen
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false, name = "employee_id")
    public long id;

    @Column(length = 80)
    public String firstName;

    @Column(length = 80)
    public String lastName;

    @Column(length = 150)
    public String street;

    @Column(length = 8)
    public String houseNumber;

    @Column(length = 7)
    public String postalCode;

    @Column(length = 120)
    public String city;

    @Column(length = 10)
    public String status;

//    @Column(length = 120, nullable = false, unique = true)
//    public String email;

    @Column(length = 15)
    public String phone;

    @OneToOne
    @JoinColumn(name = "file_id")
    private DBFile dbFile;

    @JsonIgnore
    @OneToMany(mappedBy = "employee")
//    @JoinColumn(name = "employee_id")
    List<Garden> gardens;

}
