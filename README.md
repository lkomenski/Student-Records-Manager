# Student Records Manager

A Java console application for managing student records.  
This project demonstrates Object-Oriented Programming, `ArrayList` usage, recursion, input validation, sorting, and basic statistical calculations.

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

### Option 1 – Command Line
```bash
javac *.java
java App
```

### Option 2 – VS Code
Open `App.java` and press **Run**.

---

## Menu Options

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

- `Student.java` – Student model (encapsulation, getters/setters)
- `StudentManager.java` – CRUD, sorting, recursion, statistics
- `App.java` – Menu system and user interaction

---

This project satisfies all assignment requirements, including use of ArrayList, recursion, input validation, sorting, searching, and at least five functional features.