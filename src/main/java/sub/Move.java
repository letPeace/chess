package sub;

import java.util.ArrayList;
import java.util.Scanner;

public class Move{

    private class SquarePair{
        private Square squareFrom;
        private Square squareTo;

        public SquarePair(SquarePair squarePair){
            setSquareFrom(squarePair.getSquareFrom().clone());
            setSquareTo(squarePair.getSquareTo().clone());
        }

        public SquarePair(Square squareFrom, Square squareTo){
            setSquareFrom(squareFrom.clone());
            setSquareTo(squareTo.clone());
        }

        public void setSquareFrom(Square squareFrom){
            this.squareFrom = squareFrom;
        }
        public void setSquareTo(Square squareTo){
            this.squareTo = squareTo;
        }

        public Square getSquareFrom(){
            return squareFrom;
        }
        public Square getSquareTo(){
            return squareTo;
        }

        @Override
        public String toString() {
            return "SquarePair{" +
                    "squareFrom=" + squareFrom +
                    ", squareTo=" + squareTo +
                    '}';
        }

    }

    private ArrayList<SquarePair> squarePairArrayList;
    private Chessboard chessboard;
    private String turn;
    private Scanner input; // temporary variable

    public Move(Scanner input){
        this.input = input;
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

    public SquarePair getLastMove() throws Exception{
        if(getSquarePairArrayList().isEmpty()) throw new Exception("Last move does not exist");
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

    private boolean placesInCheck(final Square square){ // does square.getPiece() place the opposite king in check ?
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
                if(squareChecking.squareIsEmpty() || pieceColor.equals(pieceChecking.getColor())) continue;
                if(placesInCheck(squareChecking)) squaresPlacingKingInCheck.add(squareChecking);
            }
        }
        return squaresPlacingKingInCheck;
    }

    // move

    public boolean move(final Square squareFrom, final Square squareTo){
        if(squareFrom == null || squareTo == null) return false; // it is checked for NULL here, so it is not necessary to do it again
        boolean isMoveAvailable = isMoveAvailable(squareFrom, squareTo);
        boolean emptySquaresBetween = emptySquaresBetween(squareFrom, squareTo);
        boolean isCorrectTurn = isCorrectTurn(squareFrom);
        if(isMoveAvailable && emptySquaresBetween && isCorrectTurn){ // if move is correct (excepting check)
            boolean pawnCanCaptureInPassing = pawnCanCaptureInPassing(squareFrom, squareTo);
            Square squareCheck = squareFrom.clone();
            if(!pawnCanCaptureInPassing && pawnReachesBackRank(squareFrom, squareTo)){ // check if pawn reaches back rank
                String pieceName = choicePieceName();
                String pieceColor = squareFrom.getPiece().getColor();
                String pieceSymbol = chessboard.getSymbolByNameAndColor(pieceName, pieceColor);
                replace(squareFrom, squareTo);
                Square squareFromNew = squareTo.clone();
                Piece newPiece = squareFromNew.getPiece();
                newPiece.setName(pieceName);
                newPiece.setSymbol(pieceSymbol);
                squareCheck = squareFromNew.clone();
                replace(squareFromNew, squareTo);
            } else{
                squareCheck = squareFrom.clone();
                replace(squareFrom, squareTo);
            }
            setOppositeTurn();
            boolean kingIsPlacedInCheck = !placedInCheckBy(squareCheck).isEmpty();
            if(kingIsPlacedInCheck){ // king is placed in check after move => it is incorrect
                moveBack();
                return false;
            }
            if(pawnCanCaptureInPassing){
                Square squareWithCapturedPawn = getMove(squarePairArrayList.size()-2).getSquareTo();
                squareWithCapturedPawn.print();
                chessboard.getSquare(squareWithCapturedPawn).deletePiece();
            }
            return true; // king is NOT placed in check after move => it is correct
        }
        return false; // move is incorrect
    }

    private void replace(Square squareFrom, Square squareTo){
        addSquarePair(squareFrom, squareTo);
        squareTo.setPiece(squareFrom.getPiece());
        squareFrom.deletePiece();
    }

