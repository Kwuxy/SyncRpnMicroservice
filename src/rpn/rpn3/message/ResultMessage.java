package rpn.rpn3.message;

import java.util.Stack;

public class ResultMessage implements Message {
    public static final String MESSAGE_TYPE = "result";

    private final Stack<Double> operands;
    private final String expressionId;

    public ResultMessage(Stack<Double> operands, String expressionId) {
        this.operands = operands;
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

    @Override
    public String toString() {
        return "ResultMessage{" +
                "operands=" + operands +
                ", expressionId='" + expressionId + '\'' +
                '}';
    }
}
