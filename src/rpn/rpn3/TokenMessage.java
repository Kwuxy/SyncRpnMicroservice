package rpn.rpn3;

public class TokenMessage implements Message {
    private static final String MESSAGE_TYPE = "token";

    private final String token;
    private final String expressionId;

    public TokenMessage(String token, String expressionId) {
        this.token = token;
        this.expressionId = expressionId;
    }

    @Override
    public String messageType() {
        return MESSAGE_TYPE;
    }

    public String token() {
        return token;
    }

    public String expressionId() {
        return expressionId;
    }
}
