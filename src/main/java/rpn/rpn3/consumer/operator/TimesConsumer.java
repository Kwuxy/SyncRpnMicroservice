package rpn.rpn3.consumer.operator;

import rpn.rpn3.bus.Bus;
import rpn.rpn3.consumer.Consumer;
import rpn.rpn3.message.ErrorMessage;
import rpn.rpn3.message.Message;
import rpn.rpn3.message.OperatorMessage;
import rpn.rpn3.message.ResultMessage;

import java.util.EmptyStackException;
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
        int operandCounter = 0;
        Message messageToSend;

        try{
            Double occurrence = operands.pop();
            operandCounter++;

            Double value = operands.pop();
            operandCounter++;

            for(int counter = 0; counter < occurrence; counter++) {
                operands.push(value);
            }

            messageToSend = new ResultMessage(operands, operatorMessage.expressionId());
        }catch(EmptyStackException e) {
            messageToSend = new ErrorMessage("Not enough operands for operator 'TIMES'. Expected 2 got " + operandCounter,
                    operatorMessage.expressionId());
        }

        bus.publish(messageToSend);
    }
}
