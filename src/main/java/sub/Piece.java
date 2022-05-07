package sub;

import java.util.Objects;

public class Piece implements Cloneable{

    private PieceName name = PieceName.EMPTY;
    private Color color = Color.EMPTY;
    private PieceSymbol symbol = PieceSymbol.EMPTY;

    public Piece(){
        //
    }

    public Piece(PieceName name, Color color){
        setName(name);
        setColor(color);
    }

    public Piece(PieceName name, Color color, PieceSymbol symbol){
        setName(name);
        setColor(color);
        setSymbol(symbol);
    }

    public PieceName getName(){
        return name;
    }

    public Color getColor(){
        return color;
    }

    public PieceSymbol getSymbol(){
        return symbol;
    }

    public void setName(PieceName name){
        this.name = name;
    }

    public void setColor(Color color){
        this.color = color;
    }

    public void setSymbol(PieceSymbol symbol){
        this.symbol = symbol;
    }

    // object methods

    @Override
    public Piece clone(){
        return new Piece(name, color, symbol);
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
