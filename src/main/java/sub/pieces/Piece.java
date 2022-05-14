package sub.pieces;

import sub.chessboard.Chessboard;
import sub.chessboard.Square;
import sub.enums.Color;
import sub.enums.PieceName;
import sub.enums.PieceSymbol;
import sub.exceptions.SquareException;

import java.util.ArrayList;
import java.util.Objects;

public class Piece implements Cloneable{

    private PieceName name;
    private Color color;
    private PieceSymbol symbol;

    public Piece(PieceName name){
        this(PieceName.EMPTY, Color.EMPTY);
    }

    public Piece(PieceName name, Color color){
        this(name, color, PieceSymbol.EMPTY);
    }

    public Piece(PieceName name, Color color, PieceSymbol symbol){
        this.name = name;
        this.color = color;
        this.symbol = symbol;
    }

    // GET

    public PieceName getName(){
        return name;
    }

    public Color getColor(){
        return color;
    }

    public PieceSymbol getSymbol(){
        return symbol;
    }

    // SET

    public void setName(PieceName name){
        this.name = name;
    }

    public void setColor(Color color){
        this.color = color;
    }

    public void setSymbol(PieceSymbol symbol){
        this.symbol = symbol;
    }

    //

    public static PieceSymbol getSymbolByNameAndColor(PieceName pieceName, Color pieceColor){
        if(pieceColor == Color.WHITE){
            if(pieceName == PieceName.PAWN) return PieceSymbol.PAWN_WHITE;
            if(pieceName == PieceName.ROOK) return PieceSymbol.ROOK_WHITE;
            if(pieceName == PieceName.KNIGHT) return PieceSymbol.KNIGHT_WHITE;
            if(pieceName == PieceName.BISHOP) return PieceSymbol.BISHOP_WHITE;
            if(pieceName == PieceName.QUEEN) return PieceSymbol.QUEEN_WHITE;
        } else if(pieceColor == Color.BLACK){
            if(pieceName == PieceName.PAWN) return PieceSymbol.PAWN_BLACK;
            if(pieceName == PieceName.ROOK) return PieceSymbol.ROOK_BLACK;
            if(pieceName == PieceName.KNIGHT) return PieceSymbol.KNIGHT_BLACK;
            if(pieceName == PieceName.BISHOP) return PieceSymbol.BISHOP_BLACK;
            if(pieceName == PieceName.QUEEN) return PieceSymbol.QUEEN_BLACK;
        }
        return PieceSymbol.EMPTY;
    }

    //

    public static boolean threatens(final Square squareThreatening, final Square square) throws SquareException{
        // does squareThreatening threaten square
        return isMoveCorrect(squareThreatening, square) && Chessboard.emptySquaresBetween(squareThreatening, square);
    }

    public static ArrayList<Square> isThreatened(final Square square) throws SquareException{
        // is square threatened
        ArrayList<Square> squaresThreatening = new ArrayList<>();
        Color pieceColor = square.getPiece().getColor();
        for(int i=1; i<=8; i++){ // it might be optimized
            for(int j=1; j<=8; j++){
                Square squareThreatening = Chessboard.getSquare(j, i);
                Piece pieceChecking = squareThreatening.getPiece();
                if(squareThreatening.isEmpty() || pieceColor == pieceChecking.getColor()) continue;
                // is square threatened by squareThreatening
                if(threatens(squareThreatening, square)) squaresThreatening.add(squareThreatening);
            }
        }
        return squaresThreatening;
    }

    public static boolean isMoveCorrect(final Square squareFrom, final Square squareTo){
        Piece pieceFrom = squareFrom.getPiece();
        Piece pieceTo = squareTo.getPiece();
        if(squareFrom.isEmpty()) return false; // there is not any piece in this square
        if(!squareTo.isEmpty() && pieceFrom.getColor().equals(pieceTo.getColor())) return false; // there is the same color piece in squareTo
        //
        int positionXFrom = squareFrom.getPositionX();
        int positionYFrom = squareFrom.getPositionY();
        PieceName pieceFromName = pieceFrom.getName();
        Color colorFrom = pieceFrom.getColor();
        //
        int positionXTo = squareTo.getPositionX();
        int positionYTo = squareTo.getPositionY();
        //
        // каждой фигуре сделать свой класс extends Square / Piece ???
        // чтобы у них был метод isMoveAvailable
        if(pieceFromName == PieceName.PAWN){ // PAWN
            boolean pawnIsWhite = colorFrom == Color.WHITE;
            //
            boolean whitePawnCanMoveForward = pawnIsWhite && (positionYFrom == 2 && (positionYTo - positionYFrom) == 2 || (positionYTo - positionYFrom) == 1);
            boolean blackPawnCanMoveForward = !pawnIsWhite && (positionYFrom == 7 && (positionYTo - positionYFrom) == -2 || (positionYTo - positionYFrom) == -1);
            boolean pawnCanMoveForward = (whitePawnCanMoveForward || blackPawnCanMoveForward) && (positionXFrom == positionXTo) && squareTo.isEmpty();
            //
            boolean whitePawnCanCapture = pawnIsWhite && ((positionYTo - positionYFrom) == 1);
            boolean blackPawnCanCapture = !pawnIsWhite && ((positionYTo - positionYFrom) == -1);
            boolean pawnCanCapture = (whitePawnCanCapture || blackPawnCanCapture) && (Math.abs(positionXFrom - positionXTo) == 1) && !squareTo.isEmpty();
            //
            boolean pawnCanCaptureInPassing = Pawn.canCapturePawnWasInPassing(squareFrom, squareTo);
            //
            return pawnCanMoveForward || pawnCanCapture || pawnCanCaptureInPassing;
        } else if(pieceFromName == PieceName.ROOK){ // ROOK
            return positionXFrom == positionXTo || positionYFrom == positionYTo;
        } else if(pieceFromName == PieceName.KNIGHT){ // KNIGHT
            boolean verticalHorizontal = Math.abs(positionXFrom - positionXTo) == 1 && Math.abs(positionYFrom - positionYTo) == 2;
            boolean horizontalVertical = Math.abs(positionXFrom - positionXTo) == 2 && Math.abs(positionYFrom - positionYTo) == 1;
            return verticalHorizontal || horizontalVertical;
        } else if(pieceFromName == PieceName.BISHOP){ // BISHOP
            return Math.abs(positionXFrom - positionXTo) == Math.abs(positionYFrom - positionYTo);
        } else if(pieceFromName == PieceName.QUEEN){ // QUEEN
            boolean horizontal = positionXFrom == positionXTo;
            boolean vertical = positionYFrom == positionYTo;
            boolean diagonal = Math.abs(positionXFrom - positionXTo) == Math.abs(positionYFrom - positionYTo);
            return horizontal || vertical || diagonal;
        } else if(pieceFromName == PieceName.KING){ // KING
            return Math.abs(positionXFrom - positionXTo) <= 1 && Math.abs(positionYFrom - positionYTo) <= 1;
        }
        return false;
    }

    //

    @Override
    public Piece clone(){
        return new Piece(getName(), getColor(), getSymbol());
    }

    @Override
    public boolean equals(Object object){
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Piece piece = (Piece) object;
        return getName() == piece.getName() && getColor() == piece.getColor();
    }

    @Override
    public int hashCode(){
        return Objects.hash(getName(), getColor());
    }

    @Override
    public String toString(){
        return "Piece{" +
                "name=" + getName() +
                ", color=" + getColor() +
                ", symbol=" + getSymbol() +
                '}';
    }

}
