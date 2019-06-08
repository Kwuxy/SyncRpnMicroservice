package rpn.rpn3;

public class EndOfToken implements Message {
    private static final String MESSAGE_TYPE = "end of token";

    private final String expressionId;

    public EndOfToken(String expressionId) {
        this.expressionId = expressionId;
    }

    @Override
    public String messageType() {
        return MESSAGE_TYPE;
    }

    public String expressionId() {
        return expressionId;
    }
}
