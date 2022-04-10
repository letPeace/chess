package sub;

public class Square{

    private int positionX;
    private int positionY;
    private String color;
    private Piece piece;

    public Square(){
        //
    }

    public Square(Piece piece){
        setPiece(piece);
    }

    public Square(int positionX, int positionY, String color){
        setPositionX(positionX);
        setPositionY(positionY);
        setColor(color);
    }

    public Square(int positionX, int positionY, String color, Piece piece){
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

    public void setColor(String color){
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

    public String getColor(){
        return color;
    }

    public Piece getPiece(){
        return piece;
    }

    // extra

    public void print(){
        String pieceString = getPiece() == null ? "NODATA" : getPiece().pieceToString();
        System.out.println(getPositionX()+" "+getPositionY()+" "+pieceString);
    }

}
