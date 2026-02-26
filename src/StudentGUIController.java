import javafx.stage.Stage;
import java.util.ArrayList;

import exceptions.StudentException;

/**
 * StudentGUIController - GUI Controller Layer (MVC Pattern)
 * 
 * Handles application flow control and coordinates between Model and View:
 * - Processes button actions and user interactions
 * - Validates user input
 * - Calls appropriate Manager methods (Model)
 * - Calls appropriate View methods for display
 * - Manages GUI state and updates
 * 
 * @author Leena Komenski
 */
public class StudentGUIController {
    private StudentManager manager;
    private StudentGUIView view;
    private Stage primaryStage;

    public StudentGUIController(StudentManager manager, StudentGUIView view, Stage primaryStage) {
        this.manager = manager;
        this.view = view;
        this.primaryStage = primaryStage;
    }

    /**
     * Adds sample student data for testing purposes
     */
    public void addSampleData() {
        try {
            manager.addStudent(new Student("S001", "John", "Doe", 3.5));
            manager.addStudent(new Student("S002", "Jane", "Smith", 3.8));
            manager.addStudent(new Student("S003", "Alice", "Johnson", 3.2));
        } catch (StudentException e) {
            view.showError("Error loading sample data", e.getMessage());
        }
    }

    /**
     * Refreshes the table view
     */
    public void refreshTable() {
        view.refreshTable(manager.getAllStudents());
        view.updateStatus("Table refreshed. Total students: " + manager.getStudentCount());
    }

    /**
     * Handles adding a new student
     */
    public void handleAddStudent() {
        String[] input = view.showAddStudentDialog(primaryStage);
        
        if (input == null) {
            return; // Cancelled
        }

        String firstName = input[0];
        String lastName = input[1];
        String gpaText = input[2];

        // Validate first name
        if (firstName.isEmpty()) {
            view.showError("Validation Error", "First name cannot be empty!");
            return;
        }

        // Validate last name
        if (lastName.isEmpty()) {
            view.showError("Validation Error", "Last name cannot be empty!");
            return;
        }

        // Validate and parse GPA
        double gpa;
        try {
            gpa = Double.parseDouble(gpaText);
            if (gpa < 0.0 || gpa > 4.0) {
                view.showError("Validation Error", "GPA must be between 0.0 and 4.0!");
                return;
            }
        } catch (NumberFormatException e) {
            view.showError("Validation Error", "Please enter a valid number for GPA!");
            return;
        }

        // Add student
        try {
            Student newStudent = manager.addStudentAutoId(firstName, lastName, gpa);
            refreshTable();
            view.updateStatus("✓ Student added successfully: " + newStudent.getStudentId());
            view.showInfo("Success", "Student added successfully!\n" + newStudent.toString());
        } catch (StudentException e) {
            view.showError("Error", "Failed to add student: " + e.getMessage());
        }
    }

    /**
     * Handles searching for a student by ID
     */
    public void handleSearchById() {
        String studentId = view.showSearchByIdDialog(primaryStage);
        
        if (studentId == null || studentId.trim().isEmpty()) {
            return; // Cancelled or empty
        }

        Student student = manager.findStudentById(studentId.trim());
        if (student != null) {
            view.showInfo("Student Found", student.toString());
            view.highlightStudent(student);
            view.updateStatus("Student found: " + studentId);
        } else {
            view.showWarning("Not Found", "No student found with ID: " + studentId);
            view.updateStatus("Student not found: " + studentId);
        }
    }

    /**
     * Handles searching for students by last name (RECURSIVE method)
     */
    public void handleSearchByLastName() {
        String lastName = view.showSearchByLastNameDialog(primaryStage);
        
        if (lastName == null || lastName.trim().isEmpty()) {
            if (lastName != null) {
                view.showError("Validation Error", "Last name cannot be empty!");
            }
            return;
        }

        // Call the RECURSIVE search method
        ArrayList<Student> results = manager.searchByLastName(lastName.trim());

        if (results.isEmpty()) {
            view.showWarning("No Results", "No students found with last name: " + lastName);
            view.updateStatus("No results for: " + lastName);
        } else {
            StringBuilder message = new StringBuilder();
            message.append("Found ").append(results.size()).append(" student(s):\n\n");
            for (Student student : results) {
                message.append(student.toString()).append("\n");
            }

            view.showInfo("Search Results (Recursive)", message.toString());
            view.highlightStudents(results);
            view.updateStatus("Found " + results.size() + " student(s) with last name: " + lastName);
        }
    }

