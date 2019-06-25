package rpn.rpn3.consumer;

import rpn.rpn3.message.*;
import rpn.rpn3.bus.Bus;

import java.util.stream.Stream;

public class TokenizerConsumer implements Consumer {
    private Bus bus;

    public TokenizerConsumer(Bus bus) {
        this.bus = bus;
    }

    @Override
    public void receive(Message message) {
        ExpressionMessage expressionMessage = (ExpressionMessage) message;

        String expression = expressionMessage.expression();

        if(expression.equals("")) {
            bus.publish(new ErrorMessage("Empty expression", expressionMessage.expressionId()));
            return;
        }

        Stream.of(expression.split("\\s+"))
                .forEach(token -> bus.publish(
                        new TokenMessage(token, expressionMessage.expressionId())
                ));

        bus.publish(new EndOfTokenMessage(expressionMessage.expressionId()));
    }
}
