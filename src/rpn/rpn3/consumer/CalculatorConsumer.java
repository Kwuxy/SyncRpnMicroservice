package rpn.rpn3.consumer;

import rpn.rpn3.message.Message;
import rpn.rpn3.message.OperatorMessage;
import rpn.rpn3.message.TokenMessage;
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
        if(message instanceof TokenMessage) {
            TokenMessage tokenMessage = (TokenMessage) message;
            Stack<Double> stack = stacksByExpressionId.get(message.messageType());

            if(stack == null) {
                stack = new Stack<>();
                stacksByExpressionId.put(message.messageType(), stack);
            }

            if(tokenIsNumber(tokenMessage.token())) {
                stack.push(Double.valueOf(tokenMessage.token()));
            }else{
                bus.publish(new OperatorMessage(stack, tokenMessage.token(), tokenMessage.expressionId()));
                stack.clear();
            }
        }
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
