import java.util.ArrayList;
import java.util.List;

import exceptions.StudentException;

/**
 * StudentController - Controller Layer (MVC Pattern)
 * 
 * Handles application flow control and coordinates between Model and View:
 * - Processes user menu selections
 * - Calls appropriate Manager methods (Model)
 * - Calls appropriate View methods for display
 * - Manages the main application loop
 * 
 * @author Leena Komenski
 */
public class StudentController {
    private StudentManager manager;
    private StudentView view;

    public StudentController(StudentManager manager, StudentView view) {
        this.manager = manager;
        this.view = view;
    }

    /**
     * Runs the main application loop
     */
    public void run() {
        view.printWelcome();
        addSampleData();

        boolean running = true;
        while (running) {
            running = processMenu();
        }

        view.printGoodbye();
    }

    /**
     * Adds sample student data for testing purposes
     */
    private void addSampleData() {
        try {
            manager.addStudent(new Student("S001", "John", "Doe", 3.5));
            manager.addStudent(new Student("S002", "Jane", "Smith", 3.8));
            manager.addStudent(new Student("S003", "Alice", "Johnson", 3.2));
            view.printMessage("Sample data loaded: 3 students added.\n");
        } catch (StudentException e) {
            view.printError("Error loading sample data: " + e.getMessage());
        }
    }

    /**
     * Displays menu and processes user selection
     * @return false if user wants to quit, true otherwise
     */
    private boolean processMenu() {
        view.printMenu();
        int choice = view.promptForMenuChoice();
        view.printBlankLine();

        switch (choice) {
            case 1:
                addStudent();
                break;
            case 2:
                listAllStudents();
                break;
            case 3:
                searchStudentById();
                break;
            case 4:
                searchStudentByLastName();
                break;
            case 5:
                updateStudent();
                break;
            case 6:
                removeStudent();
                break;
            case 7:
                sortStudents();
                break;
            case 8:
                displayStatistics();
                break;
            case 9:
                return false; // Exit
            default:
                view.printError("Invalid option. Please try again.");
        }

        view.printBlankLine();
        return true;
    }

    /**
     * Handles adding a new student
     */
    private void addStudent() {
        view.printSectionHeader("ADD NEW STUDENT");
        
        String firstName = view.promptForNonEmptyString("Enter First Name: ", "First name");
        String lastName = view.promptForNonEmptyString("Enter Last Name: ", "Last name");
        double gpa = view.promptForDouble("Enter GPA (0.0 - 4.0): ", 0.0, 4.0);
        
        try {
            Student newStudent = manager.addStudentAutoId(firstName, lastName, gpa);
            view.printSuccess("Student added successfully!");
            view.printStudent(newStudent);
        } catch (StudentException e) {
            view.printError("Failed to add student: " + e.getMessage());
        }
    }

    /**
     * Handles listing all students
     */
    private void listAllStudents() {
        List<Student> students = manager.getAllStudents();
        view.printAllStudents(students);
    }

    /**
     * Handles searching for a student by ID
     */
    private void searchStudentById() {
        view.printSectionHeader("SEARCH BY STUDENT ID");
        String studentId = view.promptForString("Enter Student ID: ");
        
        Student student = manager.findStudentById(studentId);
        if (student != null) {
            view.printStudentFound(student);
        } else {
            view.printError("No student found with ID: " + studentId);
        }
    }

    /**
     * Handles searching for students by last name (RECURSIVE method)
     */
    private void searchStudentByLastName() {
        view.printSectionHeader("SEARCH BY LAST NAME (RECURSIVE)");
        String lastName = view.promptForString("Enter Last Name: ");
        
        if (lastName.isEmpty()) {
            view.printError("Last name cannot be empty!");
            return;
        }
        
        // Call the RECURSIVE search method
        ArrayList<Student> results = manager.searchByLastName(lastName);
        view.printSearchResults(results, "last name: " + lastName);
    }

    /**
     * Handles updating a student's information
     */
    private void updateStudent() {
        view.printSectionHeader("UPDATE STUDENT");
        String studentId = view.promptForString("Enter Student ID to update: ");
        
        Student student = manager.findStudentById(studentId);
        if (student == null) {
            view.printError("No student found with ID: " + studentId);
            return;
        }
        
        view.printMessage("\nCurrent Information:");
        view.printStudent(student);
        view.printMessage("\nEnter new information (press Enter to keep current value):");
        
        String firstName = view.promptForString("New First Name [" + student.getFirstName() + "]: ");
        String lastName = view.promptForString("New Last Name [" + student.getLastName() + "]: ");
        Double gpa = view.promptForOptionalDouble("New GPA [" + student.getGpa() + "]: ", 0.0, 4.0);
        
        try {
            manager.updateStudent(studentId, firstName, lastName, gpa);
            view.printSuccess("Student updated successfully!");
            view.printStudent(manager.findStudentById(studentId));
        } catch (StudentException e) {
            view.printError("Failed to update student: " + e.getMessage());
        }
    }

    /**
     * Handles removing a student from the system
     */
    private void removeStudent() {
        view.printSectionHeader("REMOVE STUDENT");
        String studentId = view.promptForString("Enter Student ID to remove: ");
        
        Student student = manager.findStudentById(studentId);
        if (student == null) {
            view.printError("No student found with ID: " + studentId);
            return;
        }
        
        view.printMessage("\nStudent to be removed:");
        view.printStudent(student);
        
        boolean confirmed = view.promptForConfirmation("\nAre you sure you want to remove this student?");
        
        if (confirmed) {
            try {
                manager.removeStudent(studentId, true);
                view.printSuccess("Student removed successfully!");
            } catch (StudentException e) {
                view.printError("Failed to remove student: " + e.getMessage());
            }
        } else {
            view.printMessage("\nRemoval cancelled.");
        }
    }

    /**
     * Handles sorting students
     */
    private void sortStudents() {
        view.printSectionHeader("SORT STUDENTS");
        view.printMessage("1. Sort by Student ID");
        view.printMessage("2. Sort by Last Name");
        view.printMessage("3. Sort by GPA (highest first)");
        
        int choice = view.promptForIntChoice("Enter your choice (1-3): ", 1, 3);
        
        switch (choice) {
            case 1:
                manager.sortByStudentId();
                view.printSuccess("Students sorted by ID!");
                break;
            case 2:
                manager.sortByLastName();
                view.printSuccess("Students sorted by Last Name!");
                break;
            case 3:
                manager.sortByGPA();
                view.printSuccess("Students sorted by GPA!");
                break;
        }
        
        listAllStudents();
    }

    /**
     * Handles displaying statistics
     * Uses RECURSIVE method to count students above GPA threshold
     */
    private void displayStatistics() {
        int totalStudents = manager.getStudentCount();
        
        if (totalStudents == 0) {
            view.printSectionHeader("STATISTICS");
            view.printMessage("No students in the system.");
            return;
        }
        
        double averageGPA = manager.calculateAverageGPA();
        Student highest = manager.findHighestGPA();
        
        // Use RECURSIVE method to count students
        int above35 = manager.countStudentsAboveGPA(3.5);
        int above30 = manager.countStudentsAboveGPA(3.0);
        int above25 = manager.countStudentsAboveGPA(2.5);
        
        view.printStatistics(totalStudents, averageGPA, highest, above35, above30, above25);
    }
}
