package rpn.rpn3.message;

public class ErrorMessage implements Message {
    public static final String MESSAGE_TYPE = "Error";

    private final String description;
    private final String expressionId;

    public ErrorMessage(String description, String expressionId) {
        this.description = description;
        this.expressionId = expressionId;
    }

    @Override
    public String messageType() {
        return MESSAGE_TYPE;
    }

    public String description() {
        return description;
    }

    public String expressionId() {
        return expressionId;
    }
}
