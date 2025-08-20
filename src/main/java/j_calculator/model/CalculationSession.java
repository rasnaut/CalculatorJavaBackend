package j_calculator.model;

import java.time.LocalDateTime;
import java.util.Stack;
import java.util.UUID;
import java.util.Optional;

import lombok.Getter;
import lombok.Setter;


public class CalculationSession {

    @Getter @Setter
    private String sessionId;
    @Getter @Setter
    private boolean active;
    @Getter
    private LocalDateTime createdAt;
    @Getter
    private LocalDateTime updatedAt;

    private Stack<Calculation> calculations = new Stack<>();

    public CalculationSession() {
        this.sessionId = UUID.randomUUID().toString();
        this.active = true;
        onCreate();
    }

    public CalculationSession(String sessionID, boolean active) {
        this.sessionId = sessionID;
        this.active = active;
        onCreate();
    }
    
    private void onCreate() { createdAt = LocalDateTime.now(); }
    
    private void onUpdate() { updatedAt = LocalDateTime.now(); }

    public void addCalculation(Calculation newCalculation) {
        calculations.push(newCalculation);
        onUpdate();
    }

    public Optional<Calculation> popCalculation() {
        if(calculations.empty())
            return Optional.empty();
        onUpdate();
        return Optional.of(calculations.pop());
    }

    public Optional<Calculation> getCalculation(int index) {
        if (index < 0 || index >= calculations.size())
            return Optional.empty();
        return Optional.of(calculations.get(index));
    }
}