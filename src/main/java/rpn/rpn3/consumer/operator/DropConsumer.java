package rpn.rpn3.consumer.operator;

import rpn.rpn3.bus.Bus;
import rpn.rpn3.consumer.Consumer;
import rpn.rpn3.message.ErrorMessage;
import rpn.rpn3.message.Message;
import rpn.rpn3.message.OperatorMessage;
import rpn.rpn3.message.ResultMessage;

import java.util.Stack;

public class DropConsumer implements Consumer {
    private final Bus bus;

    public DropConsumer(Bus bus) {
        this.bus = bus;
    }

    @Override
    public void receive(Message message) {
        OperatorMessage operatorMessage = (OperatorMessage) message;
        Stack<Double> operands = operatorMessage.operands();

        if(operands.size() < 1) {
            bus.publish(new ErrorMessage("Not enough operands for operator 'DROP'. Expected 1 got 0",
                    operatorMessage.expressionId()));
            return;
        }

        operands.pop();
        bus.publish(new ResultMessage(operands, operatorMessage.expressionId()));
    }
}
