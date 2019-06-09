package rpn.rpn3.message;

public class EndOfCalculationMessage implements Message {
    public static final String MESSAGE_TYPE = "end of calculation";

    private final String expressionId;
    private final Double result;

    public EndOfCalculationMessage(String expressionId, Double result) {
        this.expressionId = expressionId;
        this.result = result;
    }

    @Override
    public String messageType() {
        return MESSAGE_TYPE;
    }

    public String expressionId() {
        return expressionId;
    }

    public Double result() {
        return result;
    }

    @Override
    public String toString() {
        return "EndOfCalculationMessage{" +
                "expressionId='" + expressionId + '\'' +
                ", result=" + result +
                '}';
    }
}
