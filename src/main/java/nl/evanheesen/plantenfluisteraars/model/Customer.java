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
    @Column(updatable = false, nullable = false)
    public long id;

    //    Dit toegevoegd:
    @NotNull
    private LocalDate submissionDate;

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

    @Column(length = 120, unique = true)
    public String email;

    @Column(length = 15)
    public String phone;
//
//    @OneToMany
//    @JoinColumn(name = "customer_id")
//    List<Garden> gardens;

//    // getters and setters
//    public long getId() {
//        return id;
//    }
//
//    public void setId(long id) {
//        this.id = id;
//    }
//
////    Dit toegevoegd:
//    public LocalDateTime getCreatedAt() {
//        return createdAt;
//    }
//
//    public void setCreatedAt(LocalDateTime createdAt) {
//        this.createdAt = createdAt;
//    }
//
//    public String getFirstName() {
//        return firstName;
//    }
//
//    public void setFirstName(String firstName) {
//        this.firstName = firstName;
//    }
//
//    public String getLastName() {
//        return lastName;
//    }
//
//    public void setLastName(String lastName) {
//        this.lastName = lastName;
//    }
//
//    public String getStreet() {
//        return street;
//    }
//
//    public void setStreet(String street) {
//        this.street = street;
//    }
//
//    public String getHouseNumber() {
//        return houseNumber;
//    }
//
//    public void setHouseNumber(String houseNumber) {
//        this.houseNumber = houseNumber;
//    }
//
//    public String getPostalCode() {
//        return postalCode;
//    }
//
//    public void setPostalCode(String postalCode) {
//        this.postalCode = postalCode;
//    }
//
//    public String getCity() {
//        return city;
//    }
//
//    public void setCity(String city) {
//        this.city = city;
//    }
//
//    public String getEmail() {
//        return email;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }
//
//    public String getPhone() {
//        return phone;
//    }
//
//    public void setPhone(String phone) {
//        this.phone = phone;
//    }
//
//    public List<Garden> getGardens() {
//        return gardens;
//    }
//
//    public void setGardens(List<Garden> gardens) {
//        this.gardens = gardens;
//    }
}
