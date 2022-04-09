package sub;

public class Chessboard{

    private Cell[][] cells;
    private String white = "w";
    private String black = "b";
    private final String rock = "R";
    private final String knight = "N";
    private final String bishop = "B";
    private final String queen = "Q";
    private final String king = "K";
    private final String pawn = "P";
    private String nullName = "  ";

    public Chessboard(){
        cells = new Cell[8][8];
        setCells();
    }

    // GET

    public Cell[][] getCells(){
        return cells;
    }

    public Cell getCell(int positionX, int positionY){
        if((positionX<=0 || positionX>=9) || (positionY<=0 || positionY>=9)) return null;
        return getCells()[positionY-1][positionX-1];
    }

    // SET

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

    // move

    public boolean move(Cell cellFrom, Cell cellTo){
        if(cellFrom == null || cellTo == null || !isMoveAvailable(cellFrom, cellTo)) return false;
        cellTo.setPiece(cellFrom.getPiece());
        cellFrom.setPiece(null);
        return true;
    }

    private boolean isMoveAvailable(Cell cellFrom, Cell cellTo){
        Piece pieceFrom = cellFrom.getPiece();
        Piece pieceTo = cellTo.getPiece();
        if(pieceFrom == null) return false;
        if(pieceTo != null && pieceFrom.getColor() == pieceTo.getColor()) return false;
        String name = pieceFrom.getName();
        String color = pieceFrom.getColor();
        boolean available = true;
        switch(name){
            case(pawn):
                available = isMoveAvailablePawn(cellFrom, cellTo);
                break;
            case(rock):
                available = isMoveAvailableRock(cellFrom, cellTo);
                break;
            case(knight):
                available = isMoveAvailableKnight(cellFrom, cellTo);
                break;
            case(bishop):
                available = isMoveAvailableBishop(cellFrom, cellTo);
                break;
            case(queen):
                available = isMoveAvailableQueen(cellFrom, cellTo);
                break;
        }
        return available;
    }

    private boolean isMoveAvailablePawn(Cell cellFrom, Cell cellTo){
        int positionXFrom = cellFrom.getPositionX();
        int positionYFrom = cellFrom.getPositionY();
        String colorFrom = cellFrom.getPiece().getColor();
        int positionXTo = cellTo.getPositionX();
        int positionYTo = cellTo.getPositionY();
        if(positionXFrom != positionXTo) return false; // no consider capturing another pawn
        boolean whitePawnCanMove = colorFrom == white && (positionYFrom == 2 && (positionYTo - positionYFrom) == 2 || (positionYTo - positionYFrom) == 1);
        boolean blackPawnCanMove = colorFrom == black && (positionYFrom == 7 && (positionYTo - positionYFrom) == -2 || (positionYTo - positionYFrom) == -1);
        if(whitePawnCanMove || blackPawnCanMove) return true;
        return false;
    }

    private boolean isMoveAvailableRock(Cell cellFrom, Cell cellTo){
        int positionXFrom = cellFrom.getPositionX();
        int positionYFrom = cellFrom.getPositionY();
        int positionXTo = cellTo.getPositionX();
        int positionYTo = cellTo.getPositionY();
        if(positionXFrom == positionXTo || positionYFrom == positionYTo) return true;
        return false;
    }

    private boolean isMoveAvailableKnight(Cell cellFrom, Cell cellTo){
        int positionXFrom = cellFrom.getPositionX();
        int positionYFrom = cellFrom.getPositionY();
        int positionXTo = cellTo.getPositionX();
        int positionYTo = cellTo.getPositionY();
        if(Math.abs(positionXFrom - positionXTo) == 1 && Math.abs(positionYFrom - positionYTo) == 2 || Math.abs(positionXFrom - positionXTo) == 2 && Math.abs(positionYFrom - positionYTo) == 1) return true;
        return false;
    }

    private boolean isMoveAvailableBishop(Cell cellFrom, Cell cellTo){
        int positionXFrom = cellFrom.getPositionX();
        int positionYFrom = cellFrom.getPositionY();
        int positionXTo = cellTo.getPositionX();
        int positionYTo = cellTo.getPositionY();
        if(Math.abs(positionXFrom - positionXTo) == Math.abs(positionYFrom - positionYTo)) return true;
        return false;
    }

    private boolean isMoveAvailableQueen(Cell cellFrom, Cell cellTo){
        int positionXFrom = cellFrom.getPositionX();
        int positionYFrom = cellFrom.getPositionY();
        int positionXTo = cellTo.getPositionX();
        int positionYTo = cellTo.getPositionY();
        boolean horizontal = positionXFrom == positionXTo;
        boolean vertical = positionYFrom == positionYTo;
        boolean diagonal = Math.abs(positionXFrom - positionXTo) == Math.abs(positionYFrom - positionYTo);
        if(horizontal || vertical || diagonal) return true;
        return false;
    }

    // extra

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
