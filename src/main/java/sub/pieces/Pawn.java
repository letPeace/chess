package sub.pieces;

import sub.chessboard.Chessboard;
import sub.chessboard.Square;
import sub.enums.Color;
import sub.enums.PieceName;
import sub.exceptions.MovesSequenceIsEmptyException;
import sub.move.MovesSequence;

public class Pawn extends Piece{

    public Pawn(){
        super(PieceName.PAWN);
    }

    public static boolean canCaptureInPassing(final Square squareFrom, final Square squareTo){
        try{
            Square squareToLast = Chessboard.getSquare(MovesSequence.getLastMove().getSquareTo());
            if(squareToLast.getPiece().getName() != PieceName.PAWN) return false;
            Square squareFromLast = Chessboard.getSquare(MovesSequence.getLastMove().getSquareFrom());
            boolean pawnIsWhite = squareFrom.getPiece().getColor() == Color.WHITE;
            int directionFactor = pawnIsWhite ? -1 : 1;
            boolean oppositePawnPassed = squareToLast.getPositionY() - squareFromLast.getPositionY() == 2*directionFactor;
            boolean pawnCanCapture = (squareTo.getPositionX() == squareFromLast.getPositionX()) && (squareTo.getPositionY() == squareFromLast.getPositionY() + directionFactor);
            return oppositePawnPassed && pawnCanCapture;
        } catch(MovesSequenceIsEmptyException e){
            return false;
        }
    }

    public static boolean isPromoted(){
        try{
            Square squareFrom = MovesSequence.getLastMove().getSquareFrom();
            Square squareTo = MovesSequence.getLastMove().getSquareTo();
            return squareFrom.getPositionX() == squareTo.getPositionX() && squareFrom.getPositionY() == squareTo.getPositionY(); // it must be enough
        } catch(MovesSequenceIsEmptyException e){
            return false;
        }
    }

    public static boolean reachesBackRank(final Square squareFrom, final Square squareTo){
        Piece pieceFrom = squareFrom.getPiece();
        if(pieceFrom.getName() != PieceName.PAWN) return false;
        // check for Color is unnecessary as it would be impossible to reach 8 rank being black and 1 rank being white
        return (squareFrom.getPositionY() == 7 && squareTo.getPositionY() == 8 || squareFrom.getPositionY() == 2 && squareTo.getPositionY() == 1);
    }

}
