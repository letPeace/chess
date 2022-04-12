package sub;

public class Chessboard{

    private Square[][] squares;
    private final String white = "w";
    private final String black = "b";
    private final String rook = "R";
    private final String knight = "N";
    private final String bishop = "B";
    private final String queen = "Q";
    private final String king = "K";
    private final String pawn = "P";
    private final String nullName = "   ";

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
                if(i==1 || i==8){
                    switch(j){
                        case(1):
                        case(8):
                            pieceName = rook;
                            break;
                        case(2):
                        case(7):
                            pieceName = knight;
                            break;
                        case(3):
                        case(6):
                            pieceName = bishop;
                            break;
                        case(4):
                            pieceName = queen;
                            break;
                        case(5):
                            pieceName = king;
                            break;
                    }
                }
                if(i==2 || i==7){
                    pieceName = pawn;
                }
                Piece piece = null;
                if(i<=2) piece = new Piece(pieceName, white);
                if(i>=7) piece = new Piece(pieceName, black);
                squares[i-1][j-1] = new Square(j, i, (i+j)%2==0?black:white, piece);
            }
        }
    }

    // extra

    public void print(){
        for(int i=8; i>0; i--){
            String row = "[  "+i+"  ]";
            for(int j = 1; j <= 8; j++){
                Square square = getSquares()[i-1][j-1];
                Piece piece = square.getPiece();
                row += "["+square.getColor()+" ";
                if(piece == null) row += getStringNullName();
                else row += piece.getName()+" "+piece.getColor();
                row += "]";
            }
            System.out.println(row);
        }
        String row = "[     ]";
        for(char i='a'; i<='h'; i++){
            row += "[  "+i+"  ]";
        }
        System.out.println(row);
    }

}
