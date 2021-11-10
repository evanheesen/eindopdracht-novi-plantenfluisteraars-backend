package nl.evanheesen.plantenfluisteraars.exception;

public class InvalidUsernameException extends RuntimeException {

    public InvalidUsernameException(String message) {
        super(message);
    }
    public InvalidUsernameException() {
        super("Gebruikersnaam bestaat al.");
    }
}
