package sub.enums;

public enum PieceSymbol{

    KING_WHITE("\u2654"),
    QUEEN_WHITE("\u2655"),
    ROOK_WHITE("\u2656"),
    BISHOP_WHITE("\u2657"),
    KNIGHT_WHITE("\u2658"),
    PAWN_WHITE("\u2659"),
    KING_BLACK("\u265A"),
    QUEEN_BLACK("\u265B"),
    ROOK_BLACK("\u265C"),
    BISHOP_BLACK("\u265D"),
    KNIGHT_BLACK("\u265E"),
    PAWN_BLACK("\u265F"),
    EMPTY("\u3007");

    private String symbol;

    PieceSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }

}
