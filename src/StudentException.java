public class StudentException extends Exception {
    public StudentException(String message) {
        super(message);
    }
}

class DuplicateStudentIdException extends StudentException {
    public DuplicateStudentIdException(String id) {
        super("A student with ID '" + id + "' already exists.");
    }
}

class InvalidGpaException extends StudentException {
    public InvalidGpaException(double gpa) {
        super("Invalid GPA: " + gpa + ". GPA must be between 0.0 and 4.0.");
    }
}

class StudentNotFoundException extends StudentException {
    public StudentNotFoundException(String id) {
        super("No student found with ID '" + id + "'.");
    }
}