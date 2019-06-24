package rpn.rpn3.consumer;

import rpn.rpn3.bus.Bus;
import rpn.rpn3.message.EndOfCalculationMessage;
import rpn.rpn3.message.ErrorMessage;
import rpn.rpn3.message.ExpressionMessage;
import rpn.rpn3.message.Message;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CalculationClient implements Consumer {

    private final Map<String, String> expressionById;
    private final Bus bus;

    public CalculationClient(Bus bus) {
        this.expressionById = new HashMap<>();
        this.bus = bus;
    }

    public void calculate(String expression) {
        String expressionId = UUID.randomUUID().toString();
        expressionById.put(expressionId, expression);
        bus.publish(new ExpressionMessage(expression, expressionId));
    }

    @Override
    public void receive(Message message) {
        if(message instanceof EndOfCalculationMessage) {
            processEndOfCalculation((EndOfCalculationMessage) message);
        }
        if(message instanceof ErrorMessage) {
            processErrorMessage((ErrorMessage) message);
        }
    }

    private void processErrorMessage(ErrorMessage errorMessage) {
        String expression = expressionById.get(errorMessage.expressionId());

        System.out.printf("Error while calculating expression \"%s\" : \"%s\"\n",
                expression, errorMessage.description());
    }

    private void processEndOfCalculation(EndOfCalculationMessage eoc) {
        String expression = expressionById.get(eoc.expressionId());
        System.out.println(expression + " = " + eoc.result());
    }
}
