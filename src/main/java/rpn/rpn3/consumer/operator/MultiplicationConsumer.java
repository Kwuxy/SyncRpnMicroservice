package rpn.rpn3.consumer.operator;

import rpn.rpn3.bus.Bus;
import rpn.rpn3.consumer.Consumer;
import rpn.rpn3.message.Message;
import rpn.rpn3.message.OperatorMessage;
import rpn.rpn3.message.ResultMessage;

import java.util.Stack;

public class MultiplicationConsumer implements Consumer {
    private final Bus bus;

    public MultiplicationConsumer(Bus bus) {
        this.bus = bus;
    }

    @Override
    public void receive(Message message) {
        OperatorMessage operatorMessage = (OperatorMessage) message;
        Stack<Double> operands = operatorMessage.operands();

        Double operand1 = operands.pop();
        Double operand2 = operands.pop();
        operands.push(operand1 * operand2);

        bus.publish(new ResultMessage(operands, operatorMessage.expressionId()));
    }
}
