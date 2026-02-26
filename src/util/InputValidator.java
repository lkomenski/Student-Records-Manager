package util;

/**
 * InputValidator - Input Validation Utility
 * 
 * Provides reusable validation methods for user input:
 * - GPA range validation (0.0-4.0)
 * - Non-empty string validation
 * - Numeric parsing with error handling
 * - Range checking for integers and doubles
 * 
 * Centralizes validation logic to avoid duplication across
 * View and Controller classes.
 * 
 * @author Leena Komenski
 */
public class InputValidator {
    
    // Constants for validation
    public static final double MIN_GPA = 0.0;
    public static final double MAX_GPA = 4.0;
    
    /**
     * Validates that a GPA is within the acceptable range (0.0 - 4.0).
     * 
     * @param gpa The GPA value to validate
     * @return true if valid, false otherwise
     */
    public static boolean isValidGPA(double gpa) {
        return gpa >= MIN_GPA && gpa <= MAX_GPA;
    }
    
    /**
     * Validates that a string is not null or empty after trimming.
     * 
     * @param input The string to validate
     * @return true if not empty, false otherwise
     */
    public static boolean isNonEmpty(String input) {
        return input != null && !input.trim().isEmpty();
    }
    
    /**
     * Parses a string to double with error handling.
     * 
     * @param input The string to parse
     * @return The parsed double value, or null if parsing fails
     */
    public static Double parseDouble(String input) {
        if (input == null || input.trim().isEmpty()) {
            return null;
        }
        
        try {
            return Double.parseDouble(input.trim());
        } catch (NumberFormatException e) {
            return null;
        }
    }
    
    /**
     * Parses a string to double and validates it's within a range.
     * 
     * @param input The string to parse
     * @param min Minimum acceptable value (inclusive)
     * @param max Maximum acceptable value (inclusive)
     * @return The parsed double value if valid, null otherwise
     */
    public static Double parseDoubleInRange(String input, double min, double max) {
        Double value = parseDouble(input);
        
        if (value != null && value >= min && value <= max) {
            return value;
        }
        
        return null;
    }
    
    /**
     * Parses a string to integer with error handling.
     * 
     * @param input The string to parse
     * @return The parsed integer value, or null if parsing fails
     */
    public static Integer parseInt(String input) {
        if (input == null || input.trim().isEmpty()) {
            return null;
        }
        
        try {
            return Integer.parseInt(input.trim());
        } catch (NumberFormatException e) {
            return null;
        }
    }
    
    /**
     * Parses a string to integer and validates it's within a range.
     * 
     * @param input The string to parse
     * @param min Minimum acceptable value (inclusive)
     * @param max Maximum acceptable value (inclusive)
     * @return The parsed integer value if valid, null otherwise
     */
    public static Integer parseIntInRange(String input, int min, int max) {
        Integer value = parseInt(input);
        
        if (value != null && value >= min && value <= max) {
            return value;
        }
        
        return null;
    }
    
    /**
     * Validates GPA and returns a user-friendly error message if invalid.
     * 
     * @param gpa The GPA to validate
     * @return Error message if invalid, null if valid
     */
    public static String getGPAErrorMessage(double gpa) {
        if (!isValidGPA(gpa)) {
            return "GPA must be between " + MIN_GPA + " and " + MAX_GPA + "!";
        }
        return null;
    }
    
    /**
     * Validates that a string is non-empty and returns error message if invalid.
     * 
     * @param input The string to validate
     * @param fieldName The name of the field (for error message)
     * @return Error message if invalid, null if valid
     */
    public static String getNonEmptyErrorMessage(String input, String fieldName) {
        if (!isNonEmpty(input)) {
            return fieldName + " cannot be empty!";
        }
        return null;
    }
}
