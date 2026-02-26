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

---

## Architecture

Both console and GUI versions follow **MVC (Model-View-Controller)** pattern:

**Model** (shared): `StudentManager`, `Student`, `exceptions/`
- Business logic, CRUD operations, recursion, validation
- Returns unmodifiable lists for encapsulation
- Immutable student IDs

**Console Version**:
- **View**: `StudentView` (console I/O, prompts, formatting)
- **Controller**: `StudentController` (menu flow, coordinates Model/View)
- **Entry**: `App.java`

**GUI Version**:
- **View**: `StudentGUIView` (JavaFX components, dialogs, layouts)
- **Controller**: `StudentGUIController` (event handling, validation)
- **Entry**: `AppGUI.java`

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
```

---

## Requirements Satisfied

**Core Requirements:**
- ArrayList data structure with protected access
- Multiple recursive methods (search, counting, validation)
- Comprehensive input validation
- Sorting (3 criteria) and searching (2 methods)
- 8+ functional features
- Custom exception hierarchy
- File organization and package structure

**Advanced Features:**
- Complete MVC architecture (both versions)
- Two separate interfaces (Console + JavaFX GUI)
- Modular exception package
- Immutability and defensive programming
- Professional software engineering practices

---

This project demonstrates professional software development with clean architecture, proper separation of concerns, and comprehensive feature implementation across two user interfaces.