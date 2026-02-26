import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * Student Records Manager - JavaFX GUI Application (MVC Pattern)
 * 
 * This program demonstrates core programming concepts:
 * - Problem-solving and algorithm development
 * - Object-Oriented Programming (OOP) with classes and objects
 * - Data structures (ArrayList)
 * - Recursion (search and count methods)
 * - Input validation and error handling
 * - JavaFX GUI interface
 * - MVC (Model-View-Controller) design pattern
 * 
 * Architecture:
 * - Model: StudentManager (business logic and data management)
 * - View: StudentGUIView (user interface components and dialogs)
 * - Controller: StudentGUIController (event handling and coordination)
 * 
 * @author Leena Komenski
 */
public class AppGUI extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Student Records Manager");

        // Create MVC components
        StudentManager manager = new StudentManager();  // Model
        StudentGUIView view = new StudentGUIView();      // View
        StudentGUIController controller = new StudentGUIController(manager, view, primaryStage);  // Controller

        // Add sample data
        controller.addSampleData();

        // Create main layout with event handlers
        BorderPane mainLayout = view.createMainLayout(
            primaryStage,
            () -> controller.handleAddStudent(),
            () -> controller.handleSearchById(),
            () -> controller.handleSearchByLastName(),
            () -> controller.handleUpdateStudent(),
            () -> controller.handleRemoveStudent(),
            () -> controller.handleSortStudents(),
            () -> controller.handleDisplayStatistics(),
            () -> controller.refreshTable()
        );

        // Create scene and show
        Scene scene = new Scene(mainLayout, 1000, 600);
        primaryStage.setScene(scene);
        primaryStage.show();

        // Initial table refresh
        controller.refreshTable();
    }

    /**
     * Main method to launch the JavaFX application
     */
    public static void main(String[] args) {
        launch(args);
    }
}
