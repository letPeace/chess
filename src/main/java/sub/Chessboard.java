package sub;

import java.util.HashSet;

public class Chessboard{

    private Square[][] squares;
    //
    private HashSet<Square> whitePieces;
    private HashSet<Square> blackPieces;
    //
    private final String WHITE = "w";
    private final String BLACK = "b";
    //
    private final String ROOK = "R";
    private final String KNIGHT = "N";
    private final String BISHOP = "B";
    private final String QUEEN = "Q";
    private final String KING = "K";
    private final String PAWN = "P";
    private final String NULL_NAME = " ";
    //
    private final String ROOK_WHITE = "\u2656";
    private final String KNIGHT_WHITE = "\u2658";
    private final String BISHOP_WHITE = "\u2657";
    private final String QUEEN_WHITE = "\u2655";
    private final String KING_WHITE = "\u2654";
    private final String PAWN_WHITE = "\u2659";
    private final String ROOK_BLACK = "\u265C";
    private final String KNIGHT_BLACK = "\u265E";
    private final String BISHOP_BLACK = "\u265D";
    private final String QUEEN_BLACK = "\u265B";
    private final String KING_BLACK = "\u265A";
    private final String PAWN_BLACK = "\u265F";
    private final String NULL_SYMBOL = "\u3007";

    public Chessboard(){
        this.squares = new Square[8][8];
        this.whitePieces = new HashSet<>();
        this.blackPieces = new HashSet<>();
        setSquares();
    }

    // GET

    public Square[][] getSquares(){
        return squares;
    }

    public Square getSquare(int positionX, int positionY){
        if((positionX<=0 || positionX>=9) || (positionY<=0 || positionY>=9)) return null;
        return getSquares()[positionY-1][positionX-1];
    }

    public Square getSquare(Piece piece){
        for(int i=1; i<=8; i++){
            for(int j=1; j<=8; j++){
                if(piece.equals(getSquares()[i-1][j-1].getPiece())) return getSquares()[i-1][j-1];
            }
        }
        return null;
    }

    public HashSet<Square> getWhitePieces() {
        return whitePieces;
    }

    public HashSet<Square> getBlackPieces() {
        return blackPieces;
    }

    public String getStringWhite(){
        return WHITE;
    }

    public String getStringBlack(){
        return BLACK;
    }

    public String getStringRook(){
        return ROOK;
    }

    public String getStringKnight(){
        return KNIGHT;
    }

    public String getStringBishop(){
        return BISHOP;
    }

    public String getStringQueen(){
        return QUEEN;
    }

    public String getStringKing(){
        return KING;
    }

    public String getStringPawn(){
        return PAWN;
    }

    public String getStringNullName(){
        return NULL_NAME;
    }

    // SET

    private void setSquares(){
        for(int i=1; i<=8; i++){
            for(int j=1; j<=8; j++){
                String pieceName = "";
                String pieceSymbol = NULL_SYMBOL;
                if(i==1 || i==8){
                    if(j==1 || j==8){
                        pieceName = ROOK;
                        pieceSymbol = i==1 ? ROOK_WHITE : ROOK_BLACK;
                    } else if(j==2 || j==7){
                        pieceName = KNIGHT;
                        pieceSymbol = i==1 ? KNIGHT_WHITE : KNIGHT_BLACK;
                    } else if(j==3 || j==6){
                        pieceName = BISHOP;
                        pieceSymbol = i==1 ? BISHOP_WHITE : BISHOP_BLACK;
                    } else if(j==4){
                        pieceName = QUEEN;
                        pieceSymbol = i==1 ? QUEEN_WHITE : QUEEN_BLACK;
                    } else if(j==5){
                        pieceName = KING;
                        pieceSymbol = i==1 ? KING_WHITE : KING_BLACK;
                    }
                }
                if(i==2 || i==7){
                    pieceName = PAWN;
                    pieceSymbol = i==2 ? PAWN_WHITE : PAWN_BLACK;
                }
                Piece piece = null;
                if(i<=2) piece = new Piece(pieceName, WHITE, pieceSymbol);
                if(i>=7) piece = new Piece(pieceName, BLACK, pieceSymbol);
                String squareColor = (i+j)%2==0 ? BLACK : WHITE;
                Square square = new Square(j, i, squareColor, piece);
                squares[i-1][j-1] = square;
                if(i<=2) whitePieces.add(square);
                if(i>=7) blackPieces.add(square);
            }
        }
    }

    // extra

    public void print(){
        printCharacters();
        for(int i=8; i>0; i--){
            String row = "["+i+"]";
            for(int j = 1; j <= 8; j++){
                Square square = getSquares()[i-1][j-1];
                Piece piece = square.getPiece();
                row += "[" + (piece == null ? NULL_SYMBOL : piece.getSymbol()) + "]";
            }
            row += "["+i+"]";
            System.out.println(row);
        }
        printCharacters();
    }

    private void printCharacters(){
        String row = "[ ]";
        for(char i='\uFF41'; i<='\uFF48'; i++){
            row += "["+i+"]";
        }
        row += "[ ]";
        System.out.println(row);
    }

}
