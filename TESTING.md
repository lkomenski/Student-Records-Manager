# Testing Guide for Student Records Manager

## Test-Driven Development (TDD) Implementation

This project follows TDD principles: **Red → Green → Refactor**

The `StudentManager` class is unit-tested without UI dependencies, making it ideal for isolated unit testing and TDD workflow.

---

## Test Suite Overview

**Location**: `test/StudentManagerTest.java`  
**Framework**: JUnit 5 (Jupiter)  
**Total Tests**: 12 automated unit tests + 1 documented manual test  
**Coverage**: All CRUD operations, exception handling, and business logic

### Required Test Cases (8)

1. **Add student successfully** - Validates student addition to system
2. **Add duplicate ID rejected** - Verifies `DuplicateStudentIdException` is thrown
3. **List multiple entries** - Confirms all students are stored and retrievable
4. **Search existing ID returns student** - Tests `findStudentById()` functionality
5. **Search missing ID returns null** - Ensures proper null handling
6. **Update changes name/GPA** - Verifies `updateStudent()` modifies data correctly
7. **Remove confirmed deletes** - Tests `removeStudent()` with confirmation
8. **Invalid GPA rejected** - Ensures `InvalidGpaException` for out-of-range values
9. **Invalid menu input handling** - Manual test (documented in test file) verifying UI-layer input validation prevents crashes

### Bonus Test Cases (4)

9. **Auto-generate student ID** - Tests `addStudentAutoId()` functionality
10. **Search by last name (recursive)** - Validates recursive search algorithm
11. **Update non-existent student** - Verifies `StudentNotFoundException` on update
12. **Remove non-existent student** - Verifies `StudentNotFoundException` on remove

---

## Running Tests

### Prerequisites
- JUnit 5 library: `lib/junit-platform-console-standalone-1.10.1.jar`
- Java JDK 11+

### Compile Tests

```bash
javac -cp "lib/junit-platform-console-standalone-1.10.1.jar;bin" -d bin test/StudentManagerTest.java
```

### Execute Tests

```bash
java -jar lib/junit-platform-console-standalone-1.10.1.jar execute --class-path bin --scan-class-path
```

### Expected Output

```
Test run finished after ~50 ms
[        12 tests found           ]
[        12 tests successful      ]
[         0 tests failed          ]
```

---

## Test Architecture

### Test Structure Pattern

All tests follow the **Arrange-Act-Assert** pattern:

```java
@Test
@DisplayName("Descriptive test name")
void testMethodName() throws StudentException {
    // Arrange: Set up test data
    Student student = new Student("S001", "John", "Doe", 3.5);
    
    // Act: Execute method under test
    manager.addStudent(student);
    
    // Assert: Verify expected outcomes
    assertEquals(1, manager.getStudentCount());
    assertNotNull(manager.findStudentById("S001"));
}
```

### Test Isolation

- Each test uses `@BeforeEach` to instantiate a fresh `StudentManager`
- Ensures test independence and eliminates side effects
- Guarantees predictable, repeatable outcomes

---

## Benefits of Testing StudentManager

1. **No UI dependencies** - Pure business logic testing
2. **Fast execution** - Complete suite runs in ~50ms
3. **High confidence** - Comprehensive CRUD operation coverage
4. **Regression prevention** - Catches bugs during refactoring
5. **Living documentation** - Tests demonstrate usage patterns

---

## TDD Workflow

### 1. Red - Write Failing Test
```java
@Test
void testNewFeature() {
    assertEquals(expected, manager.newFeature());  // Fails - method doesn't exist
}
```

### 2. Green - Implement Minimal Solution
```java
public Object newFeature() {
    return expected;  // Simplest implementation to pass
}
```

### 3. Refactor - Improve While Maintaining Tests
```java
public Object newFeature() {
    // Enhanced implementation with same behavior
}
```

---

## Continuous Integration

Tests should be executed:
- Before committing code changes
- After modifying `StudentManager.java`
- During code reviews
- Before project submission

---

**Author**: Leena Komenski  
**Framework**: JUnit 5 (Jupiter)  
**Test Coverage**: 100% of StudentManager CRUD operations
