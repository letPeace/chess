package sub.move;

import sub.chessboard.Chessboard;
import sub.chessboard.Square;
import sub.enums.Color;
import sub.enums.PieceName;
import sub.pieces.Piece;

import java.util.ArrayList;

public abstract class Checkmate{

    public static boolean placesInCheck(final Square square){ // does square.getPiece() place the opposite king in check ?
        Color pieceColor = square.getPiece().getColor();
        Color kingColor = pieceColor == Color.WHITE ? Color.BLACK : Color.WHITE;
        Square squareKing = Chessboard.getSquare(new Piece(PieceName.KING, kingColor));
        return Piece.isMoveCorrect(square, squareKing) && Chessboard.emptySquaresBetween(square, squareKing);
    }

    public static ArrayList<Square> placedInCheckBy(final Square square){ // square.getPiece() has just moved, so we have to check if its king is in check
        ArrayList<Square> squaresPlacingKingInCheck = new ArrayList<>();
        Color pieceColor = square.getPiece().getColor();
        for(int i=1; i<=8; i++){ // it might be optimized
            for(int j=1; j<=8; j++){
                Square squareChecking = Chessboard.getSquare(j, i);
                Piece pieceChecking = squareChecking.getPiece();
                if(squareChecking.isEmpty() || pieceColor.equals(pieceChecking.getColor())) continue;
                if(placesInCheck(squareChecking)) squaresPlacingKingInCheck.add(squareChecking);
            }
        }
        return squaresPlacingKingInCheck;
    }

}
