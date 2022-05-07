package sub.enums;

public enum Color{

    WHITE("white"),
    BLACK("black"),
    EMPTY("colorless");

    private String color;

    Color(String color){
        this.color = color;
    }

    public String getColor(){
        return color;
    }

}
