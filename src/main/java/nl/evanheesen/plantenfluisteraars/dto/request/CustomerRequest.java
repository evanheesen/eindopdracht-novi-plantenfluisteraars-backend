package nl.evanheesen.plantenfluisteraars.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerRequest {

    private String username;
    @Size(min=6)
    private String password;
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

    @JsonIgnore
    private final LocalDate submissionDate = LocalDate.now();

//private String street;
//private String houseNumber;
//private String postalCode;
//private String phone;

}
