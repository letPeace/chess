package sub;

public class Chessboard {

    private Cell[][] cells;
    private String white = "w";
    private String black = "b";
    private String rock = "R";
    private String knight = "N";
    private String bishop = "B";
    private String queen = "Q";
    private String king = "K";
    private String pawn = "P";
    private String nullName = "  ";

    public Chessboard(){
        cells = new Cell[8][8];
        setCells();
    }

    public Cell[][] getCells(){
        return cells;
    }

    private void setCells(){
        for(int i=1; i<=8; i++){
            for(int j=1; j<=8; j++){
                String pieceName = "";
                if(i==1 || i==8){
                    switch(j){
                        case(1):
                        case(8):
                            pieceName = rock;
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
                cells[i-1][j-1] = new Cell(j, i, (i+j)%2==0?black:white, piece);
            }
        }
    }

    public void print(){
        for(int i=8; i>=0; i--){
            String row = "[  "+i+"  ]";
            for(int j = 1; j <= 8; j++){
                if(i != 0){
                    Cell cell = getCells()[i-1][j-1];
                    Piece piece = cell.getPiece();
                    row += "["+cell.getColor()+" ";
                    if(piece == null) row += nullName+" ";
                    else row += piece.getName()+" "+piece.getColor();
                    row += "]";
                } else{
                    row += "[  "+j+"  ]";
                }
            }
            System.out.println(row);
        }
    }

}
