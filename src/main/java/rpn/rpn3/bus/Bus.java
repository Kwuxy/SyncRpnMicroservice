package rpn.rpn3.bus;

import rpn.rpn3.consumer.Consumer;
import rpn.rpn3.message.Message;

public interface Bus {
    int publish(Message message);
    void subscribe(String messageType, Consumer consumer);
}
