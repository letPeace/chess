package sub.exceptions;

public class MovesSequenceIsEmptyException extends Exception{
    public MovesSequenceIsEmptyException(){
        super();
    }
    public MovesSequenceIsEmptyException(String message){
        super(message);
    }
}
