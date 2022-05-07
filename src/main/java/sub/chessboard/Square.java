package sub.chessboard;

import sub.enums.Color;
import sub.pieces.Piece;

import java.util.Objects;

public class Square implements Cloneable{

    private int positionX;
    private int positionY;
    private Color color;
    private Piece piece;

    public Square(int positionX, int positionY, Color color, Piece piece){
        this.positionX = positionX;
        this.positionY = positionY;
        this.color = color;
        this.piece = piece;
    }

    // SET

    public void setPositionX(int positionX){
        this.positionX = positionX;
    }

    public void setPositionY(int positionY){
        this.positionY = positionY;
    }

    public void setColor(Color color){
        this.color = color;
    }

    public void setPiece(Piece piece){
        this.piece = piece;
    }

    // GET

    public int getPositionX(){
        return positionX;
    }

    public int getPositionY(){
        return positionY;
    }

    public Color getColor(){
        return color;
    }

    public Piece getPiece(){
        return piece;
    }

    //

    public boolean isEmpty(){
        return getPiece() == null;
    }

    public void deletePiece(){setPiece(null);}

    //

    @Override
    public Square clone(){
        return new Square(getPositionX(), getPositionY(), getColor(), getPiece());
    }

    @Override
    public boolean equals(Object object){
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Square square = (Square) object;
        return getPositionX() == square.getPositionX() && getPositionY() == square.getPositionY() && Objects.equals(getPiece(), square.getPiece());
    }

    @Override
    public int hashCode(){
        return Objects.hash(getPositionX(), getPositionY(), getPiece());
    }

    @Override
    public String toString(){
        return "Square{" +
                "positionX=" + getPositionX() +
                ", positionY=" + getPositionY() +
                ", color=" + getColor() +
                ", piece=" + getPiece() +
                "}\n";
    }

}
