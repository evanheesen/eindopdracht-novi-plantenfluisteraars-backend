package nl.evanheesen.plantenfluisteraars.dto.response;

import lombok.Getter;
import lombok.Setter;
import nl.evanheesen.plantenfluisteraars.model.Employee;

import java.time.LocalDate;

@Getter
@Setter
public class GetGardensResponse {


//    #### Nu te doen: kijken hoe je deze DTO omzet en meegeeft in GET request


    private LocalDate submissionDate;
    private String status;
    private String street;
    private String houseNumber;
    private String postalCode;
    private String city;
    private byte packagePlants;
    private Employee employee;

    public GetGardensResponse(LocalDate submissionDate, String status, String street, String houseNumber, String postalCode, String city, Employee employee) {
        this.submissionDate = submissionDate;
        this.status = status;
        this.street = street;
        this.houseNumber = houseNumber;
        this.postalCode = postalCode;
        this.city = city;
        this.employee = employee;
    }

}
