# Student Records Manager

A comprehensive Java console application for managing student records, demonstrating core programming concepts including Object-Oriented Programming, data structures, recursion, and algorithm development.

## Project Description

Student Records Manager is a menu-driven console application that allows users to store, manage, and analyze student records. The system provides full CRUD (Create, Read, Update, Delete) functionality along with advanced features like sorting, searching, and statistical analysis. This project showcases fundamental computer science concepts including:

- **Object-Oriented Programming (OOP)**: Uses classes and objects with proper encapsulation
- **Data Structures**: Implements ArrayList for dynamic data storage
- **Recursion**: Includes multiple recursive algorithms for search and computation
- **Input Validation**: Comprehensive error handling and user input validation
- **Algorithm Development**: Sorting, searching, and statistical computations

## Features Implemented

The application includes **8 major features**, exceeding the minimum requirement:

1. ✅ **Add Student** - Add new students with unique ID validation
2. ✅ **List All Students** - Display all students in a formatted table
3. ✅ **Search by ID** - Find a specific student by their unique ID
4. ✅ **Search by Last Name** - Find students using **recursive search algorithm**
5. ✅ **Update Student** - Modify student information with validation
6. ✅ **Remove Student** - Delete student records with confirmation
7. ✅ **Sort Students** - Sort by ID, Last Name, or GPA
8. ✅ **Statistics** - Calculate average GPA, highest GPA, and GPA distribution using **recursive counting**

## How to Run the Program

### Prerequisites
- Java Development Kit (JDK) 8 or higher
- VS Code with Java Extension Pack (optional but recommended)

### Compilation and Execution

#### Option 1: Using Command Line
```bash
# Navigate to the project directory
cd Student-Records-Manager

# Compile all Java files
javac -d bin src/*.java

# Run the program
java -cp bin App
```

#### Option 2: Using VS Code
1. Open the project folder in VS Code
2. Open `src/App.java`
3. Press `F5` or click "Run" in the top menu
4. The program will start in the integrated terminal

#### Option 3: Direct Execution
```bash
cd src
javac *.java
java App
```

## Usage Instructions

Upon starting the application, you'll see a menu with 9 options:

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

**Sample Data**: The program automatically loads 3 sample students for testing purposes.

### Example Workflow:
1. View existing students (option 2)
2. Add a new student (option 1)
3. Search for a student (options 3 or 4)
4. Update student information (option 5)
5. View statistics (option 8)
6. Sort students by GPA (option 7)

## Recursion Implementation

This project includes **THREE recursive methods**, exceeding the minimum requirement:

### 1. Recursive Menu Validation (`getValidMenuChoiceRecursive()`)
**Location**: [App.java](src/App.java)  
**Purpose**: Validates user menu input by recursively re-prompting until valid input is received  
**Why Recursion**: Demonstrates recursion for input validation. Continues calling itself until the base case (valid input) is met.

```java
// Base case: User enters valid integer 1-9
// Recursive case: Invalid input, re-prompt user
private static int getValidMenuChoiceRecursive()
```

### 2. Recursive Last Name Search (`searchByLastNameRecursive()`)
**Location**: [StudentManager.java](src/StudentManager.java)  
**Purpose**: Searches through the student list recursively to find all students matching a last name  
**Why Recursion**: Educational demonstration of recursive list traversal, showing how recursion can replace iteration.

```java
// Base case: Reached end of list
// Recursive case: Check current student, recurse to next index
public ArrayList<Student> searchByLastNameRecursive(String lastName, int index, ArrayList<Student> results)
```

### 3. Recursive GPA Counter (`countStudentsAboveGPARecursive()`)
**Location**: [StudentManager.java](src/StudentManager.java)  
**Purpose**: Counts students with GPA above a threshold using recursive computation  
**Why Recursion**: Demonstrates recursive counting/accumulation, used for generating GPA distribution statistics.

```java
// Base case: Reached end of list, return 0
// Recursive case: Add 1 if student qualifies, continue counting
public int countStudentsAboveGPARecursive(double threshold, int index)
```

## Input Validation Features

The program includes comprehensive input validation:

