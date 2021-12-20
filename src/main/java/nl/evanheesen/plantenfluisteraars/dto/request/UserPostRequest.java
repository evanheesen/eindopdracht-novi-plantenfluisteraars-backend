package nl.evanheesen.plantenfluisteraars.dto.request;

import lombok.*;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserPostRequest {

    private String username;
    private String password;
    private String email;
    private Set<String> authorities;
    private Boolean isAdmin;

}
