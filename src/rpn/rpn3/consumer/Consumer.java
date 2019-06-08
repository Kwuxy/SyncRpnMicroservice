package rpn.rpn3.consumer;

import rpn.rpn3.message.Message;

public interface Consumer {
    void receive(Message message);
}
