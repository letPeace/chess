package sub;

import java.util.ArrayList;

public class Move{

    private ArrayList<SquarePair> squareArrayList;

    public Move(){
        this.squareArrayList = new ArrayList<>();
    }

    private class SquarePair{
        private Square squareFrom;
        private Square squareTo;
        public SquarePair(Square squareFrom, Square squareTo){
            Square squareFromClone = new Square(squareFrom.getPositionX(), squareFrom.getPositionY(), squareFrom.getColor(), squareFrom.getPiece());
            Square squareToClone = new Square(squareTo.getPositionX(), squareTo.getPositionY(), squareTo.getColor(), squareTo.getPiece());
            this.squareFrom = squareFromClone;
            this.squareTo = squareToClone;
        }
        public Square getSquareFrom(){
            return this.squareFrom;
        }
        public Square getSquareTo(){
            return this.squareTo;
        }
    }

    public void add(Square squareFrom, Square squareTo){
        this.squareArrayList.add(new SquarePair(squareFrom, squareTo));
    }

    public void print(){
        System.out.println("Moves start:");
        int i = 1;
        for(SquarePair squarePair : this.squareArrayList){
            System.out.println(i+")");
            i++;
            squarePair.getSquareFrom().print();
            squarePair.getSquareTo().print();
        }
        System.out.println("Moves end!");
    }

}
