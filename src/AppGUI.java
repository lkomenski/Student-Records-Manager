import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.ArrayList;
import java.util.Optional;

/**
 * Student Records Manager - JavaFX GUI Application
 * 
 * This program demonstrates core programming concepts:
 * - Problem-solving and algorithm development
 * - Object-Oriented Programming (OOP) with classes and objects
 * - Data structures (ArrayList)
 * - Recursion (search and count methods)
 * - Input validation and error handling
 * - JavaFX GUI interface
 * 
 * @author Student Project
 * @version 2.0 (GUI)
 */
public class AppGUI extends Application {
    private StudentManager manager = new StudentManager();
    private TableView<Student> tableView;
    private ObservableList<Student> observableStudents;
    private Label statusLabel;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Student Records Manager");

        // Add sample data for testing
        addSampleData();

        // Create main layout
        BorderPane mainLayout = new BorderPane();
        mainLayout.setPadding(new Insets(10));

        // Top: Title and info
        VBox topSection = createTopSection();
        mainLayout.setTop(topSection);

        // Center: Table view
        VBox centerSection = createCenterSection();
        mainLayout.setCenter(centerSection);

        // Right: Action buttons
        VBox rightSection = createRightSection(primaryStage);
        mainLayout.setRight(rightSection);

        // Bottom: Status bar
        HBox bottomSection = createBottomSection();
        mainLayout.setBottom(bottomSection);

        // Create scene
        Scene scene = new Scene(mainLayout, 1000, 600);
        primaryStage.setScene(scene);
        primaryStage.show();

