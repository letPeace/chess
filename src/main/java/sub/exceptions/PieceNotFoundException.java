package sub.exceptions;

public class PieceNotFoundException extends Exception{
    public PieceNotFoundException(){
        super();
    }
    public PieceNotFoundException(String message) {
        super(message);
    }
}
