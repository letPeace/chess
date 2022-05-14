package sub.chessboard;

import sub.enums.Color;
import sub.enums.PieceName;
import sub.enums.PieceSymbol;
import sub.exceptions.PieceNotFoundException;
import sub.exceptions.SquareException;
import sub.move.Move;
import sub.move.MovesSequence;
import sub.pieces.Piece;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

public class Chessboard{

    private static final HashSet<Square> whitePieces = new HashSet<>();
    private static final HashSet<Square> blackPieces = new HashSet<>();
    private static Square[][] squares = setSquares();

    // SET

    private static Square[][] setSquares(){
        squares = new Square[8][8];
        for(int i=1; i<=8; i++){
            for(int j=1; j<=8; j++){
                PieceName pieceName = PieceName.EMPTY;
                PieceSymbol pieceSymbol = PieceSymbol.EMPTY;
                if(i==1 || i==8){
                    if(j==1 || j==8){
                        pieceName = PieceName.ROOK;
                        pieceSymbol = i==1 ? PieceSymbol.ROOK_WHITE : PieceSymbol.ROOK_BLACK;
                    } else if(j==2 || j==7){
                        pieceName = PieceName.KNIGHT;
                        pieceSymbol = i==1 ? PieceSymbol.KNIGHT_WHITE : PieceSymbol.KNIGHT_BLACK;
                    } else if(j==3 || j==6){
                        pieceName = PieceName.BISHOP;
                        pieceSymbol = i==1 ? PieceSymbol.BISHOP_WHITE : PieceSymbol.BISHOP_BLACK;
                    } else if(j==4){
                        pieceName = PieceName.QUEEN;
                        pieceSymbol = i==1 ? PieceSymbol.QUEEN_WHITE : PieceSymbol.QUEEN_BLACK;
                    } else if(j==5){
                        pieceName = PieceName.KING;
                        pieceSymbol = i==1 ? PieceSymbol.KING_WHITE : PieceSymbol.KING_BLACK;
                    }
                }
                if(i==2 || i==7){
                    pieceName = PieceName.PAWN;
                    pieceSymbol = i==2 ? PieceSymbol.PAWN_WHITE : PieceSymbol.PAWN_BLACK;
                }
                Piece piece = null; // is it ok?
                if(i<=2) piece = new Piece(pieceName, Color.WHITE, pieceSymbol);
                if(i>=7) piece = new Piece(pieceName, Color.BLACK, pieceSymbol);
                Color squareColor = (i+j)%2==0 ? Color.BLACK : Color.WHITE;
                Square square = new Square(j, i, squareColor, piece);
                squares[i-1][j-1] = square;
                if(i<=2) whitePieces.add(square);
                if(i>=7) blackPieces.add(square);
            }
        }
        return squares;
    }

    public static void reset(){
        setSquares();
        MovesSequence.reset();
        Move.setTurn(Color.WHITE);
    }

    // GET SQUARES

    public static Square[][] getSquares(){
        return squares;
    }

    public static Square getSquare(Square square) throws SquareException{
        return getSquare(square.getPositionX(), square.getPositionY());
    }

    public static Square getSquare(int positionX, int positionY) throws SquareException{
        boolean incorrectX = positionX<=0 || positionX>=9;
        boolean incorrectY = positionY<=0 || positionY>=9;
        if(incorrectX || incorrectY) throw new SquareException("Invalid value of square`s position {"+positionX+","+positionY+"}");
        return getSquares()[positionY-1][positionX-1];
    }

    public static Square getSquare(Piece piece) throws PieceNotFoundException{
        for(int i=1; i<=8; i++){
            for(int j=1; j<=8; j++){
                if(piece.equals(getSquares()[i-1][j-1].getPiece())) return getSquares()[i-1][j-1];
            }
        }
        throw new PieceNotFoundException("Piece \""+piece.getName()+"\" was not found!");
    }

    // GET PIECES

    public static HashSet<Square> getWhitePieces() {
        return whitePieces;
    }

    public static HashSet<Square> getBlackPieces() {
        return blackPieces;
    }

    // GET KING

    public static Square getOppositeKing(Square square) throws PieceNotFoundException{
        Color kingColor = square.getPiece().getColor() == Color.WHITE ? Color.BLACK : Color.WHITE;
        return getKingByColor(kingColor);
    }

    public static Square getOwnKing(Square square) throws PieceNotFoundException{
        return getKingByColor(square.getPiece().getColor());
    }

    public static Square getKingByColor(Color color) throws PieceNotFoundException{
        return Chessboard.getSquare(new Piece(PieceName.KING, color));
    }

    //