        // Initial table refresh
        refreshTable();
    }

    /**
     * Adds sample student data for testing purposes
     * Preserves all functionality from console version
     */
    private void addSampleData() {
        try {
            manager.addStudent(new Student("S001", "John", "Doe", 3.5));
            manager.addStudent(new Student("S002", "Jane", "Smith", 3.8));
            manager.addStudent(new Student("S003", "Alice", "Johnson", 3.2));
        } catch (StudentException e) {
            showError("Error loading sample data", e.getMessage());
        }
    }

    /**
     * Creates the top section with title and welcome message
     */
    private VBox createTopSection() {
        VBox topSection = new VBox(5);
        topSection.setPadding(new Insets(0, 0, 10, 0));

        Label titleLabel = new Label("STUDENT RECORDS MANAGER");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        Label welcomeLabel = new Label("Manage student records with ease");
        welcomeLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: gray;");

        topSection.getChildren().addAll(titleLabel, welcomeLabel);
        return topSection;
    }

    /**
     * Creates the center section with the student table
     * Displays all student information in a structured table
     */
    private VBox createCenterSection() {
        VBox centerSection = new VBox(10);
        centerSection.setPadding(new Insets(10));

        Label tableLabel = new Label("All Students");
        tableLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");

        // Create table
        tableView = new TableView<>();
        observableStudents = FXCollections.observableArrayList();
        tableView.setItems(observableStudents);

        // Define columns
        TableColumn<Student, String> idCol = new TableColumn<>("Student ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("studentId"));
        idCol.setPrefWidth(100);

        TableColumn<Student, String> firstNameCol = new TableColumn<>("First Name");
        firstNameCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        firstNameCol.setPrefWidth(150);

        TableColumn<Student, String> lastNameCol = new TableColumn<>("Last Name");
        lastNameCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        lastNameCol.setPrefWidth(150);

        TableColumn<Student, Double> gpaCol = new TableColumn<>("GPA");
        gpaCol.setCellValueFactory(new PropertyValueFactory<>("gpa"));
        gpaCol.setPrefWidth(80);

        tableView.getColumns().addAll(idCol, firstNameCol, lastNameCol, gpaCol);

        centerSection.getChildren().addAll(tableLabel, tableView);
        VBox.setVgrow(tableView, Priority.ALWAYS);

        return centerSection;
    }

    /**
     * Creates the right section with all action buttons
     * Each button corresponds to a menu option from the console version
     */
    private VBox createRightSection(Stage primaryStage) {
        VBox rightSection = new VBox(10);
        rightSection.setPadding(new Insets(10));
        rightSection.setPrefWidth(200);

        Label actionsLabel = new Label("Actions");
        actionsLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");

        // Create action buttons (preserving all console functionality)
        Button addBtn = createStyledButton("➕ Add Student");
        Button searchIdBtn = createStyledButton("🔍 Search by ID");
        Button searchNameBtn = createStyledButton("🔍 Search by Last Name");
        Button updateBtn = createStyledButton("✏️ Update Student");
        Button removeBtn = createStyledButton("🗑️ Remove Student");
        Button sortBtn = createStyledButton("📊 Sort Students");
        Button statsBtn = createStyledButton("📈 Statistics");
        Button refreshBtn = createStyledButton("🔄 Refresh Table");

        // Button actions
        addBtn.setOnAction(e -> addStudent(primaryStage));
        searchIdBtn.setOnAction(e -> searchStudentById(primaryStage));
        searchNameBtn.setOnAction(e -> searchStudentByLastName(primaryStage));
        updateBtn.setOnAction(e -> updateStudent(primaryStage));
        removeBtn.setOnAction(e -> removeStudent(primaryStage));
        sortBtn.setOnAction(e -> sortStudents(primaryStage));
        statsBtn.setOnAction(e -> displayStatistics(primaryStage));
        refreshBtn.setOnAction(e -> refreshTable());

        rightSection.getChildren().addAll(
            actionsLabel,
            new Separator(),
            addBtn,
            searchIdBtn,
            searchNameBtn,
            updateBtn,
            removeBtn,
            new Separator(),
            sortBtn,
            statsBtn,
            new Separator(),
            refreshBtn
        );

        return rightSection;
    }

    /**
     * Creates the bottom status bar
     */
    private HBox createBottomSection() {
        HBox bottomSection = new HBox();
        bottomSection.setPadding(new Insets(10, 0, 0, 0));
        bottomSection.setStyle("-fx-border-color: lightgray; -fx-border-width: 1 0 0 0;");

        statusLabel = new Label("Ready");
        statusLabel.setStyle("-fx-font-size: 11px;");

        bottomSection.getChildren().add(statusLabel);
        return bottomSection;
    }

    /**
     * Creates a styled button with consistent appearance
     */
    private Button createStyledButton(String text) {
        Button button = new Button(text);
        button.setPrefWidth(180);
        button.setStyle("-fx-font-size: 12px;");
        return button;
    }

    /**
     * Refreshes the table view with current student data
     */
    private void refreshTable() {
        observableStudents.clear();
        observableStudents.addAll(manager.getAllStudents());
        updateStatus("Table refreshed. Total students: " + manager.getStudentCount());
    }

    /**
     * Adds a new student to the system with input validation
     * Preserves all validation logic from console version
     */
    private void addStudent(Stage owner) {
        Dialog<Student> dialog = new Dialog<>();
        dialog.setTitle("Add New Student");
        dialog.setHeaderText("Enter student information");
        dialog.initOwner(owner);

        // Set button types
        ButtonType addButtonType = new ButtonType("Add", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(addButtonType, ButtonType.CANCEL);

        // Create form
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField firstNameField = new TextField();
        firstNameField.setPromptText("First Name");
        TextField lastNameField = new TextField();
        lastNameField.setPromptText("Last Name");
        TextField gpaField = new TextField();
        gpaField.setPromptText("0.0 - 4.0");

        Label infoLabel = new Label("Student ID will be auto-generated");
        infoLabel.setStyle("-fx-font-size: 10px; -fx-text-fill: gray;");

        grid.add(new Label("First Name:"), 0, 0);
        grid.add(firstNameField, 1, 0);
        grid.add(new Label("Last Name:"), 0, 1);
        grid.add(lastNameField, 1, 1);
        grid.add(new Label("GPA:"), 0, 2);
        grid.add(gpaField, 1, 2);
        grid.add(infoLabel, 1, 3);

        dialog.getDialogPane().setContent(grid);

        // Request focus on first field
        firstNameField.requestFocus();

        // Input validation with error handling
        Button addButton = (Button) dialog.getDialogPane().lookupButton(addButtonType);
        addButton.setOnAction(e -> {
            try {
                // Validate first name
                String firstName = firstNameField.getText().trim();
                if (firstName.isEmpty()) {
                    showError("Validation Error", "First name cannot be empty!");
                    e.consume();
                    return;
                }

                // Validate last name
                String lastName = lastNameField.getText().trim();
                if (lastName.isEmpty()) {
                    showError("Validation Error", "Last name cannot be empty!");
                    e.consume();
                    return;
                }

                // Validate GPA
                double gpa;
                try {
                    gpa = Double.parseDouble(gpaField.getText().trim());
                    if (gpa < 0.0 || gpa > 4.0) {
                        showError("Validation Error", "GPA must be between 0.0 and 4.0!");
                        e.consume();
                        return;
                    }
                } catch (NumberFormatException ex) {
                    showError("Validation Error", "Please enter a valid number for GPA!");
                    e.consume();
                    return;
                }

                // Add student with auto-generated ID
                Student newStudent = manager.addStudentAutoId(firstName, lastName, gpa);
                refreshTable();
                updateStatus("✓ Student added successfully: " + newStudent.getStudentId());
                showInfo("Success", "Student added successfully!\n" + newStudent.toString());

            } catch (StudentException ex) {
                showError("Error", "Failed to add student: " + ex.getMessage());
                e.consume();
            }
        });

        dialog.showAndWait();
    }

    /**
     * Searches for a student by their ID
     * Preserves search functionality from console version
     */
    private void searchStudentById(Stage owner) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Search by Student ID");
        dialog.setHeaderText("Enter Student ID to search");
        dialog.setContentText("Student ID:");
        dialog.initOwner(owner);

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(studentId -> {
            Student student = manager.findStudentById(studentId.trim());
            if (student != null) {
                showInfo("Student Found", student.toString());
                // Highlight in table
                tableView.getSelectionModel().select(student);
                tableView.scrollTo(student);
                updateStatus("Student found: " + studentId);
            } else {
                showWarning("Not Found", "No student found with ID: " + studentId);
                updateStatus("Student not found: " + studentId);
            }
        });
    }

    /**
     * Searches for students by last name using RECURSIVE method
     * Preserves recursive search algorithm from console version
     */
    private void searchStudentByLastName(Stage owner) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Search by Last Name (Recursive)");
        dialog.setHeaderText("Search using recursive algorithm");
        dialog.setContentText("Last Name:");
        dialog.initOwner(owner);

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(lastName -> {
            if (lastName.trim().isEmpty()) {
                showError("Validation Error", "Last name cannot be empty!");
                return;
            }

            // Call the RECURSIVE search method
            ArrayList<Student> results = manager.searchByLastName(lastName.trim());

            if (results.isEmpty()) {
                showWarning("No Results", "No students found with last name: " + lastName);
                updateStatus("No results for: " + lastName);
            } else {
                StringBuilder message = new StringBuilder();
                message.append("Found ").append(results.size()).append(" student(s):\n\n");
                for (Student student : results) {
                    message.append(student.toString()).append("\n");
                }

                showInfo("Search Results (Recursive)", message.toString());
                
                // Highlight results in table
                tableView.getSelectionModel().clearSelection();
                for (Student s : results) {
                    tableView.getSelectionModel().select(s);
                }
                updateStatus("Found " + results.size() + " student(s) with last name: " + lastName);
            }
        });
    }

    /**
     * Updates an existing student's information
     * Preserves all update and validation logic from console version
     */
    private void updateStudent(Stage owner) {
        // First, get the student ID
        TextInputDialog idDialog = new TextInputDialog();
        idDialog.setTitle("Update Student");
        idDialog.setHeaderText("Enter Student ID to update");
        idDialog.setContentText("Student ID:");
        idDialog.initOwner(owner);

        Optional<String> idResult = idDialog.showAndWait();
        if (!idResult.isPresent()) return;

        String studentId = idResult.get().trim();
        Student student = manager.findStudentById(studentId);

        if (student == null) {
            showError("Not Found", "No student found with ID: " + studentId);
            return;
        }

        // Show update dialog with current values
        Dialog<Boolean> dialog = new Dialog<>();
        dialog.setTitle("Update Student");
        dialog.setHeaderText("Update information for: " + student.getFullName());
        dialog.initOwner(owner);

        ButtonType updateButtonType = new ButtonType("Update", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(updateButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField firstNameField = new TextField(student.getFirstName());
        TextField lastNameField = new TextField(student.getLastName());
        TextField gpaField = new TextField(String.valueOf(student.getGpa()));

        Label infoLabel = new Label("Leave fields unchanged to keep current values");
        infoLabel.setStyle("-fx-font-size: 10px; -fx-text-fill: gray;");

        grid.add(new Label("First Name:"), 0, 0);
        grid.add(firstNameField, 1, 0);
        grid.add(new Label("Last Name:"), 0, 1);
        grid.add(lastNameField, 1, 1);
        grid.add(new Label("GPA:"), 0, 2);
        grid.add(gpaField, 1, 2);
        grid.add(infoLabel, 1, 3);

        dialog.getDialogPane().setContent(grid);

        Button updateButton = (Button) dialog.getDialogPane().lookupButton(updateButtonType);
        updateButton.setOnAction(e -> {
            try {
                String firstName = firstNameField.getText().trim();
                String lastName = lastNameField.getText().trim();
                Double gpa = null;

                String gpaText = gpaField.getText().trim();
                if (!gpaText.isEmpty()) {
                    try {
                        gpa = Double.parseDouble(gpaText);
                        if (gpa < 0.0 || gpa > 4.0) {
                            showError("Validation Error", "GPA must be between 0.0 and 4.0!");
                            e.consume();
                            return;
                        }
                    } catch (NumberFormatException ex) {
                        showError("Validation Error", "Please enter a valid number for GPA!");
                        e.consume();
                        return;
                    }
                }

                manager.updateStudent(studentId, firstName, lastName, gpa);
                refreshTable();
                Student updated = manager.findStudentById(studentId);
                updateStatus("✓ Student updated: " + studentId);
                showInfo("Success", "Student updated successfully!\n" + updated.toString());

            } catch (StudentException ex) {
                showError("Error", "Failed to update student: " + ex.getMessage());
                e.consume();
            }
        });

        dialog.showAndWait();
    }

    /**
     * Removes a student from the system with confirmation
     * Preserves confirmation logic from console version
     */
    private void removeStudent(Stage owner) {
        // Get student ID
        TextInputDialog idDialog = new TextInputDialog();
        idDialog.setTitle("Remove Student");
        idDialog.setHeaderText("Enter Student ID to remove");
        idDialog.setContentText("Student ID:");
        idDialog.initOwner(owner);

        Optional<String> idResult = idDialog.showAndWait();
        if (!idResult.isPresent()) return;

        String studentId = idResult.get().trim();
        Student student = manager.findStudentById(studentId);

        if (student == null) {
            showError("Not Found", "No student found with ID: " + studentId);
            return;
        }

        // Confirmation dialog
        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle("Confirm Removal");
        confirmAlert.setHeaderText("Are you sure you want to remove this student?");
        confirmAlert.setContentText(student.toString());
        confirmAlert.initOwner(owner);

        Optional<ButtonType> result = confirmAlert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                manager.removeStudent(studentId, true);
                refreshTable();
                updateStatus("✓ Student removed: " + studentId);
                showInfo("Success", "Student removed successfully!");
            } catch (StudentException e) {
                showError("Error", "Failed to remove student: " + e.getMessage());
            }
        } else {
            updateStatus("Removal cancelled");
        }
    }

    /**
     * Sorts students by various criteria
     * Preserves all sorting options from console version
     */
    private void sortStudents(Stage owner) {
        ChoiceDialog<String> dialog = new ChoiceDialog<>("By Student ID",
                "By Student ID", "By Last Name", "By GPA (Highest First)");
        dialog.setTitle("Sort Students");
        dialog.setHeaderText("Choose sorting criteria");
        dialog.setContentText("Sort by:");
        dialog.initOwner(owner);

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(choice -> {
            switch (choice) {
                case "By Student ID":
                    manager.sortByStudentId();
                    updateStatus("✓ Students sorted by ID");
                    break;
                case "By Last Name":
                    manager.sortByLastName();
                    updateStatus("✓ Students sorted by Last Name");
                    break;
                case "By GPA (Highest First)":
                    manager.sortByGPA();
                    updateStatus("✓ Students sorted by GPA");
                    break;
            }
            refreshTable();
        });
    }

    /**
     * Displays statistics about the student records
     * Uses RECURSIVE method to count students above GPA threshold
     * Preserves all statistical calculations from console version
     */
    private void displayStatistics(Stage owner) {
        if (manager.getStudentCount() == 0) {
            showWarning("No Data", "No students in the system.");
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

        showInfo("Statistics", stats.toString());
        updateStatus("Statistics displayed");
    }

    /**
     * Updates the status bar message
     */
    private void updateStatus(String message) {
        statusLabel.setText(message);
    }

    /**
     * Shows an error alert dialog
     */
    private void showError(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Shows an information alert dialog
     */
    private void showInfo(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Shows a warning alert dialog
     */
    private void showWarning(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Main method to launch the JavaFX application
     */
    public static void main(String[] args) {
        launch(args);
    }
}
