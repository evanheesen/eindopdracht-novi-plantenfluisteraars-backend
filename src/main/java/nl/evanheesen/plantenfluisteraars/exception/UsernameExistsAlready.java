package nl.evanheesen.plantenfluisteraars.exception;

import java.io.Serial;

public class UsernameExistsAlready extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    public UsernameExistsAlready(String username) {
        super("Username " + username + " bestaat al.");
    }

}
