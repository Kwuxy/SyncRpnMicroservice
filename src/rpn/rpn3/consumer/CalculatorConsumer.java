package rpn.rpn3.consumer;

import rpn.rpn3.message.*;
import rpn.rpn3.bus.Bus;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class CalculatorConsumer implements Consumer {
    private final Map<String, Stack<Double>> stacksByExpressionId;
    private final Bus bus;

    public CalculatorConsumer(Bus bus) {
        this.bus = bus;
        this.stacksByExpressionId = new HashMap<>();
    }

    @Override
    public void receive(Message message) {
        Stack<Double> stack = stacksByExpressionId.get(message.messageType());

        if(stack == null) {
            stack = new Stack<>();
            stacksByExpressionId.put(message.messageType(), stack);
        }

        if(message instanceof TokenMessage) {
            processTokenMessage((TokenMessage) message, stack);
        }

        if(message instanceof ResultMessage) {
            processResultMessage((ResultMessage) message, stack);
        }
    }

    private void processTokenMessage(TokenMessage tokenMessage, Stack<Double> stack) {
        if(tokenIsNumber(tokenMessage.token())) {
            stack.push(Double.valueOf(tokenMessage.token()));
        }else{
            bus.publish(new OperatorMessage(stack, tokenMessage.token(), tokenMessage.expressionId()));
            stack.clear();
        }
    }

    private void processResultMessage(ResultMessage resultMessage, Stack<Double> stack) {
        bus.publish(new EndOfCalculationMessage(resultMessage.expressionId(), resultMessage.operands().pop()));
    }

    private boolean tokenIsNumber(String token) {
        try {
            Double.parseDouble(token);
        } catch (NumberFormatException e) {
            return false;
        }

        return true;
    }
}
