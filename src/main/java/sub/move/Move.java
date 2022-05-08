package sub.move;

import sub.chessboard.Chessboard;
import sub.chessboard.Square;
import sub.pieces.Pawn;
import sub.pieces.Piece;
import sub.enums.Color;
import sub.enums.PieceName;
import sub.enums.PieceSymbol;
import sub.exceptions.MovesSequenceIsEmptyException;
import sub.scanner.Console;

import java.util.Scanner;

public class Move{

    private Color turn;

    public Move(Scanner input){
        this.turn = Color.WHITE;
    }

    // SET

    public void setTurn(Color turn){
        this.turn = turn;
    }

    public void setOppositeTurn(){
        if (turn == Color.WHITE) setTurn(Color.BLACK);
        else setTurn(Color.WHITE);
    }

    // GET

    public Color getTurn(){
        return turn;
    }

    // move

    public boolean move(final Square squareFrom, final Square squareTo){
        if(squareFrom == null || squareTo == null) return false; // it is checked for NULL here, so it is not necessary to do it again
        boolean isMoveAvailable = Piece.isMoveCorrect(squareFrom, squareTo);
        boolean emptySquaresBetween = Chessboard.emptySquaresBetween(squareFrom, squareTo);
        boolean isCorrectTurn = isCorrectTurn(squareFrom);
        if(isMoveAvailable && emptySquaresBetween && isCorrectTurn){ // if move is correct (excepting check)
            boolean pawnCanCaptureInPassing = Pawn.canCaptureInPassing(squareFrom, squareTo);
            Square squareCheck = squareFrom.clone();
            if(!pawnCanCaptureInPassing && Pawn.reachesBackRank(squareFrom, squareTo)){ // check if pawn reaches back rank
                PieceName pieceName = Console.choicePieceName();
                Color pieceColor = squareFrom.getPiece().getColor();
                PieceSymbol pieceSymbol = Piece.getSymbolByNameAndColor(pieceName, pieceColor);
                System.out.println(pieceSymbol);
                replace(squareFrom, squareTo);
                Square squareFromNew = squareTo.clone();
                Piece newPiece = squareFromNew.getPiece();
                newPiece.setName(pieceName);
                newPiece.setSymbol(pieceSymbol);
                squareCheck = squareFromNew.clone();
                replace(squareFromNew, squareTo);
            } else{
                squareCheck = squareFrom.clone();
                replace(squareFrom, squareTo);
            }
            setOppositeTurn();
            boolean kingIsPlacedInCheck = !Checkmate.placedInCheckBy(squareCheck).isEmpty();
            if(kingIsPlacedInCheck){ // king is placed in check after move => it is incorrect
                moveBack();
                return false;
            }
            if(pawnCanCaptureInPassing){
                Square squareWithCapturedPawn = null;
                try {
                    squareWithCapturedPawn = MovesSequence.getMoveAtIndex(MovesSequence.size()-2).getSquareTo();
                } catch (MovesSequenceIsEmptyException e) {
                    e.printStackTrace();
                }
                System.out.println(squareWithCapturedPawn);
                Chessboard.getSquare(squareWithCapturedPawn).removePiece();
            }
            return true; // king is NOT placed in check after move => it is correct
        }
        return false; // move is incorrect
    }

    private void replace(final Square squareFrom, final Square squareTo){
        MovesSequence.addMoveToSequence(squareFrom, squareTo);
        squareTo.setPiece(squareFrom.getPiece());
        squareFrom.removePiece();
    }

    public boolean moveBack(){
        try{
            if(Pawn.isPromoted()) MovesSequence.removeLastMove();
            Square squareFromLast = MovesSequence.getLastMove().getSquareFrom();
            Square squareToLast = MovesSequence.getLastMove().getSquareTo();
            Square squareFrom = Chessboard.getSquare(squareFromLast.getPositionX(), squareFromLast.getPositionY());
            Square squareTo = Chessboard.getSquare(squareToLast.getPositionX(), squareToLast.getPositionY());
            squareFrom.setPiece(squareFromLast.getPiece());
            squareTo.setPiece(squareToLast.getPiece());
            MovesSequence.removeLastMove();
            setOppositeTurn();
            return true;
        } catch(MovesSequenceIsEmptyException e){
            System.out.println(e.getMessage());
            return false;
        }
    }

    private boolean isCorrectTurn(final Square squareFrom){
        if(squareFrom.isEmpty()) return false;
        return squareFrom.getPiece().getColor().equals(getTurn());
    }

    //

    @Override
    public String toString() {
        return "squarePairArrayList{" + MovesSequence.getMovesSequence() +
                '}';
    }

}
