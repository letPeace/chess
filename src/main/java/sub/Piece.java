package sub;

public class Piece{

    private String color;
    private String name;

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

    // GET

    public String getColor(){
        return color;
    }

    public String getName(){
        return name;
    }

    // extra

    public void print(){
        System.out.println(getName()+" "+getColor());
    }

    public String pieceToString(){
        return getName()+" "+getColor();
    }

}
