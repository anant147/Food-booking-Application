package in.timesinternet.foodbooking.exception;

public class InvalidRequestBodyException extends  RuntimeException{
    public InvalidRequestBodyException(String message){
        super(message);
    }
}
