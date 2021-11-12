package nl.evanheesen.plantenfluisteraars.dto.request;

import lombok.*;
import nl.evanheesen.plantenfluisteraars.model.DBFile;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

@Getter
@Setter
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeRequest {

    private String username;
    @Size(min=6)
    private String password;
    @Email
    private String email;
    private Set<String> authorities;

    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
    private String street;
    public String houseNumber;
    public String postalCode;
    public String city;
    public String phone;
//    private DBFile dbFile;

}
