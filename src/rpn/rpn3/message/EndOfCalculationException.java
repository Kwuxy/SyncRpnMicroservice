package rpn.rpn3.message;

public class EndOfCalculationException implements Message {
    public static final String MESSAGE_TYPE = "end of calculation";

    private final String expressionId;
    private final Double result;

    public EndOfCalculationException(String expressionId, Double result) {
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
}
