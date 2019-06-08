package rpn.rpn3;

import java.util.UUID;

public class CLI {
    public static void main(String[] args) {
        InMemoryBus bus = new InMemoryBus();

        bus.subscribe(ExpressionMessage.MESSAGE_TYPE, new TokenizerConsumer(bus));

        String expressionId = UUID.randomUUID().toString();
        bus.publish(new ExpressionMessage("1 2 +", expressionId));
    }
}
