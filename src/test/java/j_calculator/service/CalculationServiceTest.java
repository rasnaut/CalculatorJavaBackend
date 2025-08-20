package j_calculator.service;

import j_calculator.model.Calculation;
import j_calculator.model.CalculationSession;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import java.util.UUID;

public class CalculationServiceTest {
    
    @InjectMocks
    private CalculationService calculationService;

    @Mock
    private RestTemplate restTemplate;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        calculationService = new CalculationService("http://localhost", "5001", restTemplate);
        System.out.println("dbClientUrl: " + calculationService.getDbClientUrl());
    }

    @Test
    void testCreateSession() {
        CalculationSession mockSession = new CalculationSession();
        when(restTemplate.postForObject(anyString(), any(CalculationSession.class), eq(CalculationSession.class)))
                .thenReturn(mockSession);

        CalculationSession result = calculationService.createSession(UUID.randomUUID().toString());

        assertNotNull(result, "Session should not be null");
        verify(restTemplate, times(1)).postForObject(anyString(), any(CalculationSession.class), eq(CalculationSession.class));
    }

    @Test
    void testSaveCalculation() {
        Calculation mockCalculation = new Calculation(5.0, 3.0, "+");
        String sessionId = UUID.randomUUID().toString();

        when(restTemplate.getForObject(anyString(), eq(Calculation.class))).thenReturn(null);

        Calculation result = calculationService.saveCalculation(sessionId, mockCalculation);

        assertNotNull(result, "Saved calculation should not be null");
        assertEquals(mockCalculation, result, "The returned calculation should match the input calculation");
        verify(restTemplate, times(1)).postForObject(anyString(), any(Calculation.class), eq(Void.class));
    }

    @Test
    void testGetCalculations() {
        String sessionId = UUID.randomUUID().toString();
        Calculation[] mockCalculations = { new Calculation(5.0, 3.0, "+"), new Calculation(10.0, 2.0, "*") };

        when(restTemplate.getForObject(anyString(), eq(Calculation[].class))).thenReturn(mockCalculations);

        Calculation[] result = calculationService.getCalculations(sessionId);

        assertNotNull(result, "Result should not be null");
        assertEquals(2, result.length, "There should be two calculations returned");
        assertArrayEquals(mockCalculations, result, "The returned calculations should match the mock calculations");
        verify(restTemplate, times(1)).getForObject(anyString(), eq(Calculation[].class));
    }

    @Test
    void testCloseSession() {
        String sessionId = UUID.randomUUID().toString();

        when(restTemplate.getForObject(anyString(), eq(Calculation.class))).thenReturn(null);

        calculationService.closeSession(sessionId);

        verify(restTemplate, times(1)).postForObject(anyString(), eq(sessionId), eq(Void.class));
    }
}
