import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.util.Optional;

import manager.StudentManager;
import view.StudentGUIView;
import controller.StudentGUIController;

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

        // Prompt user for sample data
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Load Sample Data");
        alert.setHeaderText("Load sample data for testing?");
        alert.setContentText("This will load student records from sample_data.csv.\n" +
                           "Choose 'Cancel' to start with an empty list.");
        
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            controller.loadSampleData();
        }

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
            () -> controller.handleSaveToCSV(),
            () -> controller.handleLoadFromCSV(),
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