    /**
     * Handles updating a student's information
     */
    public void handleUpdateStudent() {
        // Get student ID
        String studentId = view.showGetStudentIdDialog(primaryStage, "Update Student", 
                                                       "Enter Student ID to update");
        
        if (studentId == null || studentId.trim().isEmpty()) {
            return; // Cancelled
        }

        Student student = manager.findStudentById(studentId.trim());
        if (student == null) {
            view.showError("Not Found", "No student found with ID: " + studentId);
            return;
        }

        // Show update dialog
        String[] input = view.showUpdateStudentDialog(primaryStage, student);
        
        if (input == null) {
            return; // Cancelled
        }

        String firstName = input[0];
        String lastName = input[1];
        String gpaText = input[2];

        // Parse GPA (if provided)
        Double gpa = null;
        if (!gpaText.isEmpty()) {
            try {
                gpa = Double.parseDouble(gpaText);
                if (gpa < 0.0 || gpa > 4.0) {
                    view.showError("Validation Error", "GPA must be between 0.0 and 4.0!");
                    return;
                }
            } catch (NumberFormatException e) {
                view.showError("Validation Error", "Please enter a valid number for GPA!");
                return;
            }
        }

        // Update student
        try {
            manager.updateStudent(studentId.trim(), firstName, lastName, gpa);
            refreshTable();
            Student updated = manager.findStudentById(studentId.trim());
            view.updateStatus("✓ Student updated: " + studentId);
            view.showInfo("Success", "Student updated successfully!\n" + updated.toString());
        } catch (StudentException e) {
            view.showError("Error", "Failed to update student: " + e.getMessage());
        }
    }

    /**
     * Handles removing a student
     */
    public void handleRemoveStudent() {
        // Get student ID
        String studentId = view.showGetStudentIdDialog(primaryStage, "Remove Student", 
                                                       "Enter Student ID to remove");
        
        if (studentId == null || studentId.trim().isEmpty()) {
            return; // Cancelled
        }

        Student student = manager.findStudentById(studentId.trim());
        if (student == null) {
            view.showError("Not Found", "No student found with ID: " + studentId);
            return;
        }

        // Show confirmation dialog
        boolean confirmed = view.showRemoveConfirmation(primaryStage, student);
        
        if (confirmed) {
            try {
                manager.removeStudent(studentId.trim(), true);
                refreshTable();
                view.updateStatus("✓ Student removed: " + studentId);
                view.showInfo("Success", "Student removed successfully!");
            } catch (StudentException e) {
                view.showError("Error", "Failed to remove student: " + e.getMessage());
            }
        } else {
            view.updateStatus("Removal cancelled");
        }
    }

    /**
     * Handles sorting students
     */
    public void handleSortStudents() {
        String choice = view.showSortDialog(primaryStage);
        
        if (choice == null) {
            return; // Cancelled
        }

        switch (choice) {
            case "By Student ID":
                manager.sortByStudentId();
                view.updateStatus("✓ Students sorted by ID");
                break;
            case "By Last Name":
                manager.sortByLastName();
                view.updateStatus("✓ Students sorted by Last Name");
                break;
            case "By GPA (Highest First)":
                manager.sortByGPA();
                view.updateStatus("✓ Students sorted by GPA");
                break;
        }
        
        refreshTable();
    }

    /**
     * Handles displaying statistics
     * Uses RECURSIVE method to count students above GPA threshold
     */
    public void handleDisplayStatistics() {
        if (manager.getStudentCount() == 0) {
            view.showWarning("No Data", "No students in the system.");
            return;
        }

        StringBuilder stats = new StringBuilder();
        stats.append("=== STATISTICS ===\n\n");
        stats.append("Total Students: ").append(manager.getStudentCount()).append("\n");
        stats.append("Average GPA: ").append(String.format("%.2f", manager.calculateAverageGPA())).append("\n");

        Student highest = manager.findHighestGPA();
        if (highest != null) {
            stats.append("Highest GPA: ").append(String.format("%.2f", highest.getGpa()));
            stats.append(" (").append(highest.getFullName()).append(")\n");
        }

        // Use RECURSIVE method to count students (preserves recursion requirement)
        stats.append("\n--- GPA Distribution (using Recursive Count) ---\n");
        int above35 = manager.countStudentsAboveGPA(3.5);
        int above30 = manager.countStudentsAboveGPA(3.0);
        int above25 = manager.countStudentsAboveGPA(2.5);

        stats.append("Students with GPA > 3.5: ").append(above35).append("\n");
        stats.append("Students with GPA > 3.0: ").append(above30).append("\n");
        stats.append("Students with GPA > 2.5: ").append(above25).append("\n");

        view.showInfo("Statistics", stats.toString());
        view.updateStatus("Statistics displayed");
    }
}
