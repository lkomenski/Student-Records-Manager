# Student Records Manager

A comprehensive Java application for managing student records, available in both **Console** and **JavaFX GUI** versions.  
This project demonstrates Object-Oriented Programming, `ArrayList` usage, recursion, input validation, sorting, and basic statistical calculations.

---

## 🎯 Two Versions Available

1. **Console Version** (`App.java`) - Text-based menu interface
2. **JavaFX GUI Version** (`AppGUI.java`) - Modern graphical interface with tables, buttons, and dialogs

Both versions share the same core logic and preserve all functionality, comments, and recursive algorithms.

---

## Features

- Add a student (duplicate ID prevention)
- List all students
- Search by ID
- Search by last name (**recursive search**)
- Update student information
- Remove a student
- Sort by ID, last name, or GPA
- View statistics (average GPA, highest GPA, recursive GPA count)

---

## Concepts Demonstrated

- **OOP** – Separate `Student`, `StudentManager`, and `App` classes  
- **ArrayList** – Dynamic student storage  
- **Recursion** –  
  - Recursive last name search  
  - Recursive GPA counting  
  - Recursive menu validation  
- **Input Validation** – Prevents invalid GPA, duplicate IDs, and invalid menu input  
- **Sorting & Searching Algorithms**

---

## How to Run

### Console Version (App.java)

#### Option 1 – Command Line
```bash
cd src
javac *.java
java App
```

#### Option 2 – VS Code
Open `App.java` and press **Run** (F5).

---

### JavaFX GUI Version (AppGUI.java)

#### Prerequisites
- JavaFX SDK installed (bundled with Java 11+ or download separately)

#### Option 1 – Command Line
```bash
cd src
javac --module-path "path/to/javafx-sdk/lib" --add-modules javafx.controls *.java
java --module-path "path/to/javafx-sdk/lib" --add-modules javafx.controls AppGUI
```

#### Option 2 – VS Code
1. Open `AppGUI.java`
2. Configure JavaFX in your launch.json (add VM arguments for module path)
3. Press **Run** (F5)

#### Option 3 – Using Maven/Gradle
Create a build file with JavaFX dependencies and run:
```bash
mvn javafx:run  # or
gradle run
```

---

## Menu Options (Console Version)

```
1. Add a new student
2. List all students
3. Search student by ID
4. Search students by Last Name (Recursive)
5. Update student information
6. Remove a student
7. Sort students
8. View statistics
9. Quit
```

---

## Test Cases

1. Add valid student → student appears in list  
2. Add duplicate ID → error message  
3. Enter invalid GPA → re-prompt  
4. Search existing ID → correct record returned  
5. Search by last name → recursive results displayed  
6. Update GPA → changes reflected  
7. Sort by GPA → correctly ordered list  
8. View statistics → accurate average and recursive GPA count  
9. Remove student → student deleted  
10. Enter invalid menu input → recursive re-prompt  

---

## Project Structure

- `Student.java` – Student model (encapsulation, getters/setters, Comparable interface, static Comparators)
- `StudentManager.java` – CRUD operations, sorting, recursion, statistics  
- `StudentException.java` – Custom exception classes for error handling
- `App.java` – **Console version** with menu system and text-based user interaction
- `AppGUI.java` – **JavaFX GUI version** with tables, buttons, dialogs, and modern interface

---

## GUI Features

The JavaFX version (`AppGUI.java`) includes:
- ✅ **TableView** displaying all students with sortable columns
- ✅ **Add Student Dialog** with auto-generated IDs and validation
- ✅ **Search Dialogs** for ID and last name (uses recursive search)
- ✅ **Update Dialog** pre-filled with current student data
- ✅ **Remove Confirmation** dialog for safe deletion
- ✅ **Sort Options** dialog with multiple criteria
- ✅ **Statistics Window** showing recursive GPA counts
- ✅ **Status Bar** for feedback messages
- ✅ **Error Handling** with user-friendly alert dialogs

All recursive methods, validation logic, and functionality from the console version are preserved in the GUI.

---

This project satisfies all assignment requirements, including use of ArrayList, recursion, input validation, sorting, searching, and at least five functional features. **Bonus: Complete JavaFX GUI implementation.**