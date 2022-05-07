package sub;

import java.util.HashSet;

public class Chessboard{

    private final Square[][] squares;
    private final HashSet<Square> whitePieces;
    private final HashSet<Square> blackPieces;

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

    public Square getSquare(Square square){
        return getSquare(square.getPositionX(), square.getPositionY());
    }

    public Square getSquare(int positionX, int positionY){
        if((positionX<=0 || positionX>=9) || (positionY<=0 || positionY>=9)) return null; // throws Exception
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

    public PieceSymbol getSymbolByNameAndColor(PieceName pieceName, Color pieceColor){
        if(pieceColor == Color.WHITE){
            if(pieceName == PieceName.PAWN) return PieceSymbol.PAWN_WHITE;
            if(pieceName == PieceName.ROOK) return PieceSymbol.ROOK_WHITE;
            if(pieceName == PieceName.KNIGHT) return PieceSymbol.KNIGHT_WHITE;
            if(pieceName == PieceName.BISHOP) return PieceSymbol.BISHOP_WHITE;
            if(pieceName == PieceName.QUEEN) return PieceSymbol.QUEEN_WHITE;
        } else if(pieceColor == Color.BLACK){
            if(pieceName == PieceName.PAWN) return PieceSymbol.PAWN_BLACK;
            if(pieceName == PieceName.ROOK) return PieceSymbol.ROOK_BLACK;
            if(pieceName == PieceName.KNIGHT) return PieceSymbol.KNIGHT_BLACK;
            if(pieceName == PieceName.BISHOP) return PieceSymbol.BISHOP_BLACK;
            if(pieceName == PieceName.QUEEN) return PieceSymbol.QUEEN_BLACK;
        }
        return PieceSymbol.EMPTY;
    }

    // SET

    private void setSquares(){
        for(int i=1; i<=8; i++){
            for(int j=1; j<=8; j++){
                PieceName pieceName = PieceName.EMPTY;
                PieceSymbol pieceSymbol = PieceSymbol.EMPTY;
                if(i==1 || i==8){
                    if(j==1 || j==8){
                        pieceName = PieceName.ROOK;
                        pieceSymbol = i==1 ? PieceSymbol.ROOK_WHITE : PieceSymbol.ROOK_BLACK;
                    } else if(j==2 || j==7){
                        pieceName = PieceName.KNIGHT;
                        pieceSymbol = i==1 ? PieceSymbol.KNIGHT_WHITE : PieceSymbol.KNIGHT_BLACK;
                    } else if(j==3 || j==6){
                        pieceName = PieceName.BISHOP;
                        pieceSymbol = i==1 ? PieceSymbol.BISHOP_WHITE : PieceSymbol.BISHOP_BLACK;
                    } else if(j==4){
                        pieceName = PieceName.QUEEN;
                        pieceSymbol = i==1 ? PieceSymbol.QUEEN_WHITE : PieceSymbol.QUEEN_BLACK;
                    } else if(j==5){
                        pieceName = PieceName.KING;
                        pieceSymbol = i==1 ? PieceSymbol.KING_WHITE : PieceSymbol.KING_BLACK;
                    }
                }
                if(i==2 || i==7){
                    pieceName = PieceName.PAWN;
                    pieceSymbol = i==2 ? PieceSymbol.PAWN_WHITE : PieceSymbol.PAWN_BLACK;
                }
                Piece piece = null; // is it ok?
                if(i<=2) piece = new Piece(pieceName, Color.WHITE, pieceSymbol);
                if(i>=7) piece = new Piece(pieceName, Color.BLACK, pieceSymbol);
                Color squareColor = (i+j)%2==0 ? Color.BLACK : Color.WHITE;
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
                row += "[" + (square.squareIsEmpty() ? PieceSymbol.EMPTY.getSymbol() : piece.getSymbol().getSymbol()) + "]";
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
