# Student Records Manager

A Java application for managing student records with both **Console** and **JavaFX GUI** interfaces. Demonstrates MVC architecture, recursion, ArrayList usage, input validation, and CSV data persistence following object-oriented principles.

---

## Prerequisites

- **Java JDK 8+** (required for both versions)
- **JavaFX SDK** (required for GUI version only) - [Download here](https://gluonhq.com/products/javafx/)
- **JUnit 5** (included in `lib/` folder for testing)

---

## How to Run

### Console Version

```bash
# Compile
javac -d bin src/exceptions/*.java src/model/*.java src/util/*.java src/service/*.java src/manager/*.java src/view/StudentView.java src/controller/StudentController.java src/App.java

# Run
java -cp bin App
```

### JavaFX GUI Version

**Prerequisites**: JavaFX SDK ([download](https://gluonhq.com/products/javafx/) or use bundled JDK)

```bash
# Compile (adjust JavaFX path)
javac --module-path "C:\javafx-sdk-21\lib" --add-modules javafx.controls -d bin src/exceptions/*.java src/model/*.java src/util/*.java src/service/*.java src/manager/*.java src/view/*.java src/controller/*.java src/AppGUI.java

# Run
java --module-path "C:\javafx-sdk-21\lib" --add-modules javafx.controls -cp bin AppGUI
```

See [JAVAFX_SETUP.md](JAVAFX_SETUP.md) for detailed setup instructions.

---

## Features

- Add students with auto-generated sequential IDs (S001, S002, ...) — IDs cannot be entered manually; duplicate IDs are rejected even if added programmatically (verified in `test/StudentManagerTest.java` Test 2)
- List all students
- Search by ID or last name (last name search uses recursion)
- Update student information
- Remove students (with confirmation prompt)
- Sort by ID, last name, or GPA
- View statistics (average GPA, highest GPA, count above GPA thresholds using recursion)
- **Save/Load student data to/from CSV files** (replaces current records on load)
- **Import from CSV** (appends to existing records without replacing them)
- **Sample data loading on startup** (loads `sample_data.csv` automatically)

---

## Where Recursion is Used

This project includes **three recursive methods** that demonstrate different applications of recursion:

### 1. Recursive Search (`searchByLastNameRecursive`)
**Location**: [StudentManager.java](src/manager/StudentManager.java#L160-L180)  
**Purpose**: Searches for students by last name by recursively traversing the ArrayList.  
**Why Recursion**: Demonstrates how recursion can replace iterative loops for list traversal. The base case occurs when reaching the end of the list, and the recursive case checks each student and continues to the next index.

### 2. Recursive Counting (`countStudentsAboveGPARecursive`)
**Location**: [StudentManager.java](src/manager/StudentManager.java#L280-L300)  
**Purpose**: Counts students with GPA above a threshold using recursive computation.  
**Why Recursion**: Shows how recursion can be used for accumulation and counting. Each recursive call adds to the count and returns the sum of current and remaining elements.

### 3. Recursive Input Validation (`promptForMenuChoice`)
**Location**: [StudentView.java](src/view/StudentView.java#L137-L152)  
**Purpose**: Validates menu input by recursively re-prompting until valid input is received.  
**Why Recursion**: Demonstrates recursion for user input validation. Instead of using a while loop, the method calls itself when input is invalid, naturally continuing until a valid choice is entered.

All recursive methods include clear base cases to prevent infinite recursion and comments explaining their recursive logic.

---

## Architecture

Both console and GUI versions follow **MVC (Model-View-Controller)** pattern with **full feature parity**:

**Model Layer**: [StudentManager.java](src/manager/StudentManager.java), [Student.java](src/model/Student.java), exceptions/
- Business logic, CRUD operations, recursion
- Returns unmodifiable lists for encapsulation
- Immutable student IDs

**Service Layer**: [StudentDataService.java](src/service/StudentDataService.java), [InputValidator.java](src/util/InputValidator.java)
- CSV file persistence (save/load/import)
- Centralized validation utilities
- Separation of concerns from business logic

**Console Version**: [App.java](src/App.java), [StudentView.java](src/view/StudentView.java), [StudentController.java](src/controller/StudentController.java)

**GUI Version**: [AppGUI.java](src/AppGUI.java), [StudentGUIView.java](src/view/StudentGUIView.java), [StudentGUIController.java](src/controller/StudentGUIController.java)

This design keeps each layer focused on one responsibility, making the code easier to read, test, and extend.

---

## Testing

Comprehensive **JUnit 5 test suite** with **16 automated unit tests** covering all CRUD operations, exception handling, CSV file persistence, and edge cases. Tests validate business logic independently from UI, following TDD principles. See [TESTING.md](TESTING.md) and [HOW_TO_TEST.md](HOW_TO_TEST.md) for details.

---

## CSV File Persistence

Student records can be saved/loaded to CSV files for data persistence:

**Save to CSV**: [StudentDataService.java](src/service/StudentDataService.java#L38-L56) - Exports all records with try-with-resources and proper error handling

**Load from CSV**: [StudentDataService.java](src/service/StudentDataService.java#L70-L149) - Imports records with validation and robust error handling (skips malformed lines)

**Import from CSV**: [StudentDataService.java](src/service/StudentDataService.java#L161-L223) - Appends records without clearing existing data, with duplicate detection

**CSV Format**:
```csv
StudentID,FirstName,LastName,GPA
S001,John,Doe,3.5
S002,Jane,Smith,3.8
```

**Sample Data on Startup**: Both versions prompt to load [sample_data.csv](sample_data.csv) on startup (gracefully falls back if file not found).

---

## Project Structure

```
src/
├── model/Student.java               # Domain entity (immutable ID)
├── manager/StudentManager.java      # Business logic, CRUD, recursion
├── service/StudentDataService.java  # CSV persistence
├── util/InputValidator.java         # Validation utilities
├── view/                            # Console & GUI presentation
├── controller/                      # Application flow control
├── exceptions/                      # Custom exception hierarchy
├── App.java                         # Console entry point
└── AppGUI.java                      # GUI entry point

test/StudentManagerTest.java         # JUnit 5 test suite (16 tests)
lib/junit-platform-console-*.jar     # Testing framework
sample_data.csv                      # Sample data (other CSVs gitignored)
```

This structure separates code by layer, making it easy to navigate and understand each component's role.

---

## Additional Documentation

- **[JAVAFX_SETUP.md](JAVAFX_SETUP.md)**: JavaFX GUI setup instructions and platform-specific guidance
- **[TESTING.md](TESTING.md)**: Test suite architecture, execution commands, and coverage details
- **[HOW_TO_TEST.md](HOW_TO_TEST.md)**: Quick testing guide