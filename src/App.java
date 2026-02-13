import java.util.ArrayList;
import java.util.Scanner;

/**
 * Student Records Manager - Main Application
 * 
 * This program demonstrates core programming concepts:
 * - Problem-solving and algorithm development
 * - Object-Oriented Programming (OOP) with classes and objects
 * - Data structures (ArrayList)
 * - Recursion (search and count methods)
 * - Input validation and error handling
 * - Menu-driven console interface
 * 
 * @author Student Project
 * @version 1.0
 */
public class App {
    private static StudentManager manager = new StudentManager();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("=".repeat(60));
        System.out.println("     STUDENT RECORDS MANAGER");
        System.out.println("=".repeat(60));
        System.out.println("Welcome! This system helps you manage student records.\n");

        // Add some sample data for testing
        addSampleData();

        // Main menu loop
        boolean running = true;
        while (running) {
            running = displayMenuAndProcess();
        }

        System.out.println("\nThank you for using Student Records Manager!");
        System.out.println("Goodbye!");
        scanner.close();
    }

    /**
     * Adds sample student data for testing purposes
     */
    private static void addSampleData() {
        manager.addStudent(new Student("S001", "John", "Doe", 3.5));
        manager.addStudent(new Student("S002", "Jane", "Smith", 3.8));
        manager.addStudent(new Student("S003", "Alice", "Johnson", 3.2));
        System.out.println("Sample data loaded: 3 students added.\n");
    }

    /**
     * Displays the main menu and processes user selection
     * @return false if user wants to quit, true otherwise
     */
    private static boolean displayMenuAndProcess() {
        displayMenu();
        int choice = getValidMenuChoice();
        System.out.println();

        switch (choice) {
            case 1:
                addStudent();
                break;
            case 2:
                manager.listAllStudents();
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
                System.out.println("Invalid option. Please try again.");
        }

        System.out.println(); // Blank line for readability
        return true;
    }

    /**
     * Displays the main menu options
     */
    private static void displayMenu() {
        System.out.println("\n" + "-".repeat(60));
        System.out.println("MAIN MENU");
        System.out.println("-".repeat(60));
        System.out.println("1. Add a new student");
        System.out.println("2. List all students");
        System.out.println("3. Search student by ID");
        System.out.println("4. Search students by Last Name (Recursive)");
        System.out.println("5. Update student information");
        System.out.println("6. Remove a student");
        System.out.println("7. Sort students");
        System.out.println("8. View statistics");
        System.out.println("9. Quit");
        System.out.println("-".repeat(60));
    }

    /**
     * Gets a valid menu choice from the user with input validation
     * Uses recursion to re-prompt until valid input is received
     * @return Valid menu choice (1-9)
     */
    private static int getValidMenuChoice() {
        return getValidMenuChoiceRecursive();
    }

    /**
     * RECURSIVE METHOD: Validates menu input recursively
     * This demonstrates recursion for input validation.
     * Base case: user enters a valid integer between 1-9
     * Recursive case: invalid input, re-prompt the user
     * 
     * @return Valid menu choice (1-9)
     */
    private static int getValidMenuChoiceRecursive() {
        System.out.print("Enter your choice (1-9): ");
        
        if (scanner.hasNextInt()) {
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            
            // Base case: valid choice
            if (choice >= 1 && choice <= 9) {
                return choice;
            }
        } else {
            scanner.nextLine(); // Clear invalid input
        }
        
        // Recursive case: invalid input, try again
        System.out.println("Invalid input! Please enter a number between 1 and 9.");
        return getValidMenuChoiceRecursive();
    }

    /**
     * Adds a new student to the system with input validation
     */
    private static void addStudent() {
        System.out.println("=== ADD NEW STUDENT ===");
        
        // Get and validate Student ID
        String studentId;
        while (true) {
            System.out.print("Enter Student ID: ");
            studentId = scanner.nextLine().trim();
            
            if (studentId.isEmpty()) {
                System.out.println("Error: Student ID cannot be empty!");
                continue;
            }
            
            if (manager.findStudentById(studentId) != null) {
                System.out.println("Error: Student ID already exists! Please use a different ID.");
                continue;
            }
            
            break;
        }
        
        // Get and validate First Name
        String firstName;
        while (true) {
            System.out.print("Enter First Name: ");
            firstName = scanner.nextLine().trim();
            
            if (firstName.isEmpty()) {
                System.out.println("Error: First name cannot be empty!");
                continue;
            }
            
            break;
        }
        
        // Get and validate Last Name
        String lastName;
        while (true) {
            System.out.print("Enter Last Name: ");
            lastName = scanner.nextLine().trim();
            
            if (lastName.isEmpty()) {
                System.out.println("Error: Last name cannot be empty!");
                continue;
            }
            
            break;
        }
        
        // Get and validate GPA
        double gpa;
        while (true) {
            System.out.print("Enter GPA (0.0 - 4.0): ");
            
            if (scanner.hasNextDouble()) {
                gpa = scanner.nextDouble();
                scanner.nextLine(); // Consume newline
                
                if (gpa >= 0.0 && gpa <= 4.0) {
                    break;
                } else {
                    System.out.println("Error: GPA must be between 0.0 and 4.0!");
                }
            } else {
                scanner.nextLine(); // Clear invalid input
                System.out.println("Error: Please enter a valid number!");
            }
        }
        
        // Create and add the student
        Student newStudent = new Student(studentId, firstName, lastName, gpa);
        if (manager.addStudent(newStudent)) {
            System.out.println("\n✓ Student added successfully!");
            System.out.println(newStudent);
        } else {
            System.out.println("\n✗ Failed to add student!");
        }
    }

    /**
     * Searches for a student by their ID
     */
    private static void searchStudentById() {
        System.out.println("=== SEARCH BY STUDENT ID ===");
        System.out.print("Enter Student ID: ");
        String studentId = scanner.nextLine().trim();
        
        Student student = manager.findStudentById(studentId);
        if (student != null) {
            System.out.println("\nStudent Found:");
            System.out.println("-".repeat(60));
            System.out.println(student);
            System.out.println("-".repeat(60));
        } else {
            System.out.println("\n✗ No student found with ID: " + studentId);
        }
    }

    /**
     * Searches for students by last name using RECURSIVE method
     */
    private static void searchStudentByLastName() {
        System.out.println("=== SEARCH BY LAST NAME (RECURSIVE) ===");
        System.out.print("Enter Last Name: ");
        String lastName = scanner.nextLine().trim();
        
        if (lastName.isEmpty()) {
            System.out.println("Error: Last name cannot be empty!");
            return;
        }
        
        // Call the RECURSIVE search method
        ArrayList<Student> results = manager.searchByLastName(lastName);
        
        if (results.isEmpty()) {
            System.out.println("\n✗ No students found with last name: " + lastName);
        } else {
            System.out.println("\nStudents Found: " + results.size());
            System.out.println("-".repeat(60));
            for (Student student : results) {
                System.out.println(student);
            }
            System.out.println("-".repeat(60));
        }
    }

    /**
     * Updates an existing student's information
     */
    private static void updateStudent() {
        System.out.println("=== UPDATE STUDENT ===");
        System.out.print("Enter Student ID to update: ");
        String studentId = scanner.nextLine().trim();
        
        Student student = manager.findStudentById(studentId);
        if (student == null) {
            System.out.println("\n✗ No student found with ID: " + studentId);
            return;
        }
        
        System.out.println("\nCurrent Information:");
        System.out.println(student);
        System.out.println("\nEnter new information (press Enter to keep current value):");
        
        // Update First Name
        System.out.print("New First Name [" + student.getFirstName() + "]: ");
        String firstName = scanner.nextLine().trim();
        
        // Update Last Name
        System.out.print("New Last Name [" + student.getLastName() + "]: ");
        String lastName = scanner.nextLine().trim();
        
        // Update GPA
        Double gpa = null;
        while (true) {
            System.out.print("New GPA [" + student.getGpa() + "]: ");
            String gpaInput = scanner.nextLine().trim();
            
            if (gpaInput.isEmpty()) {
                break; // Keep current value
            }
            
            try {
                gpa = Double.parseDouble(gpaInput);
                if (gpa >= 0.0 && gpa <= 4.0) {
                    break;
                } else {
                    System.out.println("Error: GPA must be between 0.0 and 4.0!");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Please enter a valid number!");
            }
        }
        
        if (manager.updateStudent(studentId, firstName, lastName, gpa)) {
            System.out.println("\n✓ Student updated successfully!");
            System.out.println(manager.findStudentById(studentId));
        } else {
            System.out.println("\n✗ Failed to update student!");
        }
    }

    /**
     * Removes a student from the system
     */
    private static void removeStudent() {
        System.out.println("=== REMOVE STUDENT ===");
        System.out.print("Enter Student ID to remove: ");
        String studentId = scanner.nextLine().trim();
        
        Student student = manager.findStudentById(studentId);
        if (student == null) {
            System.out.println("\n✗ No student found with ID: " + studentId);
            return;
        }
        
        System.out.println("\nStudent to be removed:");
        System.out.println(student);
        System.out.print("\nAre you sure you want to remove this student? (yes/no): ");
        String confirmation = scanner.nextLine().trim().toLowerCase();
        
        if (confirmation.equals("yes") || confirmation.equals("y")) {
            if (manager.removeStudent(studentId)) {
                System.out.println("\n✓ Student removed successfully!");
            } else {
                System.out.println("\n✗ Failed to remove student!");
            }
        } else {
            System.out.println("\nRemoval cancelled.");
        }
    }

    /**
     * Sorts students by various criteria
     */
    private static void sortStudents() {
        System.out.println("=== SORT STUDENTS ===");
        System.out.println("1. Sort by Student ID");
        System.out.println("2. Sort by Last Name");
        System.out.println("3. Sort by GPA (highest first)");
        System.out.print("Enter your choice (1-3): ");
        
        if (!scanner.hasNextInt()) {
            scanner.nextLine();
            System.out.println("Invalid input!");
            return;
        }
        
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        
        switch (choice) {
            case 1:
                manager.sortByStudentId();
                System.out.println("\n✓ Students sorted by ID!");
                break;
            case 2:
                manager.sortByLastName();
                System.out.println("\n✓ Students sorted by Last Name!");
                break;
            case 3:
                manager.sortByGPA();
                System.out.println("\n✓ Students sorted by GPA!");
                break;
            default:
                System.out.println("Invalid choice!");
                return;
        }
        
        manager.listAllStudents();
    }

    /**
     * Displays statistics about the student records
     * Uses RECURSIVE method to count students above GPA threshold
     */
    private static void displayStatistics() {
        System.out.println("=== STATISTICS ===");
        
        if (manager.getStudentCount() == 0) {
            System.out.println("No students in the system.");
            return;
        }
        
        System.out.println("Total Students: " + manager.getStudentCount());
        System.out.println("Average GPA: " + String.format("%.2f", manager.calculateAverageGPA()));
        
        Student highest = manager.findHighestGPA();
        if (highest != null) {
            System.out.println("Highest GPA: " + String.format("%.2f", highest.getGpa()) + 
                             " (" + highest.getFullName() + ")");
        }
        
        // Use RECURSIVE method to count students
        System.out.println("\n--- GPA Distribution (using Recursive Count) ---");
        int above35 = manager.countStudentsAboveGPA(3.5);
        int above30 = manager.countStudentsAboveGPA(3.0);
        int above25 = manager.countStudentsAboveGPA(2.5);
        
        System.out.println("Students with GPA > 3.5: " + above35);
        System.out.println("Students with GPA > 3.0: " + above30);
        System.out.println("Students with GPA > 2.5: " + above25);
    }
}
