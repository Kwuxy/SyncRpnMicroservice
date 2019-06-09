package rpn.rpn3.consumer;

import rpn.rpn3.message.Message;
import rpn.rpn3.bus.Bus;
import rpn.rpn3.message.EndOfTokenMessage;
import rpn.rpn3.message.ExpressionMessage;
import rpn.rpn3.message.TokenMessage;

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
        Stream.of(expression.split("\\s+"))
                .forEach(token -> bus.publish(
                        new TokenMessage(token, expressionMessage.expressionId())));

        bus.publish(new EndOfTokenMessage(expressionMessage.expressionId()));
    }
}
