package exceptions;

/**
 * Exception thrown when an invalid GPA value is provided
 * 
 * GPA must be within the range of 0.0 to 4.0.
 * This exception ensures data validation at the business logic level.
 * 
 * @author Leena Komenski
 */
public class InvalidGpaException extends StudentException {
    public InvalidGpaException(double gpa) {
        super("Invalid GPA: " + gpa + ". GPA must be between 0.0 and 4.0.");
    }
}
