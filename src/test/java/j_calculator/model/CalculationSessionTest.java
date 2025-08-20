package j_calculator.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

public class CalculationSessionTest {
    private CalculationSession session;
    private Calculation calculation1;
    private Calculation calculation2;

    @BeforeEach
    public void setUp() {
        session = new CalculationSession();
        calculation1 = new Calculation(5.0, 3.0, "+");
        calculation2 = new Calculation(10.0, 2.0, "*");
    }

    @Test
    public void testCreateSession() {
        assertNotNull(session.getSessionId(), "Session ID should not be null");
        assertTrue(session.isActive(), "Session should be active");
        assertNotNull(session.getCreatedAt(), "CreatedAt should not be null");
    }

    @Test
    public void testAddCalculation() {
        session.addCalculation(calculation1);
        Optional<Calculation> result = session.getCalculation(0);
        assertTrue(result.isPresent(), "Calculation should be present");
        assertEquals(calculation1, result.get(), "The retrieved calculation should match the added one");
    }

    @Test
    public void testPopCalculation() {
        session.addCalculation(calculation1);
        Optional<Calculation> poppedCalculation = session.popCalculation();
        assertTrue(poppedCalculation.isPresent(), "Popped calculation should be present");
        assertEquals(calculation1, poppedCalculation.get(), "Popped calculation should match the added one");

        Optional<Calculation> resultAfterPop = session.getCalculation(0);
        assertFalse(resultAfterPop.isPresent(), "After pop, the stack should be empty");
    }

    @Test
    public void testPopEmptyStack() {
        Optional<Calculation> poppedCalculation = session.popCalculation();
        assertFalse(poppedCalculation.isPresent(), "Popping from an empty stack should return empty Optional");
    }

    @Test
    public void testGetCalculationOutOfBounds() {
        session.addCalculation(calculation1);
        Optional<Calculation> result = session.getCalculation(1);
        assertFalse(result.isPresent(), "Getting a calculation with out-of-bounds index should return empty Optional");
    }

    @Test
    public void testMultipleCalculations() {
        session.addCalculation(calculation1);
        session.addCalculation(calculation2);

        Optional<Calculation> firstCalculation = session.getCalculation(0);
        Optional<Calculation> secondCalculation = session.getCalculation(1);

        assertTrue(firstCalculation.isPresent(), "First calculation should be present");
        assertTrue(secondCalculation.isPresent(), "Second calculation should be present");

        assertEquals(calculation1, firstCalculation.get(), "First calculation should match calculation1");
        assertEquals(calculation2, secondCalculation.get(), "Second calculation should match calculation2");
    }

    @Test
    public void testSessionTimeStamps() {
        session.addCalculation(calculation1);
        assertNotNull(session.getUpdatedAt(), "UpdatedAt should not be null after adding a calculation");

        session.popCalculation();
        assertNotNull(session.getUpdatedAt(), "UpdatedAt should be updated after popping a calculation");
    }
}
