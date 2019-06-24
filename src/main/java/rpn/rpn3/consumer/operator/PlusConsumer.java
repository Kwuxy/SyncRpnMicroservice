package rpn.rpn3.consumer.operator;

import rpn.rpn3.bus.Bus;
import rpn.rpn3.consumer.Consumer;
import rpn.rpn3.message.ErrorMessage;
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
        Message messageToSend;
        int operandCounter = 0;

        try {
            Double operand1 = operands.pop();
            operandCounter++;

            Double operand2 = operands.pop();
            operandCounter++;

            operands.push(operand1 + operand2);
            messageToSend = new ResultMessage(operands, operatorMessage.expressionId());
        }catch (EmptyStackException e) {
            messageToSend = new ErrorMessage("Not enough operands for operator '+'. Expected 2 got " + operandCounter,
                    operatorMessage.expressionId());
        }

        bus.publish(messageToSend);
    }
}
