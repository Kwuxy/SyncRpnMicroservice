package rpn.rpn3.consumer;

import rpn.rpn3.ObjectValidator;
import rpn.rpn3.message.*;
import rpn.rpn3.bus.Bus;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class CalculatorConsumer implements Consumer {
    private final Map<String, ObjectValidator<Stack<Double>>> stacksByExpressionId;
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

        if(message instanceof ErrorMessage) {
            processErrorMessage((ErrorMessage) message);
        }
    }

    private void processErrorMessage(ErrorMessage errorMessage) {
        ObjectValidator<Stack<Double>> stack = stacksByExpressionId.get(errorMessage.expressionId());
        stack.validate(false);
    }

    private void processTokenMessage(TokenMessage tokenMessage) {
        ObjectValidator<Stack<Double>> stack = stacksByExpressionId.get(tokenMessage.expressionId());

        if(stack == null) {
            stack = new ObjectValidator<>(new Stack<>(), true);
            stacksByExpressionId.put(tokenMessage.expressionId(), stack);
        }

        if(stack.isValid()) {
            if(tokenIsNumber(tokenMessage.token())) {
                stack.getObject().push(Double.valueOf(tokenMessage.token()));
            }else{
                bus.publish(new OperatorMessage(stack.getObject(), tokenMessage.token(), tokenMessage.expressionId()));
                //stack.clear();
            }
        }
    }

    private void processResultMessage(ResultMessage resultMessage) {
        ObjectValidator<Stack<Double>> stack = stacksByExpressionId.get(resultMessage.expressionId());
        if(stack.isValid()) {
            stack.setObject(resultMessage.operands());
        }
    }

    private void processEndOfTokenMessage(EndOfTokenMessage eoc) {
        ObjectValidator<Stack<Double>> stack = stacksByExpressionId.get(eoc.expressionId());

        if(stack == null) {
            //Throw Exception ?
            //There is no result for the calculation
            System.out.println("No result");
            return;
        }

        if(stack.isValid()) {
            bus.publish(new EndOfCalculationMessage(eoc.expressionId(), stack.getObject().pop()));
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
