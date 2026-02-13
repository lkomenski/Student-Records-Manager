import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * StudentManager class manages a collection of Student objects.
 * This class handles all CRUD operations (Create, Read, Update, Delete)
 * and provides additional features like searching, sorting, and statistics.
 * Demonstrates use of ArrayList data structure and recursive algorithms.
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
     * Adds a new student to the list
     * Validates that the student ID is unique before adding
     * @param student The Student object to add
     * @return true if added successfully, false if ID already exists
     */
    public boolean addStudent(Student student) {
        // Check for duplicate ID
        if (findStudentById(student.getStudentId()) != null) {
            return false; // Duplicate ID found
        }
        students.add(student);
        return true;
    }

    /**
     * Lists all students in the system
     * @return ArrayList of all students
     */
    public ArrayList<Student> getAllStudents() {
        return students;
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
        for (Student student : students) {
            if (student.getStudentId().equalsIgnoreCase(studentId)) {
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
     * Updates a student's information
     * @param studentId ID of the student to update
     * @param firstName New first name (or null to keep unchanged)
     * @param lastName New last name (or null to keep unchanged)
     * @param gpa New GPA (or -1 to keep unchanged)
     * @return true if updated successfully, false if student not found
     */
    public boolean updateStudent(String studentId, String firstName, String lastName, Double gpa) {
        Student student = findStudentById(studentId);
        if (student == null) {
            return false;
        }
        
        if (firstName != null && !firstName.trim().isEmpty()) {
            student.setFirstName(firstName);
        }
        if (lastName != null && !lastName.trim().isEmpty()) {
            student.setLastName(lastName);
        }
        if (gpa != null && gpa >= 0) {
            student.setGpa(gpa);
        }
        
        return true;
    }

    /**
     * Removes a student from the system
     * @param studentId ID of the student to remove
     * @return true if removed successfully, false if student not found
     */
    public boolean removeStudent(String studentId) {
        Student student = findStudentById(studentId);
        if (student == null) {
            return false;
        }
        students.remove(student);
        return true;
    }

    /**
     * Sorts students by student ID
     */
    public void sortByStudentId() {
        Collections.sort(students, new Comparator<Student>() {
            @Override
            public int compare(Student s1, Student s2) {
                return s1.getStudentId().compareTo(s2.getStudentId());
            }
        });
    }

    /**
     * Sorts students by last name
     */
    public void sortByLastName() {
        Collections.sort(students, new Comparator<Student>() {
            @Override
            public int compare(Student s1, Student s2) {
                int lastNameComparison = s1.getLastName().compareTo(s2.getLastName());
                if (lastNameComparison != 0) {
                    return lastNameComparison;
                }
                return s1.getFirstName().compareTo(s2.getFirstName());
            }
        });
    }

    /**
     * Sorts students by GPA in descending order (highest first)
     */
    public void sortByGPA() {
        Collections.sort(students, new Comparator<Student>() {
            @Override
            public int compare(Student s1, Student s2) {
                return Double.compare(s2.getGpa(), s1.getGpa()); // Descending order
            }
        });
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
