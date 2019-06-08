package rpn.rpn3.consumer;

import rpn.rpn3.bus.Bus;
import rpn.rpn3.exception.NotEnoughOperandException;
import rpn.rpn3.message.Message;
import rpn.rpn3.message.OperatorMessage;
import rpn.rpn3.message.ResultMessage;

import java.util.EmptyStackException;
import java.util.Stack;

public class PlusConsumer implements Consumer {
    private final Bus bus;

    public PlusConsumer(Bus bus) {
        this.bus = bus;
    }

    @Override
    public void receive(Message message) {
        OperatorMessage operatorMessage = (OperatorMessage) message;
        Stack<Double> operands = operatorMessage.operands();

        try {
            Double operand1 = operands.pop();
            Double operand2 = operands.pop();
            operands.push(operand1 + operand2);
        }catch (EmptyStackException e) {
            //throw new NotEnoughOperandException();
            //Who is responsible for this ?
        }

        bus.publish(new ResultMessage(operands, operatorMessage.expressionId()));
    }
}
