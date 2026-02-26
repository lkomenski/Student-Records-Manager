# Student Records Manager

A comprehensive Java application for managing student records, available in both **Console** and **JavaFX GUI** versions. Demonstrates Object-Oriented Programming, MVC architecture, ArrayList usage, recursion, input validation, and sorting algorithms.

---

## Features

- Add students with auto-generated IDs (duplicate prevention)
- List all students
- Search by ID or last name (recursive)
- Update student information
- Remove students (with confirmation)
- Sort by ID, last name, or GPA
- View statistics (average GPA, highest GPA, recursive GPA distribution)
- **Save/Load student data to/from CSV files (data persistence)**
- **Import/export functionality for bulk data management**

---

## Architecture

Both console and GUI versions follow **MVC (Model-View-Controller)** pattern with **full feature parity**, including CSV file persistence:

**Model** (shared): `StudentManager`, `Student`, `exceptions/`
- Business logic, CRUD operations, recursion, validation
- CSV save/load/import functionality
- Returns unmodifiable lists for encapsulation
- Immutable student IDs

**Console Version**:
- **View**: `StudentView` (console I/O, prompts, formatting)
- **Controller**: `StudentController` (menu flow, coordinates Model/View)
- **Entry**: `App.java`
- **CSV Operations**: Menu options 9 (Save) and 10 (Load)

**GUI Version**:
- **View**: `StudentGUIView` (JavaFX components, dialogs, layouts, FileChooser)
- **Controller**: `StudentGUIController` (event handling, validation)
- **Entry**: `AppGUI.java`
- **CSV Operations**: Dedicated buttons with file chooser dialogs

**Design Benefits**: Separation of concerns, low coupling, code reusability, defensive programming

---

## Key Concepts Demonstrated

- Object-Oriented Programming (encapsulation, inheritance)
- MVC Architecture
- ArrayList with protected access
- Recursion (search, counting, validation)
- Input validation & exception handling
- Sorting & searching algorithms
- Modular package structure
- Immutability patterns
- Test-Driven Development (TDD)
- **File I/O and data persistence (CSV format)**
- **Try-with-resources for automatic resource management**

---

## Testing

Comprehensive JUnit 5 test suite with **16 automated unit tests** covering all CRUD operations, exception handling, CSV file persistence, and edge cases. Tests validate `StudentManager` business logic independently from UI, following TDD principles (Red → Green → Refactor). Additionally includes documented manual test for UI-layer input validation. See [TESTING.md](TESTING.md) for details.

---

## How to Run

### Console Version

```bash
# Compile
javac -d bin src/exceptions/*.java src/Student.java src/StudentManager.java src/StudentView.java src/StudentController.java src/App.java

# Run
java -cp bin App
```

### JavaFX GUI Version

