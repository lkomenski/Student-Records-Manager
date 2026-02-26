package view;

import java.util.List;
import java.util.Scanner;

import model.Student;

/**
 * StudentView - Console View Layer (MVC Pattern)
 * 
 * Handles all console input/output operations:
 * - Displaying menus and messages
 * - Printing student information
 * - Prompting for user input
 * - Input validation (delegates complex validation to controller)
 * 
 * @author Leena Komenski
 */
public class StudentView {
    private Scanner scanner;

    public StudentView(Scanner scanner) {
        this.scanner = scanner;
    }

    /**
     * Displays the welcome banner
     */
    public void printWelcome() {
        System.out.println("=".repeat(60));
        System.out.println("     STUDENT RECORDS MANAGER");
        System.out.println("=".repeat(60));
        System.out.println("Welcome! This system helps you manage student records.\n");
    }

    /**
     * Displays the main menu
     */
    public void printMenu() {
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
        System.out.println("9. Save to CSV file");
        System.out.println("10. Load from CSV file");
        System.out.println("11. Quit");
        System.out.println("-".repeat(60));
    }

    /**
     * Displays a single student's information
     */
    public void printStudent(Student student) {
        System.out.println(student);
    }

    /**
     * Displays a list of students
     */
    public void printStudentList(List<Student> students) {
        for (Student student : students) {
            System.out.println(student);
        }
    }

    /**
     * Displays all students with header
     */
    public void printAllStudents(List<Student> students) {
        if (students.isEmpty()) {
            System.out.println("No students in the system.");
            return;
        }
        System.out.println("\n=== ALL STUDENTS ===");
        System.out.println("Total: " + students.size());
        System.out.println("-".repeat(60));
        printStudentList(students);
        System.out.println("-".repeat(60));
    }

    /**
     * Displays a section header
     */
    public void printSectionHeader(String title) {
        System.out.println("=== " + title + " ===");
    }

    /**
     * Displays a separator line
     */
    public void printSeparator() {
        System.out.println("-".repeat(60));
    }

    /**
     * Displays a success message
     */
    public void printSuccess(String message) {
        System.out.println("\n✓ " + message);
    }

    /**
     * Displays an error message
     */
    public void printError(String message) {
        System.out.println("\n✗ " + message);
    }

    /**
     * Displays a general message
     */
    public void printMessage(String message) {
        System.out.println(message);
    }

    /**
     * Displays a blank line for readability
     */
    public void printBlankLine() {
        System.out.println();
    }

    /**
     * Displays the goodbye message
     */
    public void printGoodbye() {
        System.out.println("\nThank you for using Student Records Manager!");
        System.out.println("Goodbye!");
    }

    /**
     * Prompts for menu choice with validation
     * Uses recursion for input validation
     * @return Valid menu choice (1-11)
     */
    public int promptForMenuChoice() {
        System.out.print("Enter your choice (1-11): ");
        
        if (scanner.hasNextInt()) {
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            
            if (choice >= 1 && choice <= 11) {
                return choice;
            }
        } else {
            scanner.nextLine(); // Clear invalid input
        }
        
        System.out.println("Invalid input! Please enter a number between 1 and 11.");
        return promptForMenuChoice(); // Recursive call
    }

    /**
     * Prompts for a string input
     */
    public String promptForString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    /**
     * Prompts for a non-empty string input with validation
     */
    public String promptForNonEmptyString(String prompt, String fieldName) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            
            if (!input.isEmpty()) {
                return input;
            }
            
            System.out.println("Error: " + fieldName + " cannot be empty!");
        }
    }

    /**
     * Prompts for a double value within a range
     */
    public double promptForDouble(String prompt, double min, double max) {
        while (true) {
            System.out.print(prompt);
            
            if (scanner.hasNextDouble()) {
                double value = scanner.nextDouble();
                scanner.nextLine(); // Consume newline
                
                if (value >= min && value <= max) {
                    return value;
                } else {
                    System.out.println("Error: Value must be between " + min + " and " + max + "!");
                }
            } else {
                scanner.nextLine(); // Clear invalid input
                System.out.println("Error: Please enter a valid number!");
            }
        }
    }

    /**
     * Prompts for an optional double value (allows empty for keeping current)
     */
    public Double promptForOptionalDouble(String prompt, double min, double max) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            
            if (input.isEmpty()) {
                return null; // Keep current value
            }
            
            try {
                double value = Double.parseDouble(input);
                if (value >= min && value <= max) {
                    return value;
                } else {
                    System.out.println("Error: Value must be between " + min + " and " + max + "!");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Please enter a valid number!");
            }
        }
    }

    /**
     * Prompts for an integer choice within a range
     */
    public int promptForIntChoice(String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            
            if (scanner.hasNextInt()) {
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                
                if (choice >= min && choice <= max) {
                    return choice;
                }
            } else {
                scanner.nextLine(); // Clear invalid input
            }
            
            System.out.println("Invalid input! Please enter a number between " + min + " and " + max + ".");
        }
    }

    /**
     * Prompts for yes/no confirmation
     */
    public boolean promptForConfirmation(String prompt) {
        System.out.print(prompt + " (yes/no): ");
        String response = scanner.nextLine().trim().toLowerCase();
        return response.equals("yes") || response.equals("y");
    }

    /**
     * Displays student search results
     */
    public void printSearchResults(List<Student> results, String searchTerm) {
        if (results.isEmpty()) {
            printError("No students found with: " + searchTerm);
        } else {
            printMessage("\nStudents Found: " + results.size());
            printSeparator();
            printStudentList(results);
            printSeparator();
        }
    }

    /**
     * Displays a student found by ID
     */
    public void printStudentFound(Student student) {
        printMessage("\nStudent Found:");
        printSeparator();
        printStudent(student);
        printSeparator();
    }

    /**
     * Displays statistics
     */
    public void printStatistics(int totalStudents, double averageGPA, 
                                Student highestGPAStudent, 
                                int above35, int above30, int above25) {
        printSectionHeader("STATISTICS");
        
        if (totalStudents == 0) {
            printMessage("No students in the system.");
            return;
        }
        
        printMessage("Total Students: " + totalStudents);
        printMessage("Average GPA: " + String.format("%.2f", averageGPA));
        
        if (highestGPAStudent != null) {
            printMessage("Highest GPA: " + String.format("%.2f", highestGPAStudent.getGpa()) + 
                       " (" + highestGPAStudent.getFullName() + ")");
        }
        
        printMessage("\n--- GPA Distribution (using Recursive Count) ---");
        printMessage("Students with GPA > 3.5: " + above35);
        printMessage("Students with GPA > 3.0: " + above30);
        printMessage("Students with GPA > 2.5: " + above25);
    }

    /**
     * Prompts for a filename
     * @param prompt The prompt message to display
     * @return The filename entered by the user
     */
    public String promptForFilename(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }
}
