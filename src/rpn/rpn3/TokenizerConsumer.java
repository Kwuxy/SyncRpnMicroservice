package rpn.rpn3;

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

        bus.publish(new EndOfToken(expressionMessage.expressionId()));
    }
}
