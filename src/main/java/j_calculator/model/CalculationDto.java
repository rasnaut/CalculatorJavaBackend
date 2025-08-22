package j_calculator.model;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CalculationDto {
    private String operation;
    private double operand1;
    private double operand2;
    private double result;

     @JsonCreator
    public CalculationDto(
            @JsonProperty("operation") String operation,
            @JsonProperty("operand1") double operand1,
            @JsonProperty("operand2") double operand2,
            @JsonProperty("result") double result
    ) {
        this.operation = operation;
        this.operand1 = operand1;
        this.operand2 = operand2;
        this.result   = result;
    }
}
