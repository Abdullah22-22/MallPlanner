package exception;

public class InvalidInputException  extends  RuntimeException {
    public InvalidInputException(String messageKey){
        super(messageKey);
    }
}
