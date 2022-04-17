package sub;

import java.util.Objects;

public class Piece implements Cloneable{

    private String name;
    private String color;
    private String symbol;

    public Piece(){
        //
    }

    public Piece(String name, String color){
        setName(name);
        setColor(color);
    }

    public Piece(String name, String color, String symbol){
        setName(name);
        setColor(color);
        setSymbol(symbol);
    }

    // SET

    public void setName(String name){
        this.name = name;
    }

    public void setColor(String color){
        this.color = color;
    }

    public void setSymbol(String pieceSymbol){
        this.symbol = pieceSymbol;
    }

    // GET

    public String getName(){
        return name;
    }

    public String getColor(){
        return color;
    }

    public String getSymbol(){
        return symbol;
    }

    // object methods

    @Override
    public Piece clone(){
        Piece piece = new Piece();
        piece.setName(this.getName());
        piece.setColor(this.getColor());
        piece.setSymbol(this.getSymbol());
        return piece;
    }

    @Override
    public boolean equals(Object object){
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Piece piece = (Piece) object;
        return name.equals(piece.name) && color.equals(piece.color);
    }

    @Override
    public int hashCode(){
        return Objects.hash(name, color);
    }

    @Override
    public String toString(){
        return "Piece{" +
                "name='" + name + '\'' +
                ", color='" + color + '\'' +
                ", symbol='" + symbol + '\'' +
                '}';
    }

    // print

    public void print(){
        System.out.println(getName()+" "+getColor()+" "+getSymbol());
    }

    public String pieceToString(){
        return getName()+" "+getColor()+" "+getSymbol();
    }

}
