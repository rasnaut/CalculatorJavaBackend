package j_calculator.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.log.LogMessage;

import j_calculator.model.Calculation;
import j_calculator.model.CalculationSession;
import j_calculator.service.CalculationService;

import java.util.logging.Logger;
import java.util.UUID;

@RestController
@RequestMapping("/api/calculator")
public class CalculatorController {

    private CalculationService calculationService;
    private static final Logger logger = Logger.getLogger(CalculationService.class.getName());

    @Autowired
    public CalculatorController(CalculationService calculationService) {
        this.calculationService = calculationService;
    }
        
    @GetMapping("/add")
    public String add(@RequestParam double a, @RequestParam double b, @RequestParam String session) {
        logger.info("Session" + session + " Add: " + String.valueOf(a) + " + " + String.valueOf(b));
        LogMessage.format("Additional");
        Calculation newCalculation = calculationService.saveCalculation(session, new Calculation(a, b, "+"));
        return String.valueOf(newCalculation.getResult());
    }

    @GetMapping("/multiply")
    public String multiply(@RequestParam double a, @RequestParam double b, @RequestParam String session) {
        Calculation newCalculation = calculationService.saveCalculation(session, new Calculation(a, b, "*"));
        return String.valueOf(newCalculation.getResult());
    }
    
    @GetMapping("/devide")
    public String devide(@RequestParam double a, @RequestParam double b, @RequestParam String session) {
        
        Calculation newCalculation = calculationService.saveCalculation(session, new Calculation(a, b, "/"));
        return String.valueOf(newCalculation.getResult());
    }

    @GetMapping("/subtract")
    public String substruct(@RequestParam double a, @RequestParam double b, @RequestParam String session) {
        Calculation newCalculation = calculationService.saveCalculation(session, new Calculation(a, b, "-"));
        return String.valueOf(newCalculation.getResult());
    }

    @PostMapping("/session")
    public CalculationSession createSession() {
        logger.info("!!! Creating session !!!");
        return calculationService.createSession(UUID.randomUUID().toString());
    }

    @GetMapping("/history/{sessionId}")
    public Calculation[] getHistory(@PathVariable String sessionId) {
        logger.info("get session by ID:" + sessionId);
        return calculationService.getCalculations(sessionId);
    }

    @PostMapping("/reset")
    public void resetSession(@RequestBody String sessionId) {
        calculationService.closeSession(sessionId);
    }
}
