package nl.evanheesen.plantenfluisteraars.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter

// Deze toegevoegd op basis van UserRequest. Is dit nodig?
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerRequest {

    private String username;
    private String password;
    private String email;
    private Set<String> authorities;

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    @JsonIgnore
    private final LocalDateTime submissionDate = LocalDateTime.now();

//private String street;
//private String houseNumber;
//private String postalCode;
//private String phone;

}
