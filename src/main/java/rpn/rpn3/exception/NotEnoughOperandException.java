package rpn.rpn3.exception;

public class NotEnoughOperandException extends Exception {
    public NotEnoughOperandException() {
        super("Not enough operands to calculate operator '+'");
    }
}
