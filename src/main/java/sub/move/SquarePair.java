package sub.move;

import sub.chessboard.Square;

public class SquarePair{

    private Square squareFrom;
    private Square squareTo;

    public SquarePair(Square squareFrom, Square squareTo){
        this.squareFrom = squareFrom.clone();
        this.squareTo = squareTo.clone();
    }

    // SET

    public void setSquareFrom(Square squareFrom){
        this.squareFrom = squareFrom;
    }
    public void setSquareTo(Square squareTo){
        this.squareTo = squareTo;
    }

    // GET

    public Square getSquareFrom(){
        return squareFrom;
    }
    public Square getSquareTo(){
        return squareTo;
    }

    //

    @Override
    public String toString() {
        return "SquarePair{\n" +
                "squareFrom=" + getSquareFrom() +
                "squareTo=" + getSquareTo() +
                '}';
    }

}
