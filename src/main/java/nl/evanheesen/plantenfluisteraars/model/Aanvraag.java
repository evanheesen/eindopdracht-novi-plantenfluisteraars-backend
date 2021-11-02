package nl.evanheesen.plantenfluisteraars.model;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name =  "aanvragen")
public class Aanvraag {

    // attributen
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long id;

    @ManyToOne
    @MapsId("bewonerId")
    @JoinColumn(name = "bewoner_id")
    Bewoner bewoner;

    @ManyToOne
    @MapsId("plantenfluisteraarId")
    @JoinColumn(name = "plantenfluisteraar_id")
    Plantenfluisteraar plantenFluisteraar;

    @Column(length = 12)
    public LocalDate date;

    @Column(length = 150)
    public String street;

    @Column(length = 8)
    public String houseNumber;

    @Column(length = 7)
    public String postalCode;

    @Column(length = 120)
    public String city;

    @Column(length = 1)
    public byte packagePlants;

//    public long customer_id;
//    public long plantWhisperer_Id;

    // getters and setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
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

    public byte getPackagePlants() {
        return packagePlants;
    }

    public void setPackagePlants(byte packagePlants) {
        this.packagePlants = packagePlants;
    }
}
