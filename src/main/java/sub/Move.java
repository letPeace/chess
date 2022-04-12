package sub;

import java.util.ArrayList;

public class Move{

    private class SquarePair extends Square{
        private Square squareFrom;
        private Square squareTo;

        public SquarePair(SquarePair squarePair){
            Square squareFrom = squarePair.getSquareFrom();
            Square squareTo = squarePair.getSquareTo();
            Square squareFromClone = new Square(squareFrom.getPositionX(), squareFrom.getPositionY(), squareFrom.getColor(), squareFrom.getPiece());
            Square squareToClone = new Square(squareTo.getPositionX(), squareTo.getPositionY(), squareTo.getColor(), squareTo.getPiece());
            setSquareFrom(squareFromClone);
            setSquareTo(squareToClone);
        }

        public SquarePair(Square squareFrom, Square squareTo){
            Square squareFromClone = new Square(squareFrom.getPositionX(), squareFrom.getPositionY(), squareFrom.getColor(), squareFrom.getPiece());
            Square squareToClone = new Square(squareTo.getPositionX(), squareTo.getPositionY(), squareTo.getColor(), squareTo.getPiece());
            setSquareFrom(squareFromClone);
            setSquareTo(squareToClone);
        }

        public void setSquareFrom(Square squareFrom){
            this.squareFrom = squareFrom;
        }
        public void setSquareTo(Square squareTo){
            this.squareTo = squareTo;
        }

        public Square getSquareFrom(){
            return this.squareFrom;
        }
        public Square getSquareTo(){
            return this.squareTo;
        }
    }

    private ArrayList<SquarePair> squarePairArrayList;
    private Chessboard chessboard;
    private String turn;

    public Move(){
        setSquarePairArrayList();
        setChessboard();
        setTurn();
    }

    // SET

    public void setSquarePairArrayList(){
        this.squarePairArrayList = new ArrayList<>();
    }

    public void setSquarePairArrayList(ArrayList<SquarePair> squarePairArrayList){
        this.squarePairArrayList = squarePairArrayList;
    }

    public void setChessboard(){
        this.chessboard = new Chessboard();
    }

    public void setChessboard(Chessboard chessboard){
        this.chessboard = chessboard;
    }

    public void setTurn(){
        this.turn = getChessboard().getStringWhite();
    }

    public void setTurn(String turn){
        this.turn = turn;
    }

    public void setTurn(Square square){
        this.turn = square.getPiece().getColor().equals(chessboard.getStringWhite()) ? chessboard.getStringBlack() : chessboard.getStringWhite();
    }

    public void setOppositeTurn(){
        this.turn = turn.equals(chessboard.getStringWhite()) ? chessboard.getStringBlack() : chessboard.getStringWhite();
    }

    // GET

    public ArrayList<SquarePair> getSquarePairArrayList(){
        return squarePairArrayList;
    }

    public Chessboard getChessboard(){
        return chessboard;
    }

    public String getTurn(){
        return turn;
    }

    public SquarePair getMove(int index){
        if(index < 0 || index >= getSquarePairArrayList().size()) return null;
        return getSquarePairArrayList().get(index);
    }

    public SquarePair getLastMove(){
        return getSquarePairArrayList().get(getSquarePairArrayList().size()-1);
    }

    // ADD

    public void addSquarePair(SquarePair squarePair){
        getSquarePairArrayList().add(new SquarePair(squarePair));
    }

    public void addSquarePair(Square squareFrom, Square squareTo){
        getSquarePairArrayList().add(new SquarePair(squareFrom, squareTo));
    }

    // move

    public boolean move(Square squareFrom, Square squareTo){
        if(squareFrom == null || squareTo == null || !isMoveAvailable(squareFrom, squareTo) || !emptySquaresBetween(squareFrom, squareTo) || !isCorrectTurn(squareFrom)) return false;
//        setTurn(squareFrom);
        setOppositeTurn();
        addSquarePair(squareFrom, squareTo);
//        printLastMove();
        squareTo.setPiece(squareFrom.getPiece());
        squareFrom.setPiece(null);
        return true;
    }

    public boolean moveBack(){
        if(squarePairArrayList == null || squarePairArrayList.size() == 0) return false; // is ==null necessary ?
        Square squareFromInfo = getLastMove().getSquareFrom();
        Square squareToInfo = getLastMove().getSquareTo();
        Square squareFrom = chessboard.getSquare(squareFromInfo.getPositionX(), squareFromInfo.getPositionY());
        Square squareTo = chessboard.getSquare(squareToInfo.getPositionX(), squareToInfo.getPositionY());
        squareFrom.setPiece(squareFromInfo.getPiece());
        squareTo.setPiece(squareToInfo.getPiece());
        getSquarePairArrayList().remove(squarePairArrayList.size() - 1);
        setOppositeTurn();
        return true;
    }

