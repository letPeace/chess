package sub.move;

import sub.chessboard.Chessboard;
import sub.chessboard.Square;
import sub.exceptions.PieceNotFoundException;
import sub.exceptions.SquareException;
import sub.pieces.Piece;

import java.util.ArrayList;

public abstract class Checkmate{

    private static boolean checkmate = false;

    public static boolean getCheckmate(){
        return checkmate;
    }

    public static void setCheckmate(boolean newCheckmate){
        checkmate = newCheckmate;
    }

    // CHECKMATE

    public static boolean checkmates(final Square square) throws PieceNotFoundException, SquareException{
        // this method MUST be called in case of the opposite king has just been placed in check
        // does square.getPiece() checkmate the opposite king ?
        Square squareKingOpposite = Chessboard.getOppositeKing(square);
        ArrayList<Square> squaresCanEscape = kingCanEscape(squareKingOpposite);
        ArrayList<Square> squaresBlocking = kingCanBeProtected(squareKingOpposite);
        if(squaresCanEscape.isEmpty() && squaresBlocking.isEmpty()){
            setCheckmate(true);
            return true;
        }
        return false;
    }

    private static ArrayList<Square> kingCanEscape(final Square squareKing) throws SquareException{
        ArrayList<Square> squaresCanMoveTo = new ArrayList<>();
        int positionX = squareKing.getPositionX();
        int positionY = squareKing.getPositionY();
        for(int i=-1; i<=1; i++){
            for(int j=-1; j<=1; j++){
                boolean correctPositionX = (positionX+j)>=1 && (positionX+j)<=8;
                boolean correctPositionY = (positionY+i)>=1 && (positionY+i)<=8;
                if(correctPositionX && correctPositionY){
                    Square squareNearby = Chessboard.getSquare(positionX+j, positionY+i);
                    if(Piece.isMoveCorrect(squareKing, squareNearby)){
                        Move.replace(squareKing, squareNearby);
                        boolean isNotInCheck = isKingPlacedInCheck(squareNearby).isEmpty();
                        Move.moveBack();
                        if(isNotInCheck) squaresCanMoveTo.add(squareNearby);
                    }
                }
            }
        }
        return squaresCanMoveTo;
    }

    private static ArrayList<Square> kingCanBeProtected(final Square squareKing) throws PieceNotFoundException, SquareException{
        ArrayList<Square> squaresCanProtect = new ArrayList<>();
        ArrayList<Square> squaresChecking = isKingPlacedInCheck(squareKing);
        for(Square squareChecking : squaresChecking){
            // it may be 0-1-2 squares
            ArrayList<Square> squaresBetween = Chessboard.squaresBetweenIncludingEdge(squareChecking, squareKing);
            // adding edge to make sure squareChecking can be threatened
            for(Square squareBetween : squaresBetween){
                for(int i=1; i<=8; i++){ // can any square threaten squaresChecking or just block enemy`s attack
                    for(int j=1; j<=8; j++){
                        Square squareProtecting = Chessboard.getSquare(j, i);
                        Piece pieceProtecting = squareProtecting.getPiece();
                        if(squareProtecting.isEmpty() || squareKing.getPiece().getColor() != pieceProtecting.getColor()) continue;
                        if(Piece.threatens(squareProtecting, squareBetween)){
                            Move.replace(squareProtecting, squareBetween);
                            boolean isNotInCheck = isOwnKingPlacedInCheck(squareBetween).isEmpty();
                            Move.moveBack();
                            if(isNotInCheck) squaresCanProtect.add(squareProtecting);
                        }
                    }
                }
            }
        }
        return squaresCanProtect;
    }

    // STALEMATE

    public static boolean isStalemated(){
        //
        return false;
    }

    // CHECK

    public static ArrayList<Square> isOppositeKingPlacedInCheck(final Square square) throws PieceNotFoundException, SquareException{
        // is opposite king of square.getPiece() placed in check
        Square squareKing = Chessboard.getOppositeKing(square);
        return isKingPlacedInCheck(squareKing);
    }

    public static ArrayList<Square> isOwnKingPlacedInCheck(final Square square) throws PieceNotFoundException, SquareException{
        // is king of square.getPiece() placed in check
        Square squareKing = Chessboard.getOwnKing(square);
        return isKingPlacedInCheck(squareKing);
    }

    public static ArrayList<Square> isKingPlacedInCheck(final Square squareKing) throws SquareException{
        // is king in check
        return Piece.isThreatened(squareKing);
    }

    public static boolean placesInCheck(final Square squareChecking, final Square squareKing) throws SquareException{
        return Piece.threatens(squareChecking, squareKing);
    }

    public static boolean placesInCheck(final Square squareChecking) throws PieceNotFoundException, SquareException{
        Square squareOppositeKing = Chessboard.getOppositeKing(squareChecking);
        return Piece.threatens(squareChecking, squareOppositeKing);
    }

}
