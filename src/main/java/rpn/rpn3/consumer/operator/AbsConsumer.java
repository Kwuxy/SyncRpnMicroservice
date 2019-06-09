package rpn.rpn3.consumer.operator;

import rpn.rpn3.bus.Bus;
import rpn.rpn3.consumer.Consumer;
import rpn.rpn3.message.Message;
import rpn.rpn3.message.OperatorMessage;
import rpn.rpn3.message.ResultMessage;

import java.util.Stack;

public class AbsConsumer implements Consumer {
    private final Bus bus;

    public AbsConsumer(Bus bus) {
        this.bus = bus;
    }

    @Override
    public void receive(Message message) {
        OperatorMessage operatorMessage = (OperatorMessage) message;
        Stack<Double> operands = operatorMessage.operands();

        Double operand = operands.pop();
        operands.push(operand > 0 ? operand : -operand);

        bus.publish(new ResultMessage(operands, operatorMessage.expressionId()));
    }
}