    public boolean moveBack(){
        try{
            if(squarePairArrayList.isEmpty()) return false;
            if(isPawnPromoted()) getSquarePairArrayList().remove(squarePairArrayList.size() - 1);
            Square squareFromLast = getLastMove().getSquareFrom();
            Square squareToLast = getLastMove().getSquareTo();
            Square squareFrom = chessboard.getSquare(squareFromLast.getPositionX(), squareFromLast.getPositionY());
            Square squareTo = chessboard.getSquare(squareToLast.getPositionX(), squareToLast.getPositionY());
            squareFrom.setPiece(squareFromLast.getPiece());
            squareTo.setPiece(squareToLast.getPiece());
            getSquarePairArrayList().remove(squarePairArrayList.size() - 1);
            setOppositeTurn();
            return true;
        } catch(Exception e){
//            System.out.println("moveBack: "+e);
            return false;
        }
    }

    private boolean pawnCanCaptureInPassing(Square squareFrom, Square squareTo){
        try {
            Square squareToLast = chessboard.getSquare(getLastMove().getSquareTo());
            if(!squareToLast.getPiece().getName().equals(chessboard.getStringPawn())) return false;
            Square squareFromLast = chessboard.getSquare(getLastMove().getSquareFrom());
            boolean pawnIsWhite = squareFrom.getPiece().getColor().equals(chessboard.getStringWhite());
            int directionFactor = pawnIsWhite ? -1 : 1;
            boolean oppositePawnPassed = squareToLast.getPositionY() - squareFromLast.getPositionY() == 2*directionFactor;
            boolean pawnCanCapture = (squareTo.getPositionX() == squareFromLast.getPositionX()) && (squareTo.getPositionY() == squareFromLast.getPositionY() + directionFactor);
            return oppositePawnPassed && pawnCanCapture;
        } catch(Exception e){
//            System.out.println("pawnCanCaptureInPassing: "+e);
            return false;
        }
    }

    private boolean isPawnPromoted(){
        try{
            Square squareFrom = getLastMove().getSquareFrom();
            Square squareTo = getLastMove().getSquareTo();
            return squareFrom.getPositionX() == squareTo.getPositionX() && squareFrom.getPositionY() == squareTo.getPositionY(); // it must be enough
        } catch(Exception e){
//            System.out.println("isPawnPromoted: "+e);
            return false;
        }
    }

    private String choicePieceName(){
        while(true){
            try{
                String pieceName = input.nextLine();
                boolean pawnNameEqualsRook = pieceName.equals(chessboard.getStringRook());
                boolean pawnNameEqualsKnight = pieceName.equals(chessboard.getStringKnight());
                boolean pawnNameEqualsBishop = pieceName.equals(chessboard.getStringBishop());
                boolean pawnNameEqualsQueen = pieceName.equals(chessboard.getStringQueen());
                boolean pieceNameCorrect = pawnNameEqualsRook || pawnNameEqualsKnight || pawnNameEqualsBishop || pawnNameEqualsQueen;
                if(pieceNameCorrect) return pieceName;
                System.out.println("Enter an correct name of piece!");
            } catch(Exception e){
                e.printStackTrace();
            }
        }
    }

    private boolean pawnReachesBackRank(final Square squareFrom, final Square squareTo){
        Piece pieceFrom = squareFrom.getPiece();
        if(!pieceFrom.getName().equals(chessboard.getStringPawn())) return false;
        // check for Color is unnecessary as it would be impossible to reach 8 rank being black and 1 rank being white
        return (squareFrom.getPositionY() == 7 && squareTo.getPositionY() == 8 || squareFrom.getPositionY() == 2 && squareTo.getPositionY() == 1);
    }

    private boolean isCorrectTurn(final Square squareFrom){
        if(squareFrom.squareIsEmpty()) return false;
        return squareFrom.getPiece().getColor().equals(getTurn());
    }

