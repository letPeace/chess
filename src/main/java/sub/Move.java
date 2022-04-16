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

    public void setOppositeTurn(){
        String white = chessboard.getStringWhite();
        String black = chessboard.getStringBlack();
        if (turn.equals(white)) setTurn(black);
        else setTurn(white);
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

    public void addSquarePair(final SquarePair squarePair){
        getSquarePairArrayList().add(new SquarePair(squarePair));
    }

    public void addSquarePair(final Square squareFrom, final Square squareTo){
        getSquarePairArrayList().add(new SquarePair(squareFrom, squareTo));
    }

    // checkmate

    private boolean givesCheck(final Square square){ // does square.getPiece() place the opposite king in check ?
        String pieceColor = square.getPiece().getColor();
        String kingColor = pieceColor.equals(chessboard.getStringWhite()) ? chessboard.getStringBlack() : chessboard.getStringWhite();
        Square squareKing = chessboard.getSquare(new Piece(chessboard.getStringKing(), kingColor));
        return isMoveAvailable(square, squareKing) && emptySquaresBetween(square, squareKing);
    }

    private ArrayList<Square> placedInCheckBy(final Square square){ // square.getPiece() has just moved, so we have to check if its king is in check
        ArrayList<Square> squaresPlacingKingInCheck = new ArrayList<>();
        String pieceColor = square.getPiece().getColor();
        for(int i=1; i<=8; i++){ // it might be optimized
            for(int j=1; j<=8; j++){
                Square squareChecking = chessboard.getSquare(j, i);
                Piece pieceChecking = squareChecking.getPiece();
                if(pieceChecking == null || pieceColor.equals(pieceChecking.getColor())) continue;
                if(givesCheck(squareChecking)) squaresPlacingKingInCheck.add(squareChecking);
            }
        }
        if(squaresPlacingKingInCheck.isEmpty()) return null;
        return squaresPlacingKingInCheck;
    }

    // move

    public boolean move(Square squareFrom, Square squareTo){
        if(squareFrom == null || squareTo == null) return false; // it is checked for NULL here, so it is not necessary to do it again
        boolean isMoveAvailable = isMoveAvailable(squareFrom, squareTo);
        boolean emptySquaresBetween = emptySquaresBetween(squareFrom, squareTo);
        boolean isCorrectTurn = isCorrectTurn(squareFrom);
        if(isMoveAvailable && emptySquaresBetween && isCorrectTurn){ // if move is correct (excepting check)
            Square squareFromClone = squareFrom.clone();
            replace(squareFrom, squareTo);
            boolean kingIsPlacedInCheck = placedInCheckBy(squareFromClone) != null;
            if(kingIsPlacedInCheck){ // king is placed in check after move => it is incorrect
                moveBack();
                return false;
            }
            return true; // king is NOT placed in check after move => it is correct
        }
        return false; // move is incorrect
    }

    private void replace(Square squareFrom, Square squareTo){
        setOppositeTurn();
        addSquarePair(squareFrom, squareTo);
        squareTo.setPiece(squareFrom.getPiece());
        squareFrom.setPiece(null);
    }

    public boolean moveBack(){
        if(squarePairArrayList.size() == 0) return false;
        Square squareFromLast = getLastMove().getSquareFrom();
        Square squareToLast = getLastMove().getSquareTo();
        Square squareFrom = chessboard.getSquare(squareFromLast.getPositionX(), squareFromLast.getPositionY());
        Square squareTo = chessboard.getSquare(squareToLast.getPositionX(), squareToLast.getPositionY());
        squareFrom.setPiece(squareFromLast.getPiece());
        squareTo.setPiece(squareToLast.getPiece());
        getSquarePairArrayList().remove(squarePairArrayList.size() - 1);
        setOppositeTurn();
        return true;
    }

    private boolean isCorrectTurn(final Square squareFrom){
        Piece pieceFrom = squareFrom.getPiece();
        if(pieceFrom == null) return false;
        return pieceFrom.getColor().equals(getTurn());
    }

    private boolean isMoveAvailable(final Square squareFrom, final Square squareTo){
        Piece pieceFrom = squareFrom.getPiece();
        Piece pieceTo = squareTo.getPiece();
        if(pieceFrom == null) return false; // there is not any piece in this square
        if(pieceTo != null && pieceFrom.getColor().equals(pieceTo.getColor())) return false; // there is the same color piece in squareTo
        //
        int positionXFrom = squareFrom.getPositionX();
        int positionYFrom = squareFrom.getPositionY();
        String pieceFromName = pieceFrom.getName();
        String colorFrom = pieceFrom.getColor();
        //
        int positionXTo = squareTo.getPositionX();
        int positionYTo = squareTo.getPositionY();
        //
        if(pieceFromName.equals(chessboard.getStringPawn())){ // PAWN
            if(positionXFrom != positionXTo) return false; // we do not consider capture another pawn
            boolean whitePawnCanMove = colorFrom.equals(chessboard.getStringWhite()) && (positionYFrom == 2 && (positionYTo - positionYFrom) == 2 || (positionYTo - positionYFrom) == 1);
            boolean blackPawnCanMove = colorFrom.equals(chessboard.getStringBlack()) && (positionYFrom == 7 && (positionYTo - positionYFrom) == -2 || (positionYTo - positionYFrom) == -1);
            return whitePawnCanMove || blackPawnCanMove;
        } else if(pieceFromName.equals(chessboard.getStringRook())){ // ROOK
            return positionXFrom == positionXTo || positionYFrom == positionYTo;
        } else if(pieceFromName.equals(chessboard.getStringKnight())){ // KNIGHT
            boolean verticalHorizontal = Math.abs(positionXFrom - positionXTo) == 1 && Math.abs(positionYFrom - positionYTo) == 2;
            boolean horizontalVertical = Math.abs(positionXFrom - positionXTo) == 2 && Math.abs(positionYFrom - positionYTo) == 1;
            return verticalHorizontal || horizontalVertical;
        } else if(pieceFromName.equals(chessboard.getStringBishop())){ // BISHOP
            return Math.abs(positionXFrom - positionXTo) == Math.abs(positionYFrom - positionYTo);
        } else if(pieceFromName.equals(chessboard.getStringQueen())){ // QUEEN
            boolean horizontal = positionXFrom == positionXTo;
            boolean vertical = positionYFrom == positionYTo;
            boolean diagonal = Math.abs(positionXFrom - positionXTo) == Math.abs(positionYFrom - positionYTo);
            return horizontal || vertical || diagonal;
        } else if(pieceFromName.equals(chessboard.getStringKing())){ // KING
            return Math.abs(positionXFrom - positionXTo) <= 1 && Math.abs(positionYFrom - positionYTo) <= 1;
        }
        return false;
    }

    public boolean emptySquaresBetween(final Square squareFrom, final Square squareTo){
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
        System.out.println("Moves:");
        int i = 1;
        for(SquarePair squarePair : getSquarePairArrayList()){
            System.out.println(i+") ["+squarePair.getSquareFrom().squareInfo()+"]["+squarePair.getSquareTo().squareInfo()+"]");
            i++;
        }
    }

    public void printLastMove(){
        System.out.println("Last move: ["+getLastMove().getSquareFrom().squareInfo()+"]["+getLastMove().getSquareTo().squareInfo()+"]");
    }

}
