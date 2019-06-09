package rpn.rpn3;

import rpn.rpn3.bus.InMemoryBus;
import rpn.rpn3.consumer.*;
import rpn.rpn3.consumer.operator.AbsConsumer;
import rpn.rpn3.consumer.operator.DivideConsumer;
import rpn.rpn3.consumer.operator.MinusConsumer;
import rpn.rpn3.consumer.operator.PlusConsumer;
import rpn.rpn3.message.*;

public class CLI {
    public static void main(String[] args) {
        InMemoryBus bus = new InMemoryBus();
        CalculationClient client = new CalculationClient(bus);

        CalculatorConsumer calculator = new CalculatorConsumer(bus);
        bus.subscribe(ExpressionMessage.MESSAGE_TYPE, new TokenizerConsumer(bus));
        bus.subscribe(TokenMessage.MESSAGE_TYPE, calculator);
        bus.subscribe(EndOfTokenMessage.MESSAGE_TYPE, calculator);
        bus.subscribe("+", new PlusConsumer(bus));
        bus.subscribe("-", new MinusConsumer(bus));
        bus.subscribe("ABS", new AbsConsumer(bus));
        bus.subscribe("/", new DivideConsumer(bus));
        bus.subscribe(ResultMessage.MESSAGE_TYPE, calculator);
        bus.subscribe(EndOfCalculationMessage.MESSAGE_TYPE, client);

        client.calculate("1 2 +");
        client.calculate("1 -2 +");
        client.calculate("1 2 3 + +");

        client.calculate("3 2 -");
        client.calculate("1 2 + 3 -");

        client.calculate("1 ABS -2 ABS +");

        client.calculate("1 4 /");
    }
}
