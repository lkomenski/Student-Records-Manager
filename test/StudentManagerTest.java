import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import exceptions.DuplicateStudentIdException;
import exceptions.InvalidGpaException;
import exceptions.StudentException;
import exceptions.StudentNotFoundException;

/**
 * JUnit 5 Test Suite for StudentManager
 * Implements TDD principles (Red → Green → Refactor)
 * 
 * Tests all CRUD operations, exception handling, and business logic
 * without UI dependencies for isolated unit testing.
 * 
 * @author Leena Komenski
 */
class StudentManagerTest {
    
    private StudentManager manager;
    
    /**
     * Initialize fresh StudentManager instance before each test for isolation
     */
    @BeforeEach
    void setUp() {
        manager = new StudentManager();
    }
    
    // ==================== TEST CASE 1: Add Student Successfully ====================
    /**
     * Verify successful student addition to system
     */
    @Test
    @DisplayName("Test 1: Add student successfully")
    void testAddStudentSuccessfully() throws StudentException {
        // Arrange: Create a new student with valid data
        Student student = new Student("S001", "John", "Doe", 3.5);
        
        // Act: Add the student to the manager
        manager.addStudent(student);
        
        // Assert: Verify student was added
        assertEquals(1, manager.getStudentCount(), "Student count should be 1");
        assertNotNull(manager.findStudentById("S001"), "Student should be findable by ID");
        assertEquals("John", manager.findStudentById("S001").getFirstName(), 
                    "Student first name should match");
    }
    
    // ==================== TEST CASE 2: Add Duplicate ID Rejected ====================
    /**
     * Verify duplicate student IDs throw DuplicateStudentIdException
     */
    @Test
    @DisplayName("Test 2: Add duplicate ID rejected (expect DuplicateStudentIdException)")
    void testAddDuplicateIdRejected() throws StudentException {
        // Arrange: Add first student with ID "S001"
        Student student1 = new Student("S001", "John", "Doe", 3.5);
        manager.addStudent(student1);
        
        // Act & Assert: Try to add another student with same ID "S001"
        Student student2 = new Student("S001", "Jane", "Smith", 3.8);
        
        DuplicateStudentIdException exception = assertThrows(
            DuplicateStudentIdException.class,
            () -> manager.addStudent(student2),
            "Should throw DuplicateStudentIdException for duplicate ID"
        );
        
        // Verify exception message contains the duplicate ID
        assertTrue(exception.getMessage().contains("S001"), 
                  "Exception message should contain the duplicate ID");
        
        // Verify only one student exists
        assertEquals(1, manager.getStudentCount(), 
                    "Should still have only 1 student after duplicate rejection");
    }
    
    // ==================== TEST CASE 3: List Multiple Entries ====================
    /**
     * Verify retrieval of multiple students with correct size and contents
     */
    @Test
    @DisplayName("Test 3: List multiple entries (verify size + contents)")
    void testListMultipleEntries() throws StudentException {
        // Arrange: Add multiple students
        Student student1 = new Student("S001", "Alice", "Johnson", 3.9);
        Student student2 = new Student("S002", "Bob", "Williams", 3.5);
        Student student3 = new Student("S003", "Carol", "Brown", 3.7);
        
        manager.addStudent(student1);
        manager.addStudent(student2);
        manager.addStudent(student3);
        
        // Act: Get all students
        var allStudents = manager.getAllStudents();
        
        // Assert: Verify size
        assertEquals(3, allStudents.size(), "Should have 3 students in the list");
        assertEquals(3, manager.getStudentCount(), "Student count should be 3");
        
        // Assert: Verify contents - check that all students are present
        assertTrue(allStudents.contains(student1), "List should contain student1");
        assertTrue(allStudents.contains(student2), "List should contain student2");
        assertTrue(allStudents.contains(student3), "List should contain student3");
        
        // Assert: Verify specific IDs are findable
        assertNotNull(manager.findStudentById("S001"), "S001 should be findable");
        assertNotNull(manager.findStudentById("S002"), "S002 should be findable");
        assertNotNull(manager.findStudentById("S003"), "S003 should be findable");
    }
    
    // ==================== TEST CASE 4: Search Existing ID Returns Student ====================
    /**
     * Verify findStudentById returns correct Student object for existing ID
     */
    @Test
    @DisplayName("Test 4: Search existing ID returns student")
    void testSearchExistingIdReturnsStudent() throws StudentException {
        // Arrange: Add students
        Student student1 = new Student("S001", "David", "Miller", 3.2);
        Student student2 = new Student("S002", "Emma", "Davis", 3.6);
        
        manager.addStudent(student1);
        manager.addStudent(student2);
        
        // Act: Search for existing student
        Student found = manager.findStudentById("S002");
        
        // Assert: Verify correct student is returned
        assertNotNull(found, "Should find student with ID S002");
        assertEquals("S002", found.getStudentId(), "Student ID should match");
        assertEquals("Emma", found.getFirstName(), "First name should match");
        assertEquals("Davis", found.getLastName(), "Last name should match");
        assertEquals(3.6, found.getGpa(), 0.001, "GPA should match");
    }
    
