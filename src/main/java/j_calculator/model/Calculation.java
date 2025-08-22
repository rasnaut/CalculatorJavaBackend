package j_calculator.model;

import lombok.Value;

@Value
public class Calculation {
    private String operation;
    private double operand1;
    private double operand2;
    private double result;

    public Calculation(double operand1, double operand2, String operation) {
        this.operand1 = operand1;
        this.operand2 = operand2;
        this.operation = operation;
        this.result = executeOperation();
    }

    // Логика выполнения вычислений
    private double executeOperation() {
        switch (operation) {
            case "+": return add(operand1, operand2);
            case "-": return subtract(operand1, operand2);
            case "*": return multiply(operand1, operand2);
            case "/": return divide(operand1, operand2);
            default:
                throw new IllegalArgumentException("Invalid operation: " + operation);
        }
    }

    private double add     (double a, double b) { return a + b; }
    private double subtract(double a, double b) { return a - b; }
    private double multiply(double a, double b) { return a * b; }
    private double divide  (double a, double b) {
        if (b == 0) {
            throw new ArithmeticException("Division by zero");
        }
        return a / b;
    }
}
