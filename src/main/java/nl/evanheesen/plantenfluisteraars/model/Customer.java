package nl.evanheesen.plantenfluisteraars.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false, name = "customer_id")
    public long id;

    @Column(length = 80, nullable = false)
    public String firstName;

    @Column(length = 80, nullable = false)
    public String lastName;

    @Column(length = 15)
    public String phone;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "customer")
    private Garden garden;

}
