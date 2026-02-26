import java.util.ArrayList;
import java.util.Collections;

import exceptions.StudentException;
import exceptions.DuplicateStudentIdException;
import exceptions.InvalidGpaException;
import exceptions.StudentNotFoundException;

/**
 * StudentManager class manages a collection of Student objects.
 * This class handles all CRUD operations (Create, Read, Update, Delete)
 * and provides additional features like searching, sorting, and statistics.
 * Demonstrates use of ArrayList data structure and recursive algorithms.
 * 
 * @author Leena Komenski
 */

public class StudentManager {
    private ArrayList<Student> students;

    /**
     * Constructor initializes the ArrayList to store students
     */
    public StudentManager() {
        this.students = new ArrayList<>();
    }

    /**
     * Generates the next unique student ID in the format "S001", "S002", etc.
     * @return The next student ID
     */
    public String generateNextStudentId() {
        int nextNum = getMaxIdNumber() + 1;
        return String.format("S%03d", nextNum);
    }

    // Helper method to find the maximum numeric part of existing student IDs
    private int getMaxIdNumber() {
        int max = 0;
        for (Student s : students) {
            int n = parseIdNumberSafe(s.getStudentId());
            if (n > max) max = n;
        }
        return max;
    }

    // Safely parses the numeric part of the student ID, returns 0 if format is invalid
    private int parseIdNumberSafe(String id) {
        if (id == null) return 0;
        String cleaned = id.trim().toUpperCase();
        if (!cleaned.matches("^S\\d{3}$")) return 0;
        return Integer.parseInt(cleaned.substring(1));
    }

    /**
     * Adds a new student with an automatically generated ID.
     * @param firstname The first name of the student
     * @param lastname The last name of the student
     * @param gpa The GPA of the student
     * @return The newly added Student object
     * @throws StudentException if validation fails
     */
    public Student addStudentAutoId(String firstname, String lastname, double gpa) throws StudentException {
        String newId = generateNextStudentId();
        Student student = new Student(newId, firstname, lastname, gpa);
        addStudent(student);
        return student;
    }

    /**
     * Adds a new student to the list with validation and error handling.
     * @param student The Student object to add
     * @throws StudentException if validation fails
     */
    public void addStudent(Student student) throws StudentException {
        if (student == null) {
            throw new StudentException("Student cannot be null.");
        }
        if (student.getGpa() < 0.0 || student.getGpa() > 4.0) {
            throw new InvalidGpaException(student.getGpa());
        }
        if (findStudentById(student.getStudentId()) != null) {
            throw new DuplicateStudentIdException(student.getStudentId());
        }
        students.add(student);
    }

    /**
     * Lists all students in the system
     * Returns an unmodifiable view to protect internal list from external modification
     * @return Unmodifiable List of all students
     */
    public java.util.List<Student> getAllStudents() {
        return Collections.unmodifiableList(students);
    }

    /**
     * Displays all students to the console
     */
    public void listAllStudents() {
        if (students.isEmpty()) {
            System.out.println("No students in the system.");
            return;
        }
        
        System.out.println("\n" + "=".repeat(60));
        System.out.println("ALL STUDENTS");
        System.out.println("=".repeat(60));
        for (Student student : students) {
            System.out.println(student);
        }
        System.out.println("=".repeat(60));
        System.out.println("Total students: " + students.size());
    }

    /**
     * Finds a student by their unique ID
     * @param studentId The ID to search for
     * @return Student object if found, null otherwise
     */
    public Student findStudentById(String studentId) {
        if (studentId == null) return null;
        for (Student student : students) {
            if (student.getStudentId().equalsIgnoreCase(studentId.trim())) {
                return student;
            }
        }
        return null;
    }

    /**
     * RECURSIVE METHOD: Searches for students by last name recursively
     * This demonstrates recursion by traversing the list recursively.
     * Base case: index reaches the end of the list
     * Recursive case: check current student and move to next index
     * 
     * @param lastName The last name to search for
     * @param index Current position in the list (start with 0)
     * @param results ArrayList to accumulate matching students
     * @return ArrayList of students with matching last name
     */
    public ArrayList<Student> searchByLastNameRecursive(String lastName, int index, ArrayList<Student> results) {
        // Base case: reached end of list
        if (index >= students.size()) {
            return results;
        }
        
        // Check if current student's last name matches (case-insensitive)
        if (students.get(index).getLastName().equalsIgnoreCase(lastName)) {
            results.add(students.get(index));
        }
        
        // Recursive case: move to next student
        return searchByLastNameRecursive(lastName, index + 1, results);
    }