    // ==================== TEST CASE 5: Search Missing ID Returns Null ====================
    /**
     * Verify findStudentById returns null for non-existent student ID
     */
    @Test
    @DisplayName("Test 5: Search missing ID returns null")
    void testSearchMissingIdReturnsNull() throws StudentException {
        // Arrange: Add one student
        Student student = new Student("S001", "Frank", "Wilson", 3.4);
        manager.addStudent(student);
        
        // Act: Search for non-existent student
        Student notFound = manager.findStudentById("S999");
        
        // Assert: Verify null is returned
        assertNull(notFound, "Should return null for non-existent student ID");
        
        // Act: Search for another non-existent ID
        Student alsoNotFound = manager.findStudentById("S100");
        
        // Assert: Verify null is returned
        assertNull(alsoNotFound, "Should return null for another non-existent ID");
    }
    
    // ==================== TEST CASE 6: Update Changes Name/GPA ====================
    /**
     * Verify updateStudent correctly modifies student name and GPA
     */
    @Test
    @DisplayName("Test 6: Update changes name/GPA")
    void testUpdateChangesNameAndGpa() throws StudentException {
        // Arrange: Add a student
        Student student = new Student("S001", "George", "Taylor", 3.0);
        manager.addStudent(student);
        
        // Act: Update first name
        manager.updateStudent("S001", "Gregory", null, null);
        
        // Assert: Verify first name changed
        Student updated = manager.findStudentById("S001");
        assertEquals("Gregory", updated.getFirstName(), "First name should be updated");
        assertEquals("Taylor", updated.getLastName(), "Last name should remain unchanged");
        assertEquals(3.0, updated.getGpa(), 0.001, "GPA should remain unchanged");
        
        // Act: Update last name and GPA
        manager.updateStudent("S001", null, "Thompson", 3.8);
        
        // Assert: Verify last name and GPA changed
        updated = manager.findStudentById("S001");
        assertEquals("Gregory", updated.getFirstName(), "First name should remain from previous update");
        assertEquals("Thompson", updated.getLastName(), "Last name should be updated");
        assertEquals(3.8, updated.getGpa(), 0.001, "GPA should be updated");
    }
    
    // ==================== TEST CASE 7: Remove Confirmed Deletes ====================
    /**
     * Verify removeStudent deletes student when confirmed=true
     */
    @Test
    @DisplayName("Test 7: Remove confirmed deletes")
    void testRemoveConfirmedDeletes() throws StudentException {
        // Arrange: Add multiple students
        Student student1 = new Student("S001", "Hannah", "Martinez", 3.7);
        Student student2 = new Student("S002", "Ian", "Garcia", 3.3);
        
        manager.addStudent(student1);
        manager.addStudent(student2);
        
        assertEquals(2, manager.getStudentCount(), "Should start with 2 students");
        
        // Act: Remove student with confirmed=true
        manager.removeStudent("S001", true);
        
        // Assert: Verify student was removed
        assertEquals(1, manager.getStudentCount(), "Should have 1 student after removal");
        assertNull(manager.findStudentById("S001"), "Removed student should not be findable");
        assertNotNull(manager.findStudentById("S002"), "Other student should still exist");
        
        // Act: Verify removal without confirmation throws exception
        StudentException exception = assertThrows(
            StudentException.class,
            () -> manager.removeStudent("S002", false),
            "Should throw exception when confirmed=false"
        );
        
        assertTrue(exception.getMessage().contains("confirmed"), 
                  "Exception should mention confirmation requirement");
        
        // Assert: Verify student was NOT removed
        assertEquals(1, manager.getStudentCount(), "Student count should still be 1");
        assertNotNull(manager.findStudentById("S002"), "Student should still exist");
    }
    
