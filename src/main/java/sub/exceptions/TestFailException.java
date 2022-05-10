package sub.exceptions;

public class TestFailException extends RuntimeException{
    public TestFailException(){
        super();
    }
    public TestFailException(String message){
        super(message);
    }
}