    public static ArrayList<Square> squaresBetweenIncludingEdge(final Square squareFrom, final Square squareTo) throws SquareException{
        ArrayList<Square> squares = squaresBetween(squareFrom, squareTo);
        Collections.addAll(squares, squareFrom);
        return squares;
    }

    public static ArrayList<Square> squaresBetweenIncludingEdges(final Square squareFrom, final Square squareTo) throws SquareException{
        ArrayList<Square> squares = squaresBetween(squareFrom, squareTo);
        Collections.addAll(squares, squareFrom, squareTo);
        return squares;
    }

    public static ArrayList<Square> squaresBetween(final Square squareFrom, final Square squareTo) throws SquareException{
        ArrayList<Square> squaresBetween = new ArrayList<>();
        int positionXFrom = squareFrom.getPositionX();
        int positionYFrom = squareFrom.getPositionY();
        int positionXTo = squareTo.getPositionX();
        int positionYTo = squareTo.getPositionY();
        if(positionXFrom == positionXTo){
            for(int i=1; i<Math.abs(positionYFrom - positionYTo); i++){
                squaresBetween.add(Chessboard.getSquare(positionXFrom, Math.min(positionYFrom, positionYTo)+i));
            }
        }
        else if(positionYFrom == positionYTo){
            for(int i=1; i<Math.abs(positionXFrom - positionXTo); i++){
                squaresBetween.add(Chessboard.getSquare(Math.min(positionXFrom, positionXTo)+i, positionYFrom));
            }
        }
        else if(Math.abs(positionXFrom - positionXTo) == Math.abs(positionYFrom - positionYTo)){
            if((positionXFrom - positionXTo)*(positionYFrom - positionYTo) > 0){
                for(int i=1; i<Math.abs(positionYFrom - positionYTo); i++){
                    squaresBetween.add(Chessboard.getSquare(Math.min(positionXFrom, positionXTo)+i, Math.min(positionYFrom, positionYTo)+i));
                }
            }
            else{
                for(int i=1; i<Math.abs(positionYFrom - positionYTo); i++){
                    squaresBetween.add(Chessboard.getSquare(Math.min(positionXFrom, positionXTo)+i, Math.max(positionYFrom, positionYTo)-i));
                }
            }
        }
        return squaresBetween;
    }

    public static boolean emptySquaresBetween(final Square squareFrom, final Square squareTo) throws SquareException{
        int positionXFrom = squareFrom.getPositionX();
        int positionYFrom = squareFrom.getPositionY();
        int positionXTo = squareTo.getPositionX();
        int positionYTo = squareTo.getPositionY();
        if(Math.abs(positionXFrom - positionXTo) <= 1 && Math.abs(positionYFrom - positionYTo) <= 1) return true;
        else if(positionXFrom == positionXTo){
            for(int i=1; i<Math.abs(positionYFrom - positionYTo); i++){
                if(!Chessboard.getSquare(positionXFrom, Math.min(positionYFrom, positionYTo)+i).isEmpty()) return false;
            }
        }
        else if(positionYFrom == positionYTo){
            for(int i=1; i<Math.abs(positionXFrom - positionXTo); i++){
                if(!Chessboard.getSquare(Math.min(positionXFrom, positionXTo)+i, positionYFrom).isEmpty()) return false;
            }
        }
        else if(Math.abs(positionXFrom - positionXTo) == Math.abs(positionYFrom - positionYTo)){
            if((positionXFrom - positionXTo)*(positionYFrom - positionYTo) > 0){
                for(int i=1; i<Math.abs(positionYFrom - positionYTo); i++){
                    if(!Chessboard.getSquare(Math.min(positionXFrom, positionXTo)+i, Math.min(positionYFrom, positionYTo)+i).isEmpty()) return false;
                }
            }
            else{
                for(int i=1; i<Math.abs(positionYFrom - positionYTo); i++){
                    if(!Chessboard.getSquare(Math.min(positionXFrom, positionXTo)+i, Math.max(positionYFrom, positionYTo)-i).isEmpty()) return false;
                }
            }
        }
        return true;
    }

    // print

    public static void print(){
        printCharacters();
        for(int i=8; i>0; i--){
            String row = "["+i+"]";
            for(int j = 1; j <= 8; j++){
                Square square = getSquares()[i-1][j-1];
                Piece piece = square.getPiece();
                row += "[" + (square.isEmpty() ? PieceSymbol.EMPTY.getSymbol() : piece.getSymbol().getSymbol()) + "]";
            }
            row += "["+i+"]";
            System.out.println(row);
        }
        printCharacters();
    }

    private static void printCharacters(){
        String row = "[ ]";
        for(char i='\uFF41'; i<='\uFF48'; i++){
            row += "["+i+"]";
        }
        row += "[ ]";
        System.out.println(row);
    }

}
