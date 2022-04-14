package sub;

public class Chessboard{

    private Square[][] squares;
    // probably should create list of existing pieces
    //
    private final String white = "w";
    private final String black = "b";
    private final String rook = "R";
    private final String knight = "N";
    private final String bishop = "B";
    private final String queen = "Q";
    private final String king = "K";
    private final String pawn = "P";
    private final String nullName = " ";
    //
    private final String rookWhite = "\u2656";
    private final String knightWhite = "\u2658";
    private final String bishopWhite = "\u2657";
    private final String queenWhite = "\u2655";
    private final String kingWhite = "\u2654";
    private final String pawnWhite = "\u2659";
    private final String rookBlack = "\u265C";
    private final String knightBlack = "\u265E";
    private final String bishopBlack = "\u265D";
    private final String queenBlack = "\u265B";
    private final String kingBlack = "\u265A";
    private final String pawnBlack = "\u265F";
    private final String nullSymbol = "\u3007";

    public Chessboard(){
        this.squares = new Square[8][8];
        setSquares();
    }

    // GET

    public Square[][] getSquares(){
        return this.squares;
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

    public String getStringWhite(){
        return white;
    }

    public String getStringBlack(){
        return black;
    }

    public String getStringRook(){
        return rook;
    }

    public String getStringKnight(){
        return knight;
    }

    public String getStringBishop(){
        return bishop;
    }

    public String getStringQueen(){
        return queen;
    }

    public String getStringKing(){
        return king;
    }

    public String getStringPawn(){
        return pawn;
    }

    public String getStringNullName(){
        return nullName;
    }

    // SET

    private void setSquares(){
        for(int i=1; i<=8; i++){
            for(int j=1; j<=8; j++){
                String pieceName = "";
                String pieceSymbol = nullSymbol;
                if(i==1 || i==8){
                    switch(j){
                        case(1):
                        case(8):
                            pieceName = rook;
                            pieceSymbol = i==1 ? rookWhite : rookBlack;
                            break;
                        case(2):
                        case(7):
                            pieceName = knight;
                            pieceSymbol = i==1 ? knightWhite : knightBlack;
                            break;
                        case(3):
                        case(6):
                            pieceName = bishop;
                            pieceSymbol = i==1 ? bishopWhite : bishopBlack;
                            break;
                        case(4):
                            pieceName = queen;
                            pieceSymbol = i==1 ? queenWhite : queenBlack;
                            break;
                        case(5):
                            pieceName = king;
                            pieceSymbol = i==1 ? kingWhite : kingBlack;
                            break;
                    }
                }
                if(i==2 || i==7){
                    pieceName = pawn;
                    pieceSymbol = i==2 ? pawnWhite : pawnBlack;
                }
                Piece piece = null;
                if(i<=2) piece = new Piece(pieceName, white, pieceSymbol);
                if(i>=7) piece = new Piece(pieceName, black, pieceSymbol);
                squares[i-1][j-1] = new Square(j, i, (i+j)%2==0?black:white, piece);
            }
        }
    }

    // extra

    public void print(){
        String row = "[ ]";
        for(char i='\uFF41'; i<='\uFF48'; i++){
            row += "["+i+"]";
        }
        row += "[ ]";
        System.out.println(row);
        for(int i=8; i>0; i--){
            row = "["+i+"]";
            for(int j = 1; j <= 8; j++){
                Square square = getSquares()[i-1][j-1];
                Piece piece = square.getPiece();
                row += "[" + (piece == null ? nullSymbol : piece.getSymbol()) + "]";
            }
            row += "["+i+"]";
            System.out.println(row);
        }
        row = "[ ]";
        for(char i='\uFF41'; i<='\uFF48'; i++){
            row += "["+i+"]";
        }
        row += "[ ]";
        System.out.println(row);
    }

}
