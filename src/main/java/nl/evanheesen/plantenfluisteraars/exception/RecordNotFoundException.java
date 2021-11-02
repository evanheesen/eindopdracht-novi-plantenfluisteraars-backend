package nl.evanheesen.plantenfluisteraars.exception;

public class RecordNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

//    meegeven message aan exception:
    public RecordNotFoundException(String message) {
        super(message);
    }

    //    Exception zonder meegegeven message:
    public RecordNotFoundException() {
        super("Record not found");
    }
}
