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
    private final String nullName = "  ";

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

    // move

    public boolean move(Square squareFrom, Square squareTo){
        if(squareFrom == null || squareTo == null || !isMoveAvailable(squareFrom, squareTo) || !emptySquaresBetween(squareFrom, squareTo)) return false;
        squareTo.setPiece(squareFrom.getPiece());
        squareFrom.setPiece(null);
        return true;
    }

    private boolean isMoveAvailable(Square squareFrom, Square squareTo){
        Piece pieceFrom = squareFrom.getPiece();
        Piece pieceTo = squareTo.getPiece();
        if(pieceFrom == null) return false; // there is not any piece in this square
        if(pieceTo != null && pieceFrom.getColor().equals(pieceTo.getColor())) return false; // there is the same color piece in "squareTo" square
        String pieceFromName = pieceFrom.getName();
        //
        int positionXFrom = squareFrom.getPositionX();
        int positionYFrom = squareFrom.getPositionY();
        String colorFrom = squareFrom.getPiece().getColor();
        int positionXTo = squareTo.getPositionX();
        int positionYTo = squareTo.getPositionY();
        switch(pieceFromName){
            case(pawn):
                if(positionXFrom != positionXTo) return false; // no consider capturing another pawn
                boolean whitePawnCanMove = colorFrom.equals(white) && (positionYFrom == 2 && (positionYTo - positionYFrom) == 2 || (positionYTo - positionYFrom) == 1);
                boolean blackPawnCanMove = colorFrom.equals(black) && (positionYFrom == 7 && (positionYTo - positionYFrom) == -2 || (positionYTo - positionYFrom) == -1);
                if(whitePawnCanMove || blackPawnCanMove) return true;
                break;
            case(rook):
                if(positionXFrom == positionXTo || positionYFrom == positionYTo) return true;
                break;
            case(knight):
                boolean verticalHorizontal = Math.abs(positionXFrom - positionXTo) == 1 && Math.abs(positionYFrom - positionYTo) == 2;
                boolean horizontalVertical = Math.abs(positionXFrom - positionXTo) == 2 && Math.abs(positionYFrom - positionYTo) == 1;
                if(verticalHorizontal || horizontalVertical) return true;
                break;
            case(bishop):
                if(Math.abs(positionXFrom - positionXTo) == Math.abs(positionYFrom - positionYTo)) return true;
                break;
            case(queen):
                boolean horizontal = positionXFrom == positionXTo;
                boolean vertical = positionYFrom == positionYTo;
                boolean diagonal = Math.abs(positionXFrom - positionXTo) == Math.abs(positionYFrom - positionYTo);
                if(horizontal || vertical || diagonal) return true;
                break;
            case(king):
                if(Math.abs(positionXFrom - positionXTo) <= 1 && Math.abs(positionYFrom - positionYTo) <= 1) return true;
                break;
        }
        return false;
    }

    public boolean emptySquaresBetween(Square squareFrom, Square squareTo){
        int positionXFrom = squareFrom.getPositionX();
        int positionYFrom = squareFrom.getPositionY();
        int positionXTo = squareTo.getPositionX();
        int positionYTo = squareTo.getPositionY();
        if(Math.abs(positionXFrom - positionXTo) <= 1 && Math.abs(positionYFrom - positionYTo) <= 1) return true;
        else if(positionXFrom == positionXTo){
            for(int i=1; i<Math.abs(positionYFrom - positionYTo); i++){
                if(getSquare(positionXFrom, Math.min(positionYFrom, positionYTo)+i).getPiece() != null) return false;
            }
        }
        else if(positionYFrom == positionYTo){
            for(int i=1; i<Math.abs(positionXFrom - positionXTo); i++){
                if(getSquare(Math.min(positionXFrom, positionXTo)+i, positionYFrom).getPiece() != null) return false;
            }
        }
        else if(Math.abs(positionXFrom - positionXTo) == Math.abs(positionYFrom - positionYTo)){
            if( (positionXFrom - positionXTo)*(positionYFrom - positionYTo) > 0){
                for(int i=1; i<Math.abs(positionYFrom - positionYTo); i++){
                    if(getSquare(Math.min(positionXFrom, positionXTo)+i, Math.min(positionYFrom, positionYTo)+i).getPiece() != null) return false;
                }
            }
            else{
                for(int i=1; i<Math.abs(positionYFrom - positionYTo); i++){
                    if(getSquare(Math.min(positionXFrom, positionXTo)+i, Math.max(positionYFrom, positionYTo)-i).getPiece() != null) return false;
                }
            }
        }
        return true;
    }

    // extra

    public void print(){
        for(int i=8; i>0; i--){
            String row = "[  "+i+"  ]";
            for(int j = 1; j <= 8; j++){
                Square square = getSquares()[i-1][j-1];
                Piece piece = square.getPiece();
                row += "["+square.getColor()+" ";
                if(piece == null) row += nullName+" ";
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
