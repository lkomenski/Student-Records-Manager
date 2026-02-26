# JavaFX Setup Guide

## What You Have
- **AppGUI.java** - Complete JavaFX GUI with MVC architecture
- **StudentGUIView.java** - JavaFX UI components (View layer)
- **StudentGUIController.java** - Event handlers (Controller layer)
- All business logic preserved (Student, StudentManager, exceptions package)
- All comments and recursive methods intact  

## What You Need
JavaFX SDK - VS Code Extension for Java handles this automatically when you click Run

---

## Setup Options

### Option 1: VS Code Run Button (Easiest - Recommended)

1. Open `AppGUI.java` in VS Code
2. Click the "Run" button above the `public static void main` method
3. VS Code automatically:
   - Downloads JavaFX dependencies if needed
   - Compiles to `bin/` directory (keeps `src/` clean)
   - Runs the application

**Advantages:**
- No manual JavaFX setup required
- Automatically uses `-d bin` (proper compilation)
- Simplifies dependency management

---

### Option 2: Use Java with Bundled JavaFX
Some JDK distributions include JavaFX:
- **Azul Zulu FX** - https://www.azul.com/downloads/?package=jdk-fx
- **Liberica Full JDK** - https://bell-sw.com/pages/downloads/

Download and install one of these, then compile from project root:
```bash
javac -d bin src/AppGUI.java src/Student.java src/StudentManager.java src/StudentGUIView.java src/StudentGUIController.java src/exceptions/*.java
java -cp bin AppGUI
```

---

### Option 3: Download JavaFX SDK Separately
1. Download JavaFX SDK: https://gluonhq.com/products/javafx/
2. Extract to a folder (e.g., `C:\javafx-sdk-21`)
3. Compile from project root with module path:

```bash
javac -d bin --module-path "C:\javafx-sdk-21\lib" --add-modules javafx.controls src/AppGUI.java src/Student.java src/StudentManager.java src/StudentGUIView.java src/StudentGUIController.java src/exceptions/*.java
java -cp bin --module-path "C:\javafx-sdk-21\lib" --add-modules javafx.controls AppGUI
```

**Important:** Always use `-d bin` flag to compile to the bin/ directory, not src/

---

## Verification
Once JavaFX is set up, you should see:
- Window with "STUDENT RECORDS MANAGER" title
- Table showing 3 sample students
- 8 action buttons on the right
- Status bar at bottom

---

## Features in GUI Version
All console features are preserved:
- Add Student (auto-generated ID)
- Search by ID
- Search by Last Name (RECURSIVE)
- Update Student
- Remove Student (with confirmation)
- Sort (ID, Name, GPA)
- Statistics (with RECURSIVE GPA counting)
- Full input validation
- Error handling with dialogs

---

## Alternative: Console Version
The console version (`App.java`) provides the same functionality without requiring JavaFX.

From project root directory:
```bash
javac -d bin src/App.java src/Student.java src/StudentManager.java src/StudentView.java src/StudentController.java src/exceptions/*.java
java -cp bin App
```

---

## Avoiding .class Files in src/

**Always compile from the project root with `-d bin`:**
```bash
# Good - compiles to bin/
javac -d bin src/App.java src/Student.java ...

# Bad - creates .class files in src/
cd src
javac App.java Student.java ...
```

The `-d bin` flag tells javac to put compiled files in bin/ and automatically creates subdirectories like bin/exceptions/.
