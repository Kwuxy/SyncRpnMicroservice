package rpn.rpn3.message;

public class EndOfTokenMessage implements Message {
    public static final String MESSAGE_TYPE = "end of token";

    private final String expressionId;

    public EndOfTokenMessage(String expressionId) {
        this.expressionId = expressionId;
    }

    @Override
    public String messageType() {
        return MESSAGE_TYPE;
    }

    public String expressionId() {
        return expressionId;
    }

    @Override
    public String toString() {
        return "EndOfTokenMessage{" +
                "expressionId='" + expressionId + '\'' +
                '}';
    }
}