    private boolean isMoveAvailable(final Square squareFrom, final Square squareTo){
        Piece pieceFrom = squareFrom.getPiece();
        Piece pieceTo = squareTo.getPiece();
        if(squareFrom.squareIsEmpty()) return false; // there is not any piece in this square
        if(!squareTo.squareIsEmpty() && pieceFrom.getColor().equals(pieceTo.getColor())) return false; // there is the same color piece in squareTo
        //
        int positionXFrom = squareFrom.getPositionX();
        int positionYFrom = squareFrom.getPositionY();
        String pieceFromName = pieceFrom.getName();
        String colorFrom = pieceFrom.getColor();
        //
        int positionXTo = squareTo.getPositionX();
        int positionYTo = squareTo.getPositionY();
        //
        // каждой фигуре сделать свой класс extends Square / Piece ???
        // чтобы у них был метод isMoveAvailable
        if(pieceFromName.equals(chessboard.getStringPawn())){ // PAWN
            boolean pawnIsWhite = colorFrom.equals(chessboard.getStringWhite());
            //
            boolean whitePawnCanMoveForward = pawnIsWhite && (positionYFrom == 2 && (positionYTo - positionYFrom) == 2 || (positionYTo - positionYFrom) == 1);
            boolean blackPawnCanMoveForward = !pawnIsWhite && (positionYFrom == 7 && (positionYTo - positionYFrom) == -2 || (positionYTo - positionYFrom) == -1);
            boolean pawnCanMoveForward = (whitePawnCanMoveForward || blackPawnCanMoveForward) && (positionXFrom == positionXTo) && squareTo.squareIsEmpty();
            //
            boolean whitePawnCanCapture = pawnIsWhite && ((positionYTo - positionYFrom) == 1);
            boolean blackPawnCanCapture = !pawnIsWhite && ((positionYTo - positionYFrom) == -1);
            boolean pawnCanCapture = (whitePawnCanCapture || blackPawnCanCapture) && (Math.abs(positionXFrom - positionXTo) == 1) && !squareTo.squareIsEmpty();
            //
            boolean pawnCanCaptureInPassing = pawnCanCaptureInPassing(squareFrom, squareTo);
            //
            return pawnCanMoveForward || pawnCanCapture || pawnCanCaptureInPassing;
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

    private boolean emptySquaresBetween(final Square squareFrom, final Square squareTo){
        int positionXFrom = squareFrom.getPositionX();
        int positionYFrom = squareFrom.getPositionY();
        int positionXTo = squareTo.getPositionX();
        int positionYTo = squareTo.getPositionY();
        if(Math.abs(positionXFrom - positionXTo) <= 1 && Math.abs(positionYFrom - positionYTo) <= 1) return true;
        else if(positionXFrom == positionXTo){
            for(int i=1; i<Math.abs(positionYFrom - positionYTo); i++){
                if(!chessboard.getSquare(positionXFrom, Math.min(positionYFrom, positionYTo)+i).squareIsEmpty()) return false;
            }
        }
        else if(positionYFrom == positionYTo){
            for(int i=1; i<Math.abs(positionXFrom - positionXTo); i++){
                if(!chessboard.getSquare(Math.min(positionXFrom, positionXTo)+i, positionYFrom).squareIsEmpty()) return false;
            }
        }
        else if(Math.abs(positionXFrom - positionXTo) == Math.abs(positionYFrom - positionYTo)){
            if( (positionXFrom - positionXTo)*(positionYFrom - positionYTo) > 0){
                for(int i=1; i<Math.abs(positionYFrom - positionYTo); i++){
                    if(!chessboard.getSquare(Math.min(positionXFrom, positionXTo)+i, Math.min(positionYFrom, positionYTo)+i).squareIsEmpty()) return false;
                }
            }
            else{
                for(int i=1; i<Math.abs(positionYFrom - positionYTo); i++){
                    if(!chessboard.getSquare(Math.min(positionXFrom, positionXTo)+i, Math.max(positionYFrom, positionYTo)-i).squareIsEmpty()) return false;
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
        try{
            System.out.println("Last move: ["+getLastMove().getSquareFrom().squareInfo()+"]["+getLastMove().getSquareTo().squareInfo()+"]");
        } catch(Exception e){
            System.out.println("printLastMove: "+e);
        }
    }

    @Override
    public String toString() {
        return "Move{" +
                "squarePairArrayList=" + squarePairArrayList +
                '}';
    }

}
