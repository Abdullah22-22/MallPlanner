package exception;

public class NotEnoughSpaceException extends RuntimeException{

    private final double missingArea;

    public  NotEnoughSpaceException (String messageKey, double missingArea){
        super(messageKey);
        this.missingArea = missingArea;
    }

    public double getMissingArea(){
        return missingArea;
    }
}