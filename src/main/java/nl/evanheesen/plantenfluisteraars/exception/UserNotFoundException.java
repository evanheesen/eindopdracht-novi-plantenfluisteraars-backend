package nl.evanheesen.plantenfluisteraars.exception;

import java.io.Serial;

public class UserNotFoundException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    public UserNotFoundException(String username) {
        super("Kan gebruiker " + username + " niet vinden.");
    }
    public UserNotFoundException() {
        super("Kan de gebruiker niet vinden.");
    }

}
