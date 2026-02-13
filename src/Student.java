/**
 * Student class represents a student record with ID, name, and GPA.
 * This class demonstrates Object-Oriented Programming principles with
 * encapsulation (private fields) and public accessor methods.
 */
public class Student {
    private String studentId;
    private String firstName;
    private String lastName;
    private double gpa;

    /**
     * Constructor to create a new Student object
     * @param studentId Unique identifier for the student
     * @param firstName Student's first name
     * @param lastName Student's last name
     * @param gpa Student's grade point average (0.0 - 4.0)
     */
    public Student(String studentId, String firstName, String lastName, double gpa) {
        this.studentId = studentId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gpa = gpa;
    }

    // Getters
    public String getStudentId() {
        return studentId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public double getGpa() {
        return gpa;
    }

    // Setters
    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setGpa(double gpa) {
        this.gpa = gpa;
    }

    /**
     * Returns a formatted string representation of the student
     * @return String containing all student information
     */
    @Override
    public String toString() {
        return String.format("ID: %-10s | Name: %-20s | GPA: %.2f", 
            studentId, 
            firstName + " " + lastName, 
            gpa);
    }

    /**
     * Returns full name of the student
     * @return First name and last name combined
     */
    public String getFullName() {
        return firstName + " " + lastName;
    }
}
