package rpn.rpn3.consumer.operator;

import rpn.rpn3.bus.Bus;
import rpn.rpn3.consumer.Consumer;
import rpn.rpn3.message.Message;
import rpn.rpn3.message.OperatorMessage;
import rpn.rpn3.message.ResultMessage;

import java.util.Stack;

public class TimesConsumer implements Consumer {
    private final Bus bus;

    public TimesConsumer(Bus bus) {
        this.bus = bus;
    }

    @Override
    public void receive(Message message) {
        OperatorMessage operatorMessage = (OperatorMessage) message;
        Stack<Double> operands = operatorMessage.operands();

        Double occurence = operands.pop();
        Double value = operands.pop();

        for(int counter = 0; counter < occurence; counter++) {
            operands.push(value);
        }

        bus.publish(new ResultMessage(operands, operatorMessage.expressionId()));
    }
}
