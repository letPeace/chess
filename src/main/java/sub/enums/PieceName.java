package sub.enums;

public enum PieceName{

    KING("king"),
    QUEEN("queen"),
    ROOK("rook"),
    BISHOP("bishop"),
    KNIGHT("knight"),
    PAWN("pawn"),
    EMPTY("");

    private String name;

    PieceName(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

}
