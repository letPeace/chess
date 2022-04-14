package sub;

public class Piece{

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

    // extra

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null) return false;
        if (getClass() != object.getClass()) return false;
        Piece pieceObject = (Piece) object;
        return name.equals(pieceObject.name) && color.equals(pieceObject.color);
    }

    public void print(){
        System.out.println(getName()+" "+getColor()+" "+getSymbol());
    }

    public String pieceToString(){
        return getName()+" "+getColor()+" "+getSymbol();
    }

}