**Prerequisites**: JavaFX SDK ([download](https://gluonhq.com/products/javafx/) or use bundled JDK)

```bash
# Compile (adjust JavaFX path)
javac --module-path "C:\javafx-sdk-21\lib" --add-modules javafx.controls -d bin src/exceptions/*.java src/Student.java src/StudentManager.java src/StudentGUIView.java src/StudentGUIController.java src/AppGUI.java

# Run
java --module-path "C:\javafx-sdk-21\lib" --add-modules javafx.controls -cp bin AppGUI
```

See `JAVAFX_SETUP.md` for detailed setup instructions.

---

## Where Recursion is Used

This project includes **three recursive methods** that demonstrate different applications of recursion:

### 1. Recursive Search (`searchByLastNameRecursive`)
**Location**: [StudentManager.java](src/StudentManager.java#L132-L155)  
**Purpose**: Searches for students by last name by recursively traversing the ArrayList.  
**Why Recursion**: Demonstrates how recursion can replace iterative loops for list traversal. The base case occurs when reaching the end of the list, and the recursive case checks each student and continues to the next index.

### 2. Recursive Counting (`countStudentsAboveGPARecursive`)
**Location**: [StudentManager.java](src/StudentManager.java#L269-L298)  
**Purpose**: Counts students with GPA above a threshold using recursive computation.  
**Why Recursion**: Shows how recursion can be used for accumulation and counting. Each recursive call adds to the count and returns the sum of current and remaining elements.

### 3. Recursive Input Validation (`promptForMenuChoice`)
**Location**: [StudentView.java](src/StudentView.java#L137-L152)  
**Purpose**: Validates menu input by recursively re-prompting until valid input is received.  
**Why Recursion**: Demonstrates recursion for user input validation. Instead of using a while loop, the method calls itself when input is invalid, naturally continuing until a valid choice is entered.

All recursive methods include clear base cases to prevent infinite recursion and comments explaining their recursive logic.

---

## CSV File Persistence

This project includes **data persistence** functionality that allows saving and loading student records to/from CSV files:

### Save to CSV (`saveToCSV`)
**Location**: [StudentManager.java](src/StudentManager.java#L313-L330)  
**Purpose**: Exports all student records to a CSV file with header row.  
**What it demonstrates**: 
- File I/O using `BufferedWriter`
- Try-with-resources for automatic resource management
- CSV formatting and data serialization
- Exception handling for file operations

### Load from CSV (`loadFromCSV`)
**Location**: [StudentManager.java](src/StudentManager.java#L332-L395)  
**Purpose**: Imports student records from a CSV file, replacing current data.  
**What it demonstrates**:
- File reading using `BufferedReader`
- String parsing and data validation
- Robust error handling (skips malformed lines, continues processing)
- Data validation during import (GPA range checking)

### Import from CSV (`importFromCSV`)
**Location**: [StudentManager.java](src/StudentManager.java#L397-L450)  
**Purpose**: Appends student records from CSV without clearing existing data.  
**What it demonstrates**:
- Duplicate detection during bulk import
- Graceful error handling with detailed warnings
- Preserving data integrity during import operations

**CSV Format**:
```csv
StudentID,FirstName,LastName,GPA
S001,John,Doe,3.5
S002,Jane,Smith,3.8
```

**Why This Feature Matters**: Real-world applications require data persistence. This demonstrates understanding of file I/O, data serialization, error handling, and resource management—essential skills for practical software development.

### Sample Data on Startup

**Both console and GUI versions** prompt on startup:

**Console**: Text prompt `"Load sample data for testing? (yes/no)"`  
**GUI**: Confirmation dialog with OK/Cancel buttons

- **If yes/OK**: Application loads sample student data from [sample_data.csv](sample_data.csv), demonstrating the CSV loading functionality in action. If the file is not found, it gracefully falls back to hardcoded sample data.
- **If no/Cancel**: Application starts with an empty student list, allowing you to load your own CSV files or add students manually.

This provides user control and demonstrates good UX design. CSV files generated during testing (like saved exports) are automatically gitignored except for `sample_data.csv`.

---

## Additional Documentation

**JavaFX GUI** ([JAVAFX_SETUP.md](JAVAFX_SETUP.md)): Complete graphical user interface implementation with the same MVC architecture. Includes setup instructions, configuration details, and platform-specific guidance.

**Testing Suite** ([TESTING.md](TESTING.md)): Comprehensive JUnit 5 unit tests following TDD principles. Includes test execution commands, test architecture explanation, and coverage details for all 16 test cases (including CSV file persistence and sample data validation).

---

## Project Structure

```
src/
├── exceptions/                      # Modular exception hierarchy
│   ├── StudentException.java
│   ├── DuplicateStudentIdException.java
│   ├── InvalidGpaException.java
│   └── StudentNotFoundException.java
│
├── Student.java                     # Student model (immutable ID)
├── StudentManager.java              # Model: CRUD, recursion, statistics
│
├── App.java                         # Console entry point
├── StudentView.java                 # Console view layer
├── StudentController.java           # Console controller layer
│
├── AppGUI.java                      # GUI entry point
├── StudentGUIView.java             # GUI view layer (JavaFX)
└── StudentGUIController.java       # GUI controller layer

test/
└── StudentManagerTest.java         # JUnit 5 test suite (16 tests)

lib/
└── junit-platform-console-*.jar    # JUnit 5 testing framework

# Data Files
sample_data.csv                      # Sample student data (prompted on startup)
*.csv                                # Other CSV files gitignored (user data/testing)
```

**Note**: Only `sample_data.csv` is committed to the repository. Any CSV files you create (saves, exports, tests) are automatically gitignored to keep your workspace clean.

---

## Requirements Satisfied

**Core Requirements:**
- ArrayList data structure with protected access
- Multiple recursive methods (search, counting, validation)
- Comprehensive input validation
- Sorting (3 criteria) and searching (2 methods)
- 8+ functional features (now **10 features** with CSV save/load)
- Custom exception hierarchy
- File organization and package structure
- **JUnit test suite with 8+ test cases (16 tests total)**

**Advanced Features:**
- Complete MVC architecture (both versions)
- Two separate interfaces (Console + JavaFX GUI)
- Modular exception package
- Immutability and defensive programming
- Professional software engineering practices
- Comprehensive unit testing with 16 test cases
- **CSV file persistence (save/load/import functionality)**
- **Data serialization and robust error handling**
- **Try-with-resources for resource management**

---

This project demonstrates professional software development with clean architecture, proper separation of concerns, comprehensive feature implementation across two user interfaces, and practical data persistence capabilities.