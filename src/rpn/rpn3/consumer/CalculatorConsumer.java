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
        if(message instanceof TokenMessage) {
            processTokenMessage((TokenMessage) message);
        }

        if(message instanceof ResultMessage) {
            processResultMessage((ResultMessage) message);
        }

        if(message instanceof EndOfTokenMessage) {
            processEndOfTokenMessage((EndOfTokenMessage) message);
        }
    }

    private void processTokenMessage(TokenMessage tokenMessage) {
        System.out.println("Process TokenMessage");
        Stack<Double> stack = stacksByExpressionId.get(tokenMessage.expressionId());

        if(stack == null) {
            stack = new Stack<>();
            stacksByExpressionId.put(tokenMessage.expressionId(), stack);
        }

        if(tokenIsNumber(tokenMessage.token())) {
            stack.push(Double.valueOf(tokenMessage.token()));
        }else{
            bus.publish(new OperatorMessage(stack, tokenMessage.token(), tokenMessage.expressionId()));
            //stack.clear();
        }
    }

    private void processResultMessage(ResultMessage resultMessage) {
        System.out.println("Process ResultMessage");
        stacksByExpressionId.put(resultMessage.expressionId(), resultMessage.operands());
    }

    private void processEndOfTokenMessage(EndOfTokenMessage eoc) {
        System.out.println("Process EndOfTokenMessage");
        Stack<Double> stack = stacksByExpressionId.get(eoc.expressionId());

        if(stack == null) {
            //Throw Exception ?
            //There is no result for the calculation
            System.out.println("No result");
        }

        bus.publish(new EndOfCalculationMessage(eoc.expressionId(), stack.pop()));
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
