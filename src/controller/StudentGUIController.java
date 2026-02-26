package controller;

import javafx.stage.Stage;
import javafx.stage.FileChooser;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import model.Student;
import manager.StudentManager;
import view.StudentGUIView;
import util.InputValidator;
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
     * Loads sample student data for testing purposes.
     * Attempts to load from sample_data.csv file; falls back to hardcoded data if file not found.
     * This demonstrates the CSV loading functionality in action.
     */
    public void loadSampleData() {
        // Try to load from CSV file first (demonstrates CSV functionality)
        try {
            int count = manager.loadFromCSV("sample_data.csv");
            view.showInfo("Sample Data Loaded", 
                "Sample data loaded from CSV: " + count + " students added.");
            refreshTable();
            return; // Successfully loaded from CSV
        } catch (java.io.FileNotFoundException e) {
            // File not found - fall back to hardcoded data
            view.updateStatus("sample_data.csv not found. Loading default sample data...");
        } catch (java.io.IOException e) {
            // Other I/O error - fall back to hardcoded data
            view.updateStatus("Error reading sample_data.csv. Loading default sample data...");
        }
        
        // Fallback: Add hardcoded sample data if CSV loading failed
        try {
            manager.addStudent(new Student("S001", "John", "Doe", 3.5));
            manager.addStudent(new Student("S002", "Jane", "Smith", 3.8));
            manager.addStudent(new Student("S003", "Alice", "Johnson", 3.2));
            view.showInfo("Sample Data Loaded", "Default sample data loaded: 3 students added.");
            refreshTable();
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

        // Validate first name using InputValidator
        String firstNameError = InputValidator.getNonEmptyErrorMessage(firstName, "First name");
        if (firstNameError != null) {
            view.showError("Validation Error", firstNameError);
            return;
        }

        // Validate last name using InputValidator
        String lastNameError = InputValidator.getNonEmptyErrorMessage(lastName, "Last name");
        if (lastNameError != null) {
            view.showError("Validation Error", lastNameError);
            return;
        }

        // Validate and parse GPA using InputValidator
        Double gpa = InputValidator.parseDoubleInRange(gpaText, InputValidator.MIN_GPA, InputValidator.MAX_GPA);
        if (gpa == null) {
            if (!InputValidator.isNonEmpty(gpaText)) {
                view.showError("Validation Error", "GPA cannot be empty!");
            } else if (InputValidator.parseDouble(gpaText) == null) {
                view.showError("Validation Error", "Please enter a valid number for GPA!");
            } else {
                view.showError("Validation Error", InputValidator.getGPAErrorMessage(0));
            }
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
        
        if (!InputValidator.isNonEmpty(studentId)) {
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
        
        if (!InputValidator.isNonEmpty(lastName)) {
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
        
        if (!InputValidator.isNonEmpty(studentId)) {
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

        // Parse GPA (if provided) using InputValidator
        Double gpa = null;
        if (InputValidator.isNonEmpty(gpaText)) {
            gpa = InputValidator.parseDoubleInRange(gpaText, InputValidator.MIN_GPA, InputValidator.MAX_GPA);
            if (gpa == null) {
                if (InputValidator.parseDouble(gpaText) == null) {
                    view.showError("Validation Error", "Please enter a valid number for GPA!");
                } else {
                    view.showError("Validation Error", InputValidator.getGPAErrorMessage(0));
                }
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
        
        if (!InputValidator.isNonEmpty(studentId)) {
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

    /**
     * Handles saving student data to CSV file
     */
    public void handleSaveToCSV() {
        if (manager.getStudentCount() == 0) {
            view.showWarning("No Data", "No students to save. Add some students first.");
            return;
        }

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Students to CSV");
        fileChooser.setInitialFileName("students.csv");
        fileChooser.getExtensionFilters().add(
            new FileChooser.ExtensionFilter("CSV Files", "*.csv")
        );

        File file = fileChooser.showSaveDialog(primaryStage);
        if (file != null) {
            try {
                manager.saveToCSV(file.getAbsolutePath());
                view.showInfo("Success", 
                    "Successfully saved " + manager.getStudentCount() + " students to:\n" + file.getName());
                view.updateStatus("✓ Saved " + manager.getStudentCount() + " students to " + file.getName());
            } catch (IOException e) {
                view.showError("Save Failed", "Failed to save file: " + e.getMessage());
                view.updateStatus("✗ Failed to save file");
            }
        }
    }

    /**
     * Handles loading student data from CSV file
     */
    public void handleLoadFromCSV() {
        // Warn if data exists
        if (manager.getStudentCount() > 0) {
            boolean confirmed = view.showConfirmation(
                "Confirm Load",
                "Warning: Loading will REPLACE all current students!\n\n" +
                "Current students: " + manager.getStudentCount() + "\n\n" +
                "Do you want to continue?"
            );
            
            if (!confirmed) {
                view.updateStatus("Load cancelled");
                return;
            }
        }

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Load Students from CSV");
        fileChooser.getExtensionFilters().add(
            new FileChooser.ExtensionFilter("CSV Files", "*.csv")
        );

        File file = fileChooser.showOpenDialog(primaryStage);
        if (file != null) {
            try {
                int count = manager.loadFromCSV(file.getAbsolutePath());
                refreshTable();
                view.showInfo("Success", 
                    "Successfully loaded " + count + " students from:\n" + file.getName());
                view.updateStatus("✓ Loaded " + count + " students from " + file.getName());
            } catch (java.io.FileNotFoundException e) {
                view.showError("File Not Found", "File not found: " + file.getName());
                view.updateStatus("✗ File not found");
            } catch (IOException e) {
                view.showError("Load Failed", "Failed to load file: " + e.getMessage());
                view.updateStatus("✗ Failed to load file");
            }
        }
    }
}
