package sub.pieces;

import sub.chessboard.Square;
import sub.enums.Color;
import sub.enums.PieceName;
import sub.exceptions.MovesSequenceException;
import sub.move.MovesSequence;

public class Pawn extends Piece{

    private static class PawnIsPassingInfo{
        private Square squareFrom;
        private Square squareTo;
        private int directionFactor;
        private boolean pawnWasInPassing;
        private PawnIsPassingInfo(Square squareFrom, Square squareTo, int directionFactor, boolean pawnWasInPassing){
            this.squareFrom = squareFrom;
            this.squareTo = squareTo;
            this.directionFactor = directionFactor;
            this.pawnWasInPassing = pawnWasInPassing;
        }
        private PawnIsPassingInfo(boolean pawnWasInPassing){
            this.pawnWasInPassing = pawnWasInPassing;
        }
        public Square getSquareFrom(){
            return squareFrom;
        }
        public Square getSquareTo(){
            return squareTo;
        }
        public int getDirectionFactor(){
            return directionFactor;
        }
        public boolean getPawnWasInPassing(){
            return pawnWasInPassing;
        }
    }

    public Pawn(){
        super(PieceName.PAWN);
    }

    public static boolean canCapturePawnWasInPassing(final Square squareFrom, final Square squareTo){
        try{
            PawnIsPassingInfo pawnIsPassingInfo = pawnWasInPassingOnMoveAtIndex(MovesSequence.size()-1);
            if(!pawnIsPassingInfo.getPawnWasInPassing()) return false;
            boolean correctFile = squareTo.getPositionX() == pawnIsPassingInfo.getSquareFrom().getPositionX();
            boolean correctRank = squareTo.getPositionY() == pawnIsPassingInfo.getSquareFrom().getPositionY() + pawnIsPassingInfo.getDirectionFactor();
            boolean isPawn = squareFrom.getPiece().getName() == PieceName.PAWN;
            return correctFile && correctRank && isPawn;
        } catch(MovesSequenceException e){
            return false;
        }
    }

    private static PawnIsPassingInfo pawnWasInPassingOnMoveAtIndex(int moveIndex) throws MovesSequenceException{
        try{
            Square squareFrom = MovesSequence.getMoveAtIndex(moveIndex).getSquareFrom();
            Square squareTo = MovesSequence.getMoveAtIndex(moveIndex).getSquareTo();
            if(squareFrom.getPiece().getName() != PieceName.PAWN) return new PawnIsPassingInfo(false);
            boolean pawnIsWhite = squareFrom.getPiece().getColor() == Color.WHITE;
            int directionFactor = pawnIsWhite ? 1 : -1;
            boolean pawnWasInPassing = squareTo.getPositionY() - squareFrom.getPositionY() == 2 * directionFactor;
            return new PawnIsPassingInfo(squareFrom, squareTo, directionFactor, pawnWasInPassing);
        } catch(MovesSequenceException | NullPointerException e){
            throw new MovesSequenceException();
        }
    }

    public static boolean capturedInPassing(){
        try{
            Square squareFrom = MovesSequence.getMoveAtIndex(MovesSequence.size()-2).getSquareFrom();
            Square squareTo = MovesSequence.getMoveAtIndex(MovesSequence.size()-2).getSquareTo();
            if(squareTo.isEmpty()) return false;
            if(squareTo.getPiece().getName() != PieceName.PAWN) return false;
            boolean samePositionX = squareTo.getPositionX() == squareFrom.getPositionX();
            boolean samePositionY = squareTo.getPositionY() == squareFrom.getPositionY();
            boolean correctRank = squareTo.getPositionY() == 5 || squareTo.getPositionY() == 4;
            return samePositionX && samePositionY && correctRank;
        } catch(MovesSequenceException e){
            return false;
        }
    }

    public static boolean isPromoted(){
        try{
            Square squareFrom = MovesSequence.getLastMove().getSquareFrom();
            Square squareTo = MovesSequence.getLastMove().getSquareTo();
            boolean samePosition = squareFrom.getPositionX() == squareTo.getPositionX() && squareFrom.getPositionY() == squareTo.getPositionY();
            boolean correctRank = squareTo.getPositionY() == 8 || squareTo.getPositionY() == 1;
            return samePosition && correctRank;
        } catch(MovesSequenceException e){
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
