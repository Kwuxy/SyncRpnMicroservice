package rpn.rpn3;

public class ObjectValidator<T> {
    private T object;
    private Boolean valid;


    public ObjectValidator(T object, Boolean valid) {
        this.object = object;
        this.valid = valid;
    }

    public T getObject() {
        return object;
    }

    public void setObject(T object) {
        this.object = object;
    }

    public Boolean isValid() {
        return valid;
    }

    public void validate(Boolean valid) {
        this.valid = valid;
    }
}