    /**
     * Wrapper method for recursive last name search
     * @param lastName The last name to search for
     * @return ArrayList of students with matching last name
     */
    public ArrayList<Student> searchByLastName(String lastName) {
        return searchByLastNameRecursive(lastName, 0, new ArrayList<>());
    }

    /**
     * Updates a student's information.
     * @param studentId ID of the student to update
     * @param firstName New first name (blank to keep unchanged)
     * @param lastName New last name (blank to keep unchanged)
     * @param gpa New GPA (null to keep unchanged)
     * @throws StudentException if student not found or GPA invalid
     */
    public void updateStudent(String studentId, String firstName, String lastName, Double gpa) throws StudentException {
        Student student = findStudentById(studentId);
        if (student == null) {
            throw new StudentNotFoundException(studentId);
        }

        if (firstName != null && !firstName.trim().isEmpty()) {
            student.setFirstName(firstName);
        }
        if (lastName != null && !lastName.trim().isEmpty()) {
            student.setLastName(lastName);
        }
        if (gpa != null) {
            if (gpa < 0.0 || gpa > 4.0) {
                throw new InvalidGpaException(gpa);
            }
            student.setGpa(gpa);
        }
    }


    /**
     * Removes a student from the system with confirmation.
     * @param studentId ID of the student to remove
     * @param confirmed Must be true to confirm deletion (prevents accidental deletions)
     * @throws StudentException if student not found or confirmation not provided
     */
    public void removeStudent(String studentId, boolean confirmed) throws StudentException {
        if (!confirmed) {
            throw new StudentException("Deletion must be confirmed. Set confirmed=true to proceed.");
        }
        
        Student student = findStudentById(studentId);
        if (student == null) {
            throw new StudentNotFoundException(studentId);
        }
        students.remove(student);
    }

    /**
     * Sorts students by student ID
     */
    public void sortByStudentId() {
        Collections.sort(students);
    }

    /**
     * Sorts students by last name
     */
    public void sortByLastName() {
        students.sort(Student.BY_LAST_NAME_THEN_FIRST);
    }

    /**
     * Sorts students by GPA in descending order (highest first)
     */
    public void sortByGPA() {
        students.sort(Student.BY_GPA_DESC);
    }

    /**
     * Calculates the average GPA of all students
     * @return Average GPA, or 0.0 if no students
     */
    public double calculateAverageGPA() {
        if (students.isEmpty()) {
            return 0.0;
        }
        
        double sum = 0.0;
        for (Student student : students) {
            sum += student.getGpa();
        }
        return sum / students.size();
    }

    /**
     * Finds the student with the highest GPA
     * @return Student with highest GPA, or null if no students
     */
    public Student findHighestGPA() {
        if (students.isEmpty()) {
            return null;
        }
        
        Student highest = students.get(0);
        for (Student student : students) {
            if (student.getGpa() > highest.getGpa()) {
                highest = student;
            }
        }
        return highest;
    }

    /**
     * RECURSIVE METHOD: Counts students with GPA above a threshold recursively
     * This demonstrates recursion for computation/counting purposes.
     * Base case: index reaches the end of the list
     * Recursive case: increment count if student meets criteria, continue recursion
     * 
     * @param threshold The GPA threshold to compare against
     * @param index Current position in the list (start with 0)
     * @return Count of students with GPA above threshold
     */
    public int countStudentsAboveGPARecursive(double threshold, int index) {
        // Base case: reached end of list
        if (index >= students.size()) {
            return 0;
        }
        
        // Check if current student's GPA is above threshold
        int currentCount = (students.get(index).getGpa() > threshold) ? 1 : 0;
        
        // Recursive case: add current count to count from remaining students
        return currentCount + countStudentsAboveGPARecursive(threshold, index + 1);
    }

    /**
     * Wrapper method for recursive GPA count
     * @param threshold The GPA threshold
     * @return Count of students with GPA above threshold
     */
    public int countStudentsAboveGPA(double threshold) {
        return countStudentsAboveGPARecursive(threshold, 0);
    }

    /**
     * Returns the number of students in the system
     * @return Total count of students
     */
    public int getStudentCount() {
        return students.size();
    }
}
