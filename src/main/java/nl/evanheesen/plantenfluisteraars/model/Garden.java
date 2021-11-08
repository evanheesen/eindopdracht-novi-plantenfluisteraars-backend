//package nl.evanheesen.plantenfluisteraars.model;
//
//import javax.persistence.*;
//import java.time.LocalDate;
//
//@Entity
//@Table(name = "gardens")
//public class Garden {
//
//    // attributen
//    @Id
//    @EmbeddedId
//    GardenKey id;
//
//    @ManyToOne
//    @MapsId("customerId")
//    @JoinColumn(name = "customer_id")
//    Customer customer;
//
//    @ManyToOne
//    @MapsId("employeeId")
//    @JoinColumn(name = "employees_id")
//    Employee employee;
//
//    @Column(length = 12)
//    public LocalDate date;
//
//    @Column(length = 150)
//    public String street;
//
//    @Column(length = 8)
//    public String houseNumber;
//
//    @Column(length = 7)
//    public String postalCode;
//
//    @Column(length = 120)
//    public String city;
//
//    // Hier nog aparte klasse van maken met one-to-many relatie?
//    @Column(length = 1)
//    public byte packagePlants;
//
//    // getters and setters
//    public GardenKey getId() {
//        return id;
//    }
//
//    public void setId(GardenKey id) {
//        this.id = id;
//    }
//
//    public Customer getCustomer() {
//        return customer;
//    }
//
//    public void setCustomer(Customer customer) {
//        this.customer = customer;
//    }
//
//    public Employee getEmployee() {
//        return employee;
//    }
//
//    public void setEmployee(Employee employee) {
//        this.employee = employee;
//    }
//
//    public LocalDate getDate() {
//        return date;
//    }
//
//    public void setDate(LocalDate date) {
//        this.date = date;
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
//    public byte getPackagePlants() {
//        return packagePlants;
//    }
//
//    public void setPackagePlants(byte packagePlants) {
//        this.packagePlants = packagePlants;
//    }
//}
