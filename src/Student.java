/**
 * Student class represents a student record with ID, name, and GPA.
 * This class demonstrates Object-Oriented Programming principles with
 * encapsulation (private fields) and public accessor methods.
 * 
 * @author Leena Komenski
 */
import java.util.Comparator;

public class Student implements Comparable<Student> {
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
    // Note: setStudentId() removed to maintain data integrity - student IDs are immutable after construction
    
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

    /** 
     * Compares this student to another based on student ID (case-insensitive).
     * This allows sorting by student ID in ascending order.
     */

    /**
     * Default sort order: Student ID ascending (case-insensitive).
     */
    @Override
    public int compareTo(Student other) {
        return this.getStudentId().compareToIgnoreCase(other.getStudentId());
    }

    /**
     * Sort by last name, then first name, then ID (case-insensitive).
     */
    public static final Comparator<Student> BY_LAST_NAME_THEN_FIRST =
            Comparator.comparing(Student::getLastName, String.CASE_INSENSITIVE_ORDER)
                    .thenComparing(Student::getFirstName, String.CASE_INSENSITIVE_ORDER)
                    .thenComparing(Student::getStudentId, String.CASE_INSENSITIVE_ORDER);

    /**
     * Sort by GPA descending, then last name, then first name.
     */
    public static final Comparator<Student> BY_GPA_DESC =
            Comparator.comparingDouble(Student::getGpa).reversed()
                    .thenComparing(Student::getLastName, String.CASE_INSENSITIVE_ORDER)
                    .thenComparing(Student::getFirstName, String.CASE_INSENSITIVE_ORDER);
}
