package nl.evanheesen.plantenfluisteraars.model;

import javax.persistence.*;
import java.util.List;
import javax.persistence.OneToOne;

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

    @Column(length = 120, nullable = false, unique = true)
    public String email;

    @Column(length = 15)
    public String phone;

    @OneToOne
    @JoinColumn(name = "file_id")
    private DBFile dbFile;

    @OneToMany(mappedBy = "employee")
    @JoinColumn(name = "employee_id")
    List<Garden> gardens;

    // getters and setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public DBFile getDBFile() {
        return dbFile;
    }

    public void setDBFile(DBFile dbFile) {
        this.dbFile = dbFile;
    }

//    public List<Garden> getGardens() {
//        return gardens;
//    }
//
//    public void setGardens(List<Garden> gardens) {
//        this.gardens = gardens;
//    }
}
