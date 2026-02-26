package service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import model.Student;
import manager.StudentManager;
import exceptions.DuplicateStudentIdException;
import exceptions.InvalidGpaException;

/**
 * StudentDataService - Data Persistence Layer
 * 
 * Handles all file I/O operations for student data:
 * - CSV file saving (export)
 * - CSV file loading (import/replace)
 * - CSV file importing (append)
 * - Data validation during import
 * 
 * Separates data persistence concerns from business logic,
 * following Single Responsibility Principle.
 * 
 * @author Leena Komenski
 */
public class StudentDataService {
    
    /**
     * Saves all student records from a StudentManager to a CSV file.
     * CSV Format: StudentID,FirstName,LastName,GPA
     * 
     * @param manager The StudentManager containing students to save
     * @param filename The name of the file to save to
     * @throws IOException if file writing fails
     */
    public void saveToCSV(StudentManager manager, String filename) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            // Write CSV header
            writer.write("StudentID,FirstName,LastName,GPA");
            writer.newLine();
            
            // Write each student record
            for (Student student : manager.getAllStudents()) {
                String line = String.format("%s,%s,%s,%.2f",
                    student.getStudentId(),
                    student.getFirstName(),
                    student.getLastName(),
                    student.getGpa());
                writer.write(line);
                writer.newLine();
            }
        }
        // BufferedWriter is auto-closed by try-with-resources
    }

    /**
     * Loads student records from a CSV file into a StudentManager.
     * CSV Format: StudentID,FirstName,LastName,GPA
     * 
     * Note: This method CLEARS existing students in the manager and replaces with loaded data.
     * Skips invalid lines and continues processing.
     * 
     * @param manager The StudentManager to load students into
     * @param filename The name of the file to load from
     * @return Number of students successfully loaded
     * @throws IOException if file reading fails
     */
    public int loadFromCSV(StudentManager manager, String filename) throws IOException {
        int lineNumber = 0;
        int successCount = 0;
        int errorCount = 0;
        
        // Temporary list to hold loaded students
        java.util.ArrayList<Student> loadedStudents = new java.util.ArrayList<>();
        
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            
            // Read and skip header line
            line = reader.readLine();
            lineNumber++;
            
            // Read each student record
            while ((line = reader.readLine()) != null) {
                lineNumber++;
                
                // Skip empty lines
                if (line.trim().isEmpty()) {
                    continue;
                }
                
                try {
                    // Parse CSV line
                    String[] parts = line.split(",");
                    
                    if (parts.length != 4) {
                        System.err.println("Warning: Line " + lineNumber + " has invalid format (expected 4 fields): " + line);
                        errorCount++;
                        continue;
                    }
                    
                    String studentId = parts[0].trim();
                    String firstName = parts[1].trim();
                    String lastName = parts[2].trim();
                    double gpa = Double.parseDouble(parts[3].trim());
                    
                    // Validate GPA range
                    if (gpa < 0.0 || gpa > 4.0) {
                        System.err.println("Warning: Line " + lineNumber + " has invalid GPA (must be 0.0-4.0): " + gpa);
                        errorCount++;
                        continue;
                    }
                    
                    // Create and add student to temporary list
                    Student student = new Student(studentId, firstName, lastName, gpa);
                    loadedStudents.add(student);
                    successCount++;
                    
                } catch (NumberFormatException e) {
                    System.err.println("Warning: Line " + lineNumber + " has invalid GPA format: " + line);
                    errorCount++;
                } catch (Exception e) {
                    System.err.println("Warning: Line " + lineNumber + " could not be processed: " + e.getMessage());
                    errorCount++;
                }
            }
        }
        
        // Clear manager and add loaded students (only if at least one was successfully loaded)
        if (successCount > 0) {
            manager.clearAllStudents();
            for (Student student : loadedStudents) {
                try {
                    manager.addStudent(student);
                } catch (Exception e) {
                    // This shouldn't happen since we validated, but handle gracefully
                    System.err.println("Warning: Could not add student " + student.getStudentId() + ": " + e.getMessage());
                }
            }
        }
        
        if (errorCount > 0) {
            System.out.println("\nLoaded " + successCount + " students with " + errorCount + " errors/warnings.");
        }
        
        return successCount;
    }

    /**
     * Appends student records from a CSV file to existing records in StudentManager.
     * Unlike loadFromCSV, this does NOT clear existing students.
     * Skips duplicate IDs and invalid data.
     * 
     * @param manager The StudentManager to import students into
     * @param filename The name of the file to import from
     * @return Number of students successfully imported
     * @throws IOException if file reading fails
     */
    public int importFromCSV(StudentManager manager, String filename) throws IOException {
        int lineNumber = 0;
        int successCount = 0;
        int errorCount = 0;
        
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            
            // Read and skip header line
            line = reader.readLine();
            lineNumber++;
            
            // Read each student record
            while ((line = reader.readLine()) != null) {
                lineNumber++;
                
                // Skip empty lines
                if (line.trim().isEmpty()) {
                    continue;
                }
                
                try {
                    // Parse CSV line
                    String[] parts = line.split(",");
                    
                    if (parts.length != 4) {
                        System.err.println("Warning: Line " + lineNumber + " has invalid format: " + line);
                        errorCount++;
                        continue;
                    }
                    
                    String studentId = parts[0].trim();
                    String firstName = parts[1].trim();
                    String lastName = parts[2].trim();
                    double gpa = Double.parseDouble(parts[3].trim());
                    
                    // Create student and attempt to add (handles validation)
                    Student student = new Student(studentId, firstName, lastName, gpa);
                    manager.addStudent(student);
                    successCount++;
                    
                } catch (DuplicateStudentIdException e) {
                    System.err.println("Warning: Line " + lineNumber + " - Duplicate ID skipped: " + e.getMessage());
                    errorCount++;
                } catch (InvalidGpaException e) {
                    System.err.println("Warning: Line " + lineNumber + " - Invalid GPA: " + e.getMessage());
                    errorCount++;
                } catch (NumberFormatException e) {
                    System.err.println("Warning: Line " + lineNumber + " - Invalid number format: " + line);
                    errorCount++;
                } catch (Exception e) {
                    System.err.println("Warning: Line " + lineNumber + " - Error: " + e.getMessage());
                    errorCount++;
                }
            }
        }
        
        if (errorCount > 0) {
            System.out.println("\nImported " + successCount + " students with " + errorCount + " errors/warnings.");
        }
        
        return successCount;
    }
}
