import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * StudentGUIView - JavaFX View Layer (MVC Pattern)
 * 
 * Handles all GUI components and dialogs:
 * - Creating UI layouts and components
 * - Showing dialogs for input/output
 * - Displaying student information in tables
 * - Alert messages (success, error, warning, info)
 * 
 * @author Leena Komenski
 */
public class StudentGUIView {
    private TableView<Student> tableView;
    private ObservableList<Student> observableStudents;
    private Label statusLabel;

    /**
     * Creates the main layout with all sections
     */
    public BorderPane createMainLayout(Stage primaryStage, 
                                      Runnable onAdd, Runnable onSearchId, Runnable onSearchName,
                                      Runnable onUpdate, Runnable onRemove, Runnable onSort,
                                      Runnable onStats, Runnable onRefresh) {
        BorderPane mainLayout = new BorderPane();
        mainLayout.setPadding(new Insets(10));

        // Top: Title and info
        VBox topSection = createTopSection();
        mainLayout.setTop(topSection);

        // Center: Table view
        VBox centerSection = createCenterSection();
        mainLayout.setCenter(centerSection);

        // Right: Action buttons
        VBox rightSection = createRightSection(primaryStage, onAdd, onSearchId, onSearchName,
                                               onUpdate, onRemove, onSort, onStats, onRefresh);
        mainLayout.setRight(rightSection);

        // Bottom: Status bar
        HBox bottomSection = createBottomSection();
        mainLayout.setBottom(bottomSection);

        return mainLayout;
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

        tableView.getColumns().add(idCol);
        tableView.getColumns().add(firstNameCol);
        tableView.getColumns().add(lastNameCol);
        tableView.getColumns().add(gpaCol);

        centerSection.getChildren().addAll(tableLabel, tableView);
        VBox.setVgrow(tableView, Priority.ALWAYS);

        return centerSection;
    }

    /**
     * Creates the right section with all action buttons
     */
    private VBox createRightSection(Stage primaryStage,
                                   Runnable onAdd, Runnable onSearchId, Runnable onSearchName,
                                   Runnable onUpdate, Runnable onRemove, Runnable onSort,
                                   Runnable onStats, Runnable onRefresh) {
        VBox rightSection = new VBox(10);
        rightSection.setPadding(new Insets(10));
        rightSection.setPrefWidth(200);

        Label actionsLabel = new Label("Actions");
        actionsLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");

        // Create action buttons
        Button addBtn = createStyledButton("➕ Add Student");
        Button searchIdBtn = createStyledButton("🔍 Search by ID");
        Button searchNameBtn = createStyledButton("🔍 Search by Last Name");
        Button updateBtn = createStyledButton("✏️ Update Student");
        Button removeBtn = createStyledButton("🗑️ Remove Student");
        Button sortBtn = createStyledButton("📊 Sort Students");
        Button statsBtn = createStyledButton("📈 Statistics");
        Button refreshBtn = createStyledButton("🔄 Refresh Table");

        // Button actions
        addBtn.setOnAction(e -> onAdd.run());
        searchIdBtn.setOnAction(e -> onSearchId.run());
        searchNameBtn.setOnAction(e -> onSearchName.run());
        updateBtn.setOnAction(e -> onUpdate.run());
        removeBtn.setOnAction(e -> onRemove.run());
        sortBtn.setOnAction(e -> onSort.run());
        statsBtn.setOnAction(e -> onStats.run());
        refreshBtn.setOnAction(e -> onRefresh.run());

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
     * Shows dialog to add a new student
     * @return Array with [firstName, lastName, gpa] or null if cancelled
     */
    public String[] showAddStudentDialog(Stage owner) {
        Dialog<String[]> dialog = new Dialog<>();
        dialog.setTitle("Add New Student");
        dialog.setHeaderText("Enter student information");
        dialog.initOwner(owner);

        ButtonType addButtonType = new ButtonType("Add", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(addButtonType, ButtonType.CANCEL);

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
        firstNameField.requestFocus();

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == addButtonType) {
                return new String[]{
                    firstNameField.getText().trim(),
                    lastNameField.getText().trim(),
                    gpaField.getText().trim()
                };
            }
            return null;
        });

        Optional<String[]> result = dialog.showAndWait();
        return result.orElse(null);
    }

    /**
     * Shows dialog to search by student ID
     */
    public String showSearchByIdDialog(Stage owner) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Search by Student ID");
        dialog.setHeaderText("Enter Student ID to search");
        dialog.setContentText("Student ID:");
        dialog.initOwner(owner);

        Optional<String> result = dialog.showAndWait();
        return result.orElse(null);
    }

    /**
     * Shows dialog to search by last name
     */
    public String showSearchByLastNameDialog(Stage owner) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Search by Last Name (Recursive)");
        dialog.setHeaderText("Search using recursive algorithm");
        dialog.setContentText("Last Name:");
        dialog.initOwner(owner);

        Optional<String> result = dialog.showAndWait();
        return result.orElse(null);
    }

    /**
     * Shows dialog to get student ID for update
     */
    public String showGetStudentIdDialog(Stage owner, String title, String header) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle(title);
        dialog.setHeaderText(header);
        dialog.setContentText("Student ID:");
        dialog.initOwner(owner);

        Optional<String> result = dialog.showAndWait();
        return result.orElse(null);
    }

    /**
     * Shows dialog to update student information
     * @return Array with [firstName, lastName, gpa] or null if cancelled
     */
    public String[] showUpdateStudentDialog(Stage owner, Student student) {
        Dialog<String[]> dialog = new Dialog<>();
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

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == updateButtonType) {
                return new String[]{
                    firstNameField.getText().trim(),
                    lastNameField.getText().trim(),
                    gpaField.getText().trim()
                };
            }
            return null;
        });

        Optional<String[]> result = dialog.showAndWait();
        return result.orElse(null);
    }

    /**
     * Shows confirmation dialog for removing a student
     */
    public boolean showRemoveConfirmation(Stage owner, Student student) {
        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle("Confirm Removal");
        confirmAlert.setHeaderText("Are you sure you want to remove this student?");
        confirmAlert.setContentText(student.toString());
        confirmAlert.initOwner(owner);

        Optional<ButtonType> result = confirmAlert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
    }

    /**
     * Shows dialog to select sort criteria
     */
    public String showSortDialog(Stage owner) {
        ChoiceDialog<String> dialog = new ChoiceDialog<>("By Student ID",
                "By Student ID", "By Last Name", "By GPA (Highest First)");
        dialog.setTitle("Sort Students");
        dialog.setHeaderText("Choose sorting criteria");
        dialog.setContentText("Sort by:");
        dialog.initOwner(owner);

        Optional<String> result = dialog.showAndWait();
        return result.orElse(null);
    }

    /**
     * Refreshes the table with student data
     */
    public void refreshTable(List<Student> students) {
        observableStudents.clear();
        observableStudents.addAll(students);
    }

    /**
     * Highlights a student in the table
     */
    public void highlightStudent(Student student) {
        tableView.getSelectionModel().select(student);
        tableView.scrollTo(student);
    }

    /**
     * Highlights multiple students in the table
     */
    public void highlightStudents(ArrayList<Student> students) {
        tableView.getSelectionModel().clearSelection();
        for (Student s : students) {
            tableView.getSelectionModel().select(s);
        }
    }

    /**
     * Updates the status bar message
     */
    public void updateStatus(String message) {
        statusLabel.setText(message);
    }

    /**
     * Shows an error alert dialog
     */
    public void showError(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Shows an information alert dialog
     */
    public void showInfo(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Shows a warning alert dialog
     */
    public void showWarning(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
