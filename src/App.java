import java.util.Scanner;

import manager.StudentManager;
import view.StudentView;
import controller.StudentController;

/**
 * Student Records Manager - Main Application (MVC Pattern)
 * 
 * This program demonstrates core programming concepts:
 * - Problem-solving and algorithm development
 * - Object-Oriented Programming (OOP) with classes and objects
 * - Data structures (ArrayList)
 * - Recursion (search and count methods)
 * - Input validation and error handling
 * - Menu-driven console interface
 * - MVC (Model-View-Controller) design pattern
 * 
 * Architecture:
 * - Model: StudentManager (business logic and data management)
 * - View: StudentView (user interface and I/O)
 * - Controller: StudentController (flow control and coordination)
 * 
 * @author Leena Komenski
 */
public class App {
    public static void main(String[] args) {
        // Create Scanner for console input
        Scanner scanner = new Scanner(System.in);
        
        // Create MVC components
        StudentManager manager = new StudentManager();  // Model
        StudentView view = new StudentView(scanner);     // View
        StudentController controller = new StudentController(manager, view);  // Controller
        
        // Run the application
        controller.run();
        
        // Clean up
        scanner.close();
    }
}
