package rpn.rpn3.message;

public class TokenMessage implements Message {
    public static final String MESSAGE_TYPE = "token";

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

    @Override
    public String toString() {
        return "TokenMessage{" +
                "token='" + token + '\'' +
                ", expressionId='" + expressionId + '\'' +
                '}';
    }
}
