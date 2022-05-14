package sub.move;

import sub.chessboard.Chessboard;
import sub.chessboard.Square;
import sub.exceptions.MovesSequenceException;
import sub.exceptions.PieceNotFoundException;
import sub.exceptions.SquareException;
import sub.pieces.Pawn;
import sub.pieces.Piece;
import sub.enums.Color;
import sub.enums.PieceName;
import sub.enums.PieceSymbol;
import sub.exceptions.MovesSequenceException;
import sub.exceptions.SquareException;
import sub.scanner.Console;

public class Move{

    private static Color turn = Color.WHITE;

    // SET

    public static void setTurn(Color newTurn){
        turn = newTurn;
    }

    public static void setOppositeTurn(){
        if (turn == Color.WHITE) setTurn(Color.BLACK);
        else setTurn(Color.WHITE);
    }

    // GET

    public static Color getTurn(){
        return turn;
    }

    // move

    public static boolean move(final Square squareFrom, final Square squareTo) throws SquareException{
        if(squareFrom == null || squareTo == null) throw new SquareException("squareFrom || squareTo is NULL"); // return false
        boolean isMoveAvailable = Piece.isMoveCorrect(squareFrom, squareTo);
        boolean emptySquaresBetween = Chessboard.emptySquaresBetween(squareFrom, squareTo);
        boolean isCorrectTurn = isCorrectTurn(squareFrom);
        if(isMoveAvailable && emptySquaresBetween && isCorrectTurn){
            if(Pawn.reachesBackRank(squareFrom, squareTo)){
                PieceName pieceName = Console.choicePieceName();
                Color pieceColor = squareFrom.getPiece().getColor();
                PieceSymbol pieceSymbol = Piece.getSymbolByNameAndColor(pieceName, pieceColor);
                replaceSavingTurn(squareFrom, squareTo);
                Square squareFromAfterPromoting = squareTo.clone();
                squareFromAfterPromoting.setPiece(new Piece(pieceName, pieceColor, pieceSymbol));
                replace(squareFromAfterPromoting, squareTo);
            } else{
                if(Pawn.canCapturePawnWasInPassing(squareFrom, squareTo)){
                    try{
                        Square squareEmpty = MovesSequence.getLastMove().getSquareTo().clone();
                        squareEmpty.removePiece();
                        replaceSavingTurn(squareEmpty, Chessboard.getSquare(squareEmpty));
                    } catch(MovesSequenceException e){
                        e.printStackTrace();
                    }
                }
                replace(squareFrom, squareTo);
            }
            try{
                if(!Checkmate.isOwnKingPlacedInCheck(squareTo).isEmpty()){
                    // king is placed in check after move => it is incorrect
                    moveBack();
                    return false;
                }
                if(!Checkmate.isOppositeKingPlacedInCheck(squareTo).isEmpty()){
                    if(Checkmate.checkmates(squareTo)){
                        System.out.println("\n!Checkmate!\n");
                    }
                }
            } catch(PieceNotFoundException e){
                System.out.println(e.getMessage());
                return false;
            }
            return true; // king is NOT placed in check after move => it is correct
        }
        return false; // move is incorrect
    }

    public static void replace(final Square squareFrom, final Square squareTo){
        MovesSequence.addMoveToSequence(squareFrom, squareTo); // square clones are added
        squareTo.setPiece(squareFrom.getPiece());
        squareFrom.removePiece();
        setOppositeTurn();
    }

    public static void replaceSavingTurn(final Square squareFrom, final Square squareTo){
        replace(squareFrom, squareTo);
        setOppositeTurn();
        // opposite turn is set once in replace() and then here, so it is not changed in a result
    }

    public static boolean moveBack(){
        try{
            if(Pawn.isPromoted()){
                MovesSequence.removeLastMove();
            }
            if(Pawn.capturedInPassing()){
                Square squareWithCapturedPawn = MovesSequence.getMoveAtIndex(MovesSequence.size()-2).getSquareTo();
                Chessboard.getSquare(squareWithCapturedPawn).setPiece(squareWithCapturedPawn.getPiece());
                MovesSequence.removeMoveAtIndex(MovesSequence.size()-2);
            }
        } catch(MovesSequenceException movesSequenceIsEmptyException){
            //
        } catch (SquareException e) {
            System.out.println(e.getMessage());
            return false;
        } finally{
            try{
                Square squareFromLast = MovesSequence.getLastMove().getSquareFrom();
                Square squareToLast = MovesSequence.getLastMove().getSquareTo();
                Square squareFrom = Chessboard.getSquare(squareFromLast);
                Square squareTo = Chessboard.getSquare(squareToLast);
                squareFrom.setPiece(squareFromLast.getPiece());
                squareTo.setPiece(squareToLast.getPiece());
                MovesSequence.removeLastMove();
                setOppositeTurn();
            } catch(MovesSequenceException | SquareException exception){
                System.out.println(exception.getMessage());
                return false;
            }
        }
        return true;
    }

    private static boolean isCorrectTurn(final Square squareFrom){
        if(squareFrom.isEmpty()) return false;
        return squareFrom.getPiece().getColor().equals(getTurn());
    }

}
