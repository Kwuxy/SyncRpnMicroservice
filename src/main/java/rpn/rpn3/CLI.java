package rpn.rpn3;

import rpn.rpn3.bus.InMemoryBus;
import rpn.rpn3.consumer.*;
import rpn.rpn3.consumer.operator.*;
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
        bus.subscribe("*", new MultiplicationConsumer(bus));
        bus.subscribe("DROP", new DropConsumer(bus));
        bus.subscribe("TIMES", new TimesConsumer(bus));
        bus.subscribe("SWAP", new SwapConsumer(bus));
        bus.subscribe(ResultMessage.MESSAGE_TYPE, calculator);
        bus.subscribe(EndOfCalculationMessage.MESSAGE_TYPE, client);

        bus.subscribe(ErrorMessage.MESSAGE_TYPE, calculator);
        bus.subscribe(ErrorMessage.MESSAGE_TYPE, client);

        client.calculate("1 2 +");
        client.calculate("1 -2 +");
        client.calculate("1 2 3 + +");

        client.calculate("3 2 -");
        client.calculate("1 2 + 3 -");

        client.calculate("1 ABS -2 ABS +");

        client.calculate("1 4 /");

        client.calculate("1 3 + 5 DROP");

        client.calculate("6 3 TIMES + +");

        client.calculate("3 5 *");

        client.calculate("2 6 SWAP /");

        System.out.println("Trying to calculate some malformed expressions");
        client.calculate("1 +");
        client.calculate("+");
        client.calculate("+ 3 4 +");
    }
}