    // ==================== TEST CASE 8: Invalid GPA Rejected ====================
    /**
     * Verify InvalidGpaException thrown for GPA outside valid range (0.0-4.0)
     */
    @Test
    @DisplayName("Test 8: Invalid GPA rejected (expect InvalidGpaException)")
    void testInvalidGpaRejected() {
        // Test 1: GPA too high (> 4.0)
        Student invalidHigh = new Student("S001", "Jack", "Lopez", 4.5);
        
        InvalidGpaException exceptionHigh = assertThrows(
            InvalidGpaException.class,
            () -> manager.addStudent(invalidHigh),
            "Should throw InvalidGpaException for GPA > 4.0"
        );
        
        assertTrue(exceptionHigh.getMessage().contains("4.5"), 
                  "Exception should mention the invalid GPA value");
        
        // Test 2: GPA too low (< 0.0)
        Student invalidLow = new Student("S002", "Kate", "Lee", -0.5);
        
        InvalidGpaException exceptionLow = assertThrows(
            InvalidGpaException.class,
            () -> manager.addStudent(invalidLow),
            "Should throw InvalidGpaException for GPA < 0.0"
        );
        
        assertTrue(exceptionLow.getMessage().contains("-0.5"), 
                  "Exception should mention the invalid GPA value");
        
        // Verify no students were added
        assertEquals(0, manager.getStudentCount(), 
                    "No students should be added when GPA is invalid");
    }
    
    // ==================== TEST CASE 9: Menu Input Validation (Manual Test) ====================
    /**
     * TEST 9: Invalid menu input handling (letters when numbers expected)
     * 
     * NOTE: This requirement tests UI-layer input validation in App.java/StudentController,
     * which is outside the scope of StudentManager unit tests. This has been verified through
     * manual testing:
     * 
     * Manual Test Procedure:
     * 1. Run: java -cp bin App
     * 2. At main menu, enter "abc" instead of a number
     * 3. Expected: Program displays error message and re-prompts (does not crash)
     * 4. Result: PASSED - Scanner validation in StudentController prevents crash
     * 
     * The StudentController uses try-catch blocks and Scanner.hasNextInt() to validate
     * menu input, ensuring the application remains stable with invalid user input.
     * Unit tests focus on StudentManager business logic; UI input validation is 
     * integration-tested manually.
     */
    // No automated test for UI layer - documented manual verification above
    
    // ==================== BONUS TEST CASES ====================
    
    /**
     * Verify addStudentAutoId generates sequential IDs
     */
    @Test
    @DisplayName("Bonus: Auto-generate student ID")
    void testAutoGenerateStudentId() throws StudentException {
        // Act: Add students with auto-generated IDs
        Student student1 = manager.addStudentAutoId("Lisa", "Wang", 3.9);
        Student student2 = manager.addStudentAutoId("Mike", "Chen", 3.4);
        
        // Assert: Verify IDs were auto-generated in sequence
        assertEquals("S001", student1.getStudentId(), "First auto-ID should be S001");
        assertEquals("S002", student2.getStudentId(), "Second auto-ID should be S002");
        assertEquals(2, manager.getStudentCount(), "Should have 2 students");
    }
    
    /**
     * Verify recursive search by last name returns all matching students
     */
    @Test
    @DisplayName("Bonus: Search by last name (recursive)")
    void testSearchByLastName() throws StudentException {
        // Arrange: Add students with some shared last names
        manager.addStudent(new Student("S001", "Nancy", "Smith", 3.5));
        manager.addStudent(new Student("S002", "Oscar", "Johnson", 3.6));
        manager.addStudent(new Student("S003", "Paul", "Smith", 3.7));
        
        // Act: Search for "Smith"
        var results = manager.searchByLastName("Smith");
        
        // Assert: Should find 2 students
        assertEquals(2, results.size(), "Should find 2 students with last name Smith");
        assertTrue(results.stream().anyMatch(s -> s.getFirstName().equals("Nancy")),
                  "Results should include Nancy");
        assertTrue(results.stream().anyMatch(s -> s.getFirstName().equals("Paul")),
                  "Results should include Paul");
    }
    
    /**
     * Verify updateStudent throws StudentNotFoundException for non-existent ID
     */
    @Test
    @DisplayName("Bonus: Update non-existent student throws StudentNotFoundException")
    void testUpdateNonExistentStudent() {
        // Act & Assert: Try to update non-existent student
        StudentNotFoundException exception = assertThrows(
            StudentNotFoundException.class,
            () -> manager.updateStudent("S999", "New", "Name", 3.5),
            "Should throw StudentNotFoundException"
        );
        
        assertTrue(exception.getMessage().contains("S999"),
                  "Exception should mention the student ID");
    }
    
    /**
     * Verify removeStudent throws StudentNotFoundException for non-existent ID
     */
    @Test
    @DisplayName("Bonus: Remove non-existent student throws StudentNotFoundException")
    void testRemoveNonExistentStudent() {
        // Act & Assert: Try to remove non-existent student
        StudentNotFoundException exception = assertThrows(
            StudentNotFoundException.class,
            () -> manager.removeStudent("S999", true),
            "Should throw StudentNotFoundException"
        );
        
        assertTrue(exception.getMessage().contains("S999"),
                  "Exception should mention the student ID");
    }
}
