package j_calculator.service;

import j_calculator.model.Calculation;
import j_calculator.model.CalculationSession;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Value;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;


@Service
public class CalculationService {
    private static final Logger logger = Logger.getLogger(CalculationService.class.getName());

    private final RestTemplate restTemplate;
    private final String dbClientUrl;
    private final String dbClientPort;
    
    @Autowired
    public CalculationService(@Value("${client.db.url}") String dbClientUrl, @Value("${client.db.port}") String dbClientPort) {
        this.dbClientUrl = dbClientUrl;
        this.dbClientPort = dbClientPort;
        this.restTemplate = new RestTemplate();
    }

    public CalculationService(String dbClientUrl, String dbClientPort, RestTemplate restTemplate) {
        this.dbClientUrl = dbClientUrl;
        this.dbClientPort = dbClientPort;
        this.restTemplate = restTemplate;
    }

    public String getDbClientUrl() { return dbClientUrl; }
    public String getDbClientPort() { return dbClientPort; }
    public String getFinalDbClientUrl() {
        return "http://" + dbClientUrl + ":" + dbClientPort;
    }

    public CalculationSession createSession(String sessionID) {
        String url = getFinalDbClientUrl() + "/api/sessions/" + sessionID + "/create";
        logger.info("DB URL: " + url);
        CalculationSession session = new CalculationSession(sessionID, true); // Генерация sessionId в Java
        
        restTemplate.postForObject(url, sessionID, Void.class);
        logger.info("Calculation session created!!!");
        return session;
    }

    public Calculation saveCalculation(String sessionId, Calculation calculation) {
        System.out.println("Save Calculation by ID: " + sessionId);
        String url = getFinalDbClientUrl() + "/api/sessions/" + sessionId + "/save_calculations";
        restTemplate.postForObject(url, calculation, Void.class);
        return calculation;
    }
    
    public Calculation[] getCalculations(String sessionId) {
        String url = getFinalDbClientUrl() + "/api/sessions/" + sessionId + "/get_calculations";
        return restTemplate.getForObject(url, Calculation[].class);
    }

    public void closeSession(String sessionId) {
        String url = getFinalDbClientUrl() + "/api/sessions/" + sessionId + "/close";
        restTemplate.postForObject(url, sessionId, Void.class);
    }
}
