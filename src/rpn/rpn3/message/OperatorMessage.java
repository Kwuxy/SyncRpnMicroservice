package rpn.rpn3.message;

import java.util.Stack;

public class OperatorMessage implements Message {
    private final String MESSAGE_TYPE;

    private final Stack<Double> operands;
    private final String expressionId;

    public OperatorMessage(Stack<Double> operands, String operator, String expressionId) {
        this.operands = operands;
        this.MESSAGE_TYPE = operator;
        this.expressionId = expressionId;
    }

    @Override
    public String messageType() {
        return MESSAGE_TYPE;
    }

    public Stack<Double> operands() {
        return operands;
    }

    public String expressionId() {
        return expressionId;
    }
}
