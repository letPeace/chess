package sub.move;

import sub.chessboard.Square;
import sub.exceptions.MovesSequenceIsEmptyException;

import java.util.ArrayList;

public class MovesSequence{

    private static ArrayList<SquarePair> movesSequence = new ArrayList<>();

    // GET

    public static ArrayList<SquarePair> getMovesSequence(){
        return movesSequence;
    }

    public static SquarePair getMoveAtIndex(int index) throws MovesSequenceIsEmptyException {
        if(index < 0 || index >= getMovesSequence().size()) throw new MovesSequenceIsEmptyException("Sequence does not contain such index");
        return getMovesSequence().get(index);
    }

    public static SquarePair getLastMove() throws MovesSequenceIsEmptyException{
        if(getMovesSequence().isEmpty()) throw new MovesSequenceIsEmptyException("Last move does not exist");
        return getMovesSequence().get(getMovesSequence().size()-1);
    }

    public static int size(){
        return getMovesSequence().size();
    }

    // ADD

    public static void addMoveToSequence(final Square squareFrom, final Square squareTo){
        getMovesSequence().add(new SquarePair(squareFrom, squareTo));
    }

    // REMOVE

    public static void removeLastMove(){
        getMovesSequence().remove(getMovesSequence().size() - 1);
    }

    //

    public static void print(){
        System.out.println(getMovesSequence());
    }

}
