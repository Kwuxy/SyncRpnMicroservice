package rpn.rpn3.bus;

import rpn.rpn3.message.Message;
import rpn.rpn3.consumer.Consumer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryBus implements Bus {
    private Map<String, List<Consumer>> consumersByMessageType = new HashMap<>();

    @Override
    public void publish(Message message) {
        List<Consumer> consumers = consumersByMessageType.get(message.messageType());

        if(consumers == null) {
            return;
        }

        consumers.forEach((consumer -> consumer.receive(message)));
    }

    @Override
    public void subscribe(String messageType, Consumer consumer) {
        List<Consumer> consumers = consumersByMessageType.get(messageType);

        if(consumers == null) {
            consumers = new ArrayList<>();
            consumersByMessageType.put(messageType, consumers);
        }

        consumers.add(consumer);
    }
}
