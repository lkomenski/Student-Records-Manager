package exceptions;

/**
 * Exception thrown when a requested student cannot be found
 * 
 * This occurs when attempting to access, update, or delete a student
 * that doesn't exist in the system.
 * 
 * @author Leena Komenski
 */
public class StudentNotFoundException extends StudentException {
    public StudentNotFoundException(String id) {
        super("No student found with ID '" + id + "'.");
    }
}