    private boolean isCorrectTurn(Square squareFrom){
        Piece pieceFrom = squareFrom.getPiece();
        // should we check if it is null ?
        String colorFrom = pieceFrom.getColor();
        if(colorFrom.equals(getTurn())) return true;
        return false;
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
        //
        if(pieceFromName.equals(chessboard.getStringPawn())){ // PAWN
            if(positionXFrom != positionXTo) return false; // no consider capturing another pawn
            boolean whitePawnCanMove = colorFrom.equals(chessboard.getStringWhite()) && (positionYFrom == 2 && (positionYTo - positionYFrom) == 2 || (positionYTo - positionYFrom) == 1);
            boolean blackPawnCanMove = colorFrom.equals(chessboard.getStringBlack()) && (positionYFrom == 7 && (positionYTo - positionYFrom) == -2 || (positionYTo - positionYFrom) == -1);
            if(whitePawnCanMove || blackPawnCanMove) return true;
        } else if(pieceFromName.equals(chessboard.getStringRook())){ // ROOK
            if(positionXFrom == positionXTo || positionYFrom == positionYTo) return true;
        } else if(pieceFromName.equals(chessboard.getStringKnight())){
            boolean verticalHorizontal = Math.abs(positionXFrom - positionXTo) == 1 && Math.abs(positionYFrom - positionYTo) == 2;
            boolean horizontalVertical = Math.abs(positionXFrom - positionXTo) == 2 && Math.abs(positionYFrom - positionYTo) == 1;
            if(verticalHorizontal || horizontalVertical) return true;
        } else if(pieceFromName.equals(chessboard.getStringBishop())){ // BISHOP
            if(Math.abs(positionXFrom - positionXTo) == Math.abs(positionYFrom - positionYTo)) return true;
        } else if(pieceFromName.equals(chessboard.getStringQueen())){ // QUEEN
            boolean horizontal = positionXFrom == positionXTo;
            boolean vertical = positionYFrom == positionYTo;
            boolean diagonal = Math.abs(positionXFrom - positionXTo) == Math.abs(positionYFrom - positionYTo);
            if(horizontal || vertical || diagonal) return true;
        } else if(pieceFromName.equals(chessboard.getStringKing())){ // KING
            if(Math.abs(positionXFrom - positionXTo) <= 1 && Math.abs(positionYFrom - positionYTo) <= 1) return true;
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
                if(chessboard.getSquare(positionXFrom, Math.min(positionYFrom, positionYTo)+i).getPiece() != null) return false;
            }
        }
        else if(positionYFrom == positionYTo){
            for(int i=1; i<Math.abs(positionXFrom - positionXTo); i++){
                if(chessboard.getSquare(Math.min(positionXFrom, positionXTo)+i, positionYFrom).getPiece() != null) return false;
            }
        }
        else if(Math.abs(positionXFrom - positionXTo) == Math.abs(positionYFrom - positionYTo)){
            if( (positionXFrom - positionXTo)*(positionYFrom - positionYTo) > 0){
                for(int i=1; i<Math.abs(positionYFrom - positionYTo); i++){
                    if(chessboard.getSquare(Math.min(positionXFrom, positionXTo)+i, Math.min(positionYFrom, positionYTo)+i).getPiece() != null) return false;
                }
            }
            else{
                for(int i=1; i<Math.abs(positionYFrom - positionYTo); i++){
                    if(chessboard.getSquare(Math.min(positionXFrom, positionXTo)+i, Math.max(positionYFrom, positionYTo)-i).getPiece() != null) return false;
                }
            }
        }
        return true;
    }

    // extra

    public void print(){
        System.out.println("Moves start:");
        int i = 1;
        for(SquarePair squarePair : getSquarePairArrayList()){
            System.out.println(i+") ["+squarePair.getSquareFrom().squareInfo()+"]["+squarePair.getSquareTo().squareInfo()+"]");
            i++;
        }
        System.out.println("Moves end!");
    }

    public void printLastMove(){
        System.out.println("Last move: ["+getLastMove().getSquareFrom().squareInfo()+"]["+getLastMove().getSquareTo().squareInfo()+"]");
    }

}
