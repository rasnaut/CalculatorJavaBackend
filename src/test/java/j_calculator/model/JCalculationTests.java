package j_calculator.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class JCalculationTests {

    @Test
    void testAdd() {
        Calculation calc = new Calculation(5.0, 3.0, "+");
        assertEquals(8.0, calc.getResult(), 0.001, "Addition operation failed");
    }

    @Test
    void testSubtract() {
        Calculation calc = new Calculation(5.0, 3.0, "-");
        assertEquals(2.0, calc.getResult(), 0.001, "Subtraction operation failed");
    }

    @Test
    void testMultiply() {
        Calculation calc = new Calculation(5.0, 3.0, "*");
        assertEquals(15.0, calc.getResult(), 0.001, "Multiplication operation failed");
    }

    @Test
    void testDivide() {
        Calculation calc = new Calculation(10.0, 2.0, "/");
        assertEquals(5.0, calc.getResult(), 0.001, "Division operation failed");
    }

    @Test
    void testDivideByZero() {
        ArithmeticException exception = assertThrows(ArithmeticException.class, () -> {
            new Calculation(10.0, 0.0, "/");
        }, "Expected division by zero to throw, but it didn't");
        assertEquals("Division by zero", exception.getMessage(), "Incorrect exception message");
    }

    @Test
    void testInvalidOperation() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new Calculation(5.0, 3.0, "^"); // Операция ^ не поддерживается
        }, "Expected invalid operation to throw, but it didn't");
        assertEquals("Invalid operation: ^", exception.getMessage(), "Incorrect exception message");
    }

    @Test
    void testAddWithNegativeNumbers() {
        Calculation calc = new Calculation(-5.0, -3.0, "+");
        assertEquals(-8.0, calc.getResult(), 0.001, "Addition with negative numbers failed");
    }

    @Test
    void testSubtractWithNegativeNumbers() {
        Calculation calc = new Calculation(-5.0, 3.0, "-");
        assertEquals(-8.0, calc.getResult(), 0.001, "Subtraction with negative numbers failed");
    }

    @Test
    void testMultiplyWithNegativeNumbers() {
        Calculation calc = new Calculation(-5.0, 3.0, "*");
        assertEquals(-15.0, calc.getResult(), 0.001, "Multiplication with negative numbers failed");
    }

    @Test
    void testDivideWithNegativeNumbers() {
        Calculation calc = new Calculation(-10.0, 2.0, "/");
        assertEquals(-5.0, calc.getResult(), 0.001, "Division with negative numbers failed");
    }
}