- ✅ **Duplicate ID Prevention** - Checks for existing IDs before adding
- ✅ **Empty Field Validation** - Ensures no required fields are left empty
- ✅ **Numeric Validation** - Validates GPA is a number between 0.0-4.0
- ✅ **Menu Choice Validation** - Recursive validation ensures only 1-9 is accepted
- ✅ **Confirmation Prompts** - Asks for confirmation before deleting records

## Project Structure

```
Student-Records-Manager/
├── src/
│   ├── App.java              # Main application with menu system
│   ├── Student.java          # Student data model (OOP)
│   └── StudentManager.java   # Business logic and data management
├── lib/                      # External libraries (if needed)
├── bin/                      # Compiled .class files
└── README.md                 # This file
```

### Class Descriptions

**Student.java**
- Represents a student record with ID, first name, last name, and GPA
- Demonstrates encapsulation with private fields and public getters/setters
- Includes `toString()` for formatted output

**StudentManager.java**
- Manages ArrayList of Student objects
- Implements all CRUD operations
- Contains recursive algorithms for search and counting
- Provides sorting and statistical computations

**App.java**
- Entry point of the application
- Implements menu-driven user interface
- Handles user input and validation
- Delegates operations to StudentManager

## Testing Guide

Here are **8 comprehensive test cases** to validate the program:

### Test Case 1: Add Student with Valid Data
- **Action**: Add student with ID "S004", Name "Bob Wilson", GPA 3.7
- **Expected**: Student added successfully, appears in list

### Test Case 2: Duplicate ID Prevention
- **Action**: Try to add student with ID "S001" (already exists)
- **Expected**: Error message, prevents duplicate

### Test Case 3: Invalid GPA Validation
- **Action**: Try to enter GPA of 5.0 or -1.0
- **Expected**: Error message, re-prompts for valid GPA (0.0-4.0)

### Test Case 4: Search by ID (Existing)
- **Action**: Search for student ID "S002"
- **Expected**: Displays Jane Smith's information

### Test Case 5: Recursive Last Name Search
- **Action**: Search by last name "Smith"
- **Expected**: Shows all students with last name Smith (tests recursion)

### Test Case 6: Update Student Information
- **Action**: Update S003's GPA from 3.2 to 3.9
- **Expected**: Record updated, new GPA displayed

### Test Case 7: Sort Functionality
- **Action**: Sort students by GPA (highest first)
- **Expected**: Students displayed in descending GPA order

### Test Case 8: Recursive Statistics
- **Action**: View statistics (option 8)
- **Expected**: Shows accurate count of students above various GPA thresholds using recursive method

### Test Case 9: Remove Student
- **Action**: Remove student S003, confirm deletion
- **Expected**: Student removed, no longer appears in list

### Test Case 10: Invalid Menu Input
- **Action**: Enter "abc" or "15" at menu prompt
- **Expected**: Recursive validation continues prompting until valid input

## Learning Objectives Demonstrated

✅ **Problem-Solving**: Menu-driven interface with multiple operations  
✅ **Algorithm Development**: Search, sort, and statistical algorithms  
✅ **Object-Oriented Programming**: Classes, objects, encapsulation, methods  
✅ **Data Structures**: ArrayList for dynamic student storage  
✅ **Recursion**: Three different recursive implementations  
✅ **Testing & Debugging**: Comprehensive input validation and error handling  
✅ **Software Design**: Separation of concerns (Model, Manager, UI)

## Technical Specifications

- **Language**: Java (JDK 8+)
- **Data Structure**: `ArrayList<Student>`
- **Input/Output**: Console-based using `Scanner` and `System.out`
- **Design Pattern**: MVC-inspired separation (Model, Manager, Controller)
- **Code Style**: Well-documented with JavaDoc comments

## Future Enhancements

Potential features for future versions:
- File persistence (save/load from file)
- More advanced search filters
- Grade calculation with multiple courses
- Export to CSV format
- GUI interface using JavaFX

## Author

Course Project - Demonstrating Fundamental Programming Concepts

## Course Objectives Met

This project successfully demonstrates all required course skills:
- ✅ Problem-solving and algorithm development
- ✅ Object-oriented programming with Java
- ✅ Data structure implementation (ArrayList)
- ✅ Recursive algorithm design
- ✅ Testing and debugging practices
- ✅ Software design principles

---

**Note**: This project is designed as an educational demonstration of core computer science concepts and programming fundamentals.
