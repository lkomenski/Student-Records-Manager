package exceptions;

/**
 * Base exception class for all student-related exceptions
 * 
 * This serves as the parent class for more specific student exceptions,
 * allowing for polymorphic exception handling.
 * 
 * @author Leena Komenski
 */
public class StudentException extends Exception {
    public StudentException(String message) {
        super(message);
    }
}
