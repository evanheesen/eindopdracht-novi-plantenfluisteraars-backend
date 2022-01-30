package nl.evanheesen.plantenfluisteraars.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsernameResponse {

    private final String username;

    public UsernameResponse(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

}
