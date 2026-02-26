package exceptions;

/**
 * Exception thrown when attempting to add a student with an ID that already exists
 * 
 * This ensures data integrity by preventing duplicate student IDs in the system.
 * 
 * @author Leena Komenski
 */
public class DuplicateStudentIdException extends StudentException {
    public DuplicateStudentIdException(String id) {
        super("A student with ID '" + id + "' already exists.");
    }
}
