package sub;

public class Piece{

    private String color;
    private String name;
    private String symbol;

    public Piece(String name, String color, String symbol){
        setColor(color);
        setName(name);
        setSymbol(symbol);
    }

    public Piece(String name, String color){
        setColor(color);
        setName(name);
    }

    public Piece(){
        //
    }

    // SET

    public void setColor(String color){
        this.color = color;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setSymbol(String pieceSymbol){
        this.symbol = pieceSymbol;
    }

    // GET

    public String getColor(){
        return color;
    }

    public String getName(){
        return name;
    }

    public String getSymbol(){
        return symbol;
    }

    // extra

    public void print(){
        System.out.println(getName()+" "+getColor());
    }

    public String pieceToString(){
        return getName()+" "+getColor();
    }

}
