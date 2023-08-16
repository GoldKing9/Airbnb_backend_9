package project.airbnb_backend_9.exception;

public class ReservationDateDuplicateException extends RuntimeException{
    public ReservationDateDuplicateException() {
        super();
    }

    public ReservationDateDuplicateException(String message) {
        super(message);
    }

    public ReservationDateDuplicateException(String message, Throwable cause) {
        super(message, cause);
    }
    public ReservationDateDuplicateException(Throwable cause) {
        super(cause);
    }


}
