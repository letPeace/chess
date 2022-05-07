package sub;

import java.util.Objects;

public class Square implements Cloneable{

    private int positionX;
    private int positionY;
    private Color color;
    private Piece piece;

    public Square(){
        //
    }

    public Square(Piece piece){
        setPiece(piece);
    }

    public Square(int positionX, int positionY, Color color){
        setPositionX(positionX);
        setPositionY(positionY);
        setColor(color);
    }

    public Square(int positionX, int positionY, Color color, Piece piece){
        setPositionX(positionX);
        setPositionY(positionY);
        setColor(color);
        setPiece(piece);
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

    // object methods

    @Override
    public Square clone(){
        return new Square(positionX, positionY, color, piece);
    }

    @Override
    public boolean equals(Object object){
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Square square = (Square) object;
        return positionX == square.positionX && positionY == square.positionY && Objects.equals(piece, square.piece);
    }

    @Override
    public int hashCode(){
        return Objects.hash(positionX, positionY, piece);
    }

    @Override
    public String toString(){
        return "Square{" +
                "positionX=" + positionX +
                ", positionY=" + positionY +
                ", color=" + color +
                ", piece=" + piece +
                "}\n";
    }

    // extra

    public boolean squareIsEmpty(){
        return getPiece() == null;
    }

    public void deletePiece(){setPiece(null);}

    public void print(){
        String pieceString = getPiece() == null ? "NODATA" : getPiece().pieceToString();
        System.out.println(getPositionX()+" "+getPositionY()+" "+pieceString);
    }

    public String squareInfo(){
        return getPositionX()+" "+getPositionY()+" "+(squareIsEmpty() ? "NODATA" : getPiece().pieceToString());
    }

}
