package chess;

import sub.chessboard.Chessboard;
import sub.chessboard.Square;
import sub.enums.Color;
import sub.exceptions.MovesSequenceIsEmptyException;
import sub.move.Move;
import sub.move.MovesSequence;
import sub.scanner.Console;

public class Chess{

    public static void main(String[] args){
        System.out.println(Color.WHITE.getColor());
//        Scanner input = new Scanner(System.in);
        Move move = new Move(Console.getInput());
        Chessboard.print();
//        testCapture(move);
//        testPromoting(move);
//        testPawn(move);
//        testRook(move);
//        testKnight(move);
//        testBishop(move);
//        testQueen(move);
//        testKing(move);
//        testCheck(move);
        while(true){
            try{
                String fileRankFrom = Console.getInput().nextLine();
                if(fileRankFrom.equals("end")) break;
                if(fileRankFrom.equals("print")){
                    System.out.println(move);
                    continue;
                }
                if(fileRankFrom.equals("back")){
                    if(move.moveBack()) Chessboard.print();
                    else System.out.println("impossible to move back");
                    continue;
                }
                if(fileRankFrom.equals("last")){
                    System.out.println(MovesSequence.getLastMove());
                    continue;
                }
                int xFrom = fileRankFrom.charAt(0) - 'a' + 1;
                int yFrom = fileRankFrom.charAt(1) - '0';
                Square cellFrom = Chessboard.getSquare(xFrom, yFrom);
                //
                String fileRankTo = Console.getInput().nextLine();
                int xTo = fileRankTo.charAt(0) - 'a' + 1;
                int yTo = fileRankTo.charAt(1) - '0';
                Square cellTo = Chessboard.getSquare(xTo, yTo);
                //
                boolean isMoveSuccessful = move.move(cellFrom, cellTo);
                System.out.println("["+xFrom+","+yFrom+"] -> ["+xTo+","+yTo+"] = "+isMoveSuccessful);
                Chessboard.print();
            } catch(MovesSequenceIsEmptyException e){
                System.out.println(e.getMessage());
            } catch(Exception e){
                e.printStackTrace();
            }
        }
        Console.getInput().close();
    }
/*
    public static void testCapture(Move move){
        Chessboard chessboard = move.getChessboard();
        //
        Square cell = chessboard.getSquare(3,2);
        Square cell1 = chessboard.getSquare(3,4);
        boolean moveSuccess = move.move(cell, cell1);
        System.out.println("moveSuccess = "+moveSuccess);
        chessboard.print();
        //
        cell = chessboard.getSquare(2,8);
        cell1 = chessboard.getSquare(1,6);
        moveSuccess = move.move(cell, cell1);
        System.out.println("moveSuccess = "+moveSuccess);
        chessboard.print();
        //
        cell = chessboard.getSquare(3,4);
        cell1 = chessboard.getSquare(3,5);
        moveSuccess = move.move(cell, cell1);
        System.out.println("moveSuccess = "+moveSuccess);
        chessboard.print();
        //
        cell = chessboard.getSquare(2,7);
        cell1 = chessboard.getSquare(2,5);
        moveSuccess = move.move(cell, cell1);
        System.out.println("moveSuccess = "+moveSuccess);
        chessboard.print();
        //
        cell = chessboard.getSquare(3,5);
        cell1 = chessboard.getSquare(2,6);
        moveSuccess = move.move(cell, cell1);
        System.out.println("moveSuccess = "+moveSuccess);
        chessboard.print();
    }

    public static void testPromoting(Move move){
        Chessboard chessboard = move.getChessboard();
        //
        Square cell = chessboard.getSquare(3,2);
        Square cell1 = chessboard.getSquare(3,4);
        boolean moveSuccess = move.move(cell, cell1);
        System.out.println("moveSuccess = "+moveSuccess);
        chessboard.print();
        //
        cell = chessboard.getSquare(4,7);
        cell1 = chessboard.getSquare(4,5);
        moveSuccess = move.move(cell, cell1);
        System.out.println("moveSuccess = "+moveSuccess);
        chessboard.print();
        //
        cell = chessboard.getSquare(3,4);
        cell1 = chessboard.getSquare(4,5);
        moveSuccess = move.move(cell, cell1);
        System.out.println("moveSuccess = "+moveSuccess);
        chessboard.print();
        //
        cell = chessboard.getSquare(3,7);
        cell1 = chessboard.getSquare(3,6);
        moveSuccess = move.move(cell, cell1);
        System.out.println("moveSuccess = "+moveSuccess);
        chessboard.print();
        //
        cell = chessboard.getSquare(4,5);
        cell1 = chessboard.getSquare(3,6);
        moveSuccess = move.move(cell, cell1);
        System.out.println("moveSuccess = "+moveSuccess);
        chessboard.print();
        //
        cell = chessboard.getSquare(3,8);
        cell1 = chessboard.getSquare(4,7);
        moveSuccess = move.move(cell, cell1);
        System.out.println("moveSuccess = "+moveSuccess);
        chessboard.print();
        //
        cell = chessboard.getSquare(3,6);
        cell1 = chessboard.getSquare(3,7);
        moveSuccess = move.move(cell, cell1);
        System.out.println("moveSuccess = "+moveSuccess);
        chessboard.print();
        //
        cell = chessboard.getSquare(4,7);
        cell1 = chessboard.getSquare(1,4);
        moveSuccess = move.move(cell, cell1);
        System.out.println("moveSuccess = "+moveSuccess);
        chessboard.print();
        //
        cell = chessboard.getSquare(3,7);
        cell1 = chessboard.getSquare(3,8);
        moveSuccess = move.move(cell, cell1);
        System.out.println("moveSuccess = "+moveSuccess);
        chessboard.print();
    }

    public static void testCheck(Move move){
        Chessboard chessboard = move.getChessboard();
        //
        chessboard.getSquare(6, 2).setPiece(null); // remove pawn
        chessboard.getSquare(3, 2).setPiece(null); // remove pawn
        chessboard.getSquare(5, 7).setPiece(null); // remove pawn
        chessboard.getSquare(4, 7).setPiece(null); // remove pawn
        //
        Square cell = chessboard.getSquare(4,1);
        Square cell1 = chessboard.getSquare(1,4);
        boolean move1 = move.move(cell, cell1);
        //
        cell = chessboard.getSquare(4,8);
        Square cell2 = chessboard.getSquare(8,4);
        boolean move2 = move.move(cell, cell2);

        System.out.println("/move1 = "+move1+"/move2 = "+move2);
        chessboard.print();
    }

    public static void testKing(Move move){
        Chessboard chessboard = move.getChessboard();
        //
        chessboard.getSquare(5, 7).setPiece(null); // remove pawn
        //
        Square cell = chessboard.getSquare(5,8);
        Square cell2 = chessboard.getSquare(5,7);
        boolean move2 = move.move(cell, cell2);
        //
        cell = cell2;
        Square cell3 = chessboard.getSquare(6,6);
        boolean move3 = move.move(cell, cell3);
        //
        cell = cell3;
        Square cell4 = chessboard.getSquare(6,5);
        boolean move4 = move.move(cell, cell4);
        //
        cell = cell4;
        Square cell5 = chessboard.getSquare(5,4);
        boolean move5 = move.move(cell, cell5);
        //
        cell = cell5;
        Square cell6 = chessboard.getSquare(4,4);
        boolean move6 = move.move(cell, cell6);
        //
        cell = cell6;
        Square cell7 = chessboard.getSquare(3,5);
        boolean move7 = move.move(cell, cell7);
        //
        cell = cell7;
        Square cell8 = chessboard.getSquare(4,6);
        boolean move8 = move.move(cell, cell8);
        //
        cell = cell8;
        Square cell9 = chessboard.getSquare(5,6);
        boolean move9 = move.move(cell, cell9);
        //
        cell = cell9;
        Square cell10 = chessboard.getSquare(5,7);
        boolean move10 = move.move(cell, cell10);

        System.out.println("/move2 = "+move2+"/move3 = "+move3+"/move4 = "+move4+"/move5 = "+move5+"/move6 = "+move6+"/move7 = "+move7+"/move8 = "+move8+"/move9 = "+move9+"/move10 = "+move10);
        chessboard.print();
    }

    public static void testQueen(Move move){
        Chessboard chessboard = move.getChessboard();
        //
        chessboard.getSquare(3, 7).setPiece(null); // remove pawn
        //
        Square cell = chessboard.getSquare(4,8);
        Square cell2 = chessboard.getSquare(2,6);
        boolean move2 = move.move(cell, cell2);
        //
        cell = cell2;
        Square cell3 = chessboard.getSquare(2,4);
        boolean move3 = move.move(cell, cell3);
        //
        cell = cell3;
        Square cell4 = chessboard.getSquare(3,3);
        boolean move4 = move.move(cell, cell4);
        //
        cell = cell4;
        Square cell5 = chessboard.getSquare(6,3);
        boolean move5 = move.move(cell, cell5);
        //
        cell = cell5;
        Square cell6 = chessboard.getSquare(8,5);
        boolean move6 = move.move(cell, cell6);
        //
        cell = cell6;
        Square cell7 = chessboard.getSquare(7,6);
        boolean move7 = move.move(cell, cell7);
        //
        cell = cell7;
        Square cell8 = chessboard.getSquare(1,6);
        boolean move8 = move.move(cell, cell8);

        System.out.println("/move2 = "+move2+"/move3 = "+move3+"/move4 = "+move4+"/move5 = "+move5+"/move6 = "+move6+"/move7 = "+move7+"/move8 = "+move8);
        chessboard.print();
    }

    public static void testBishop(Move move){
        Chessboard chessboard = move.getChessboard();
        //
        chessboard.getSquare(4, 2).setPiece(null); // remove pawn
        //
        Square cell = chessboard.getSquare(3,1);
        Square cell2 = chessboard.getSquare(5,3);
        boolean move2 = move.move(cell, cell2);
        //
        cell = cell2;
        Square cell3 = chessboard.getSquare(3,5);
        boolean move3 = move.move(cell, cell3);
        //
        cell = cell3;
        Square cell4 = chessboard.getSquare(2,4);
        boolean move4 = move.move(cell, cell4);
        //
        cell = cell4;
        Square cell5 = chessboard.getSquare(3,3);
        boolean move5 = move.move(cell, cell5);
        //
        cell = cell5;
        Square cell6 = chessboard.getSquare(3,2);
        boolean move6 = move.move(cell, cell6);
        //
        cell = cell6;
        Square cell7 = chessboard.getSquare(4,1);
        boolean move7 = move.move(cell, cell7);

        System.out.println("/move2 = "+move2+"/move3 = "+move3+"/move4 = "+move4+"/move5 = "+move5+"/move6 = "+move6+"/move7 = "+move7);
        chessboard.print();
    }

    public static void testKnight(Move move){
        Chessboard chessboard = move.getChessboard();
        //
        Square cell = chessboard.getSquare(2,1);
        Square cell2 = chessboard.getSquare(3,3);
        boolean move2 = move.move(cell, cell2);
        //
        cell = cell2;
        Square cell3 = chessboard.getSquare(2,5);
        boolean move3 = move.move(cell, cell3);
        //
        cell = cell3;
        Square cell4 = chessboard.getSquare(1,3);
        boolean move4 = move.move(cell, cell4);
        //
        cell = cell4;
        Square cell5 = chessboard.getSquare(2,1);
        boolean move5 = move.move(cell, cell5);
        //
        cell = cell5;
        Square cell_33 = chessboard.getSquare(3,3);
        boolean move_33 = move.move(cell, cell_33);
        //
        cell = cell_33;
        Square cell6 = chessboard.getSquare(5,4);
        boolean move6 = move.move(cell, cell6);
        //
        cell = cell6;
        Square cell7 = chessboard.getSquare(3,5);
        boolean move7 = move.move(cell, cell7);
        //
        cell = cell7;
        Square cell8 = chessboard.getSquare(1,4);
        boolean move8 = move.move(cell, cell8);
        //
        cell = cell8;
        Square cell9 = chessboard.getSquare(3,3);
        boolean move9 = move.move(cell, cell9);
        //
        cell = cell9;
        Square cell10 = chessboard.getSquare(5,3);
        boolean move10 = move.move(cell, cell10);

        System.out.println("/move2 = "+move2+"/move3 = "+move3+"/move4 = "+move4+"/move5 = "+move5+"/move6 = "+move6+"/move7 = "+move7+"/move8 = "+move8+"/move9 = "+move9+"/move10 = "+move10);
        chessboard.print();
    }

    public static void testRook(Move move){
        Chessboard chessboard = move.getChessboard();
        //
        chessboard.getSquare(1, 2).setPiece(null); // remove pawn
        //
        Square cell = chessboard.getSquare(1,1);
        Square cell2 = chessboard.getSquare(1,6);
        boolean move2 = move.move(cell, cell2);
        //
        cell = cell2;
        Square cell3 = chessboard.getSquare(8,6);
        boolean move3 = move.move(cell, cell3);
        //
        cell = cell3;
        Square cell4 = chessboard.getSquare(8,3);
        boolean move4 = move.move(cell, cell4);
        //
        cell = cell4;
        Square cell5 = chessboard.getSquare(2,3);
        boolean move5 = move.move(cell, cell5);
        //
        cell = cell5;
        Square cell6 = chessboard.getSquare(3,4);
        boolean move6 = move.move(cell, cell6);
        //
        cell = cell6;
        Square cell7 = chessboard.getSquare(1,2);
        boolean move7 = move.move(cell, cell7);

        System.out.println("/move2 = "+move2+"/move3 = "+move3+"/move4 = "+move4+"/move5 = "+move5+"/move6 = "+move6+"/move7 = "+move7);
        chessboard.print();
    }

    public static void testPawn(Move move){
        Chessboard chessboard = move.getChessboard();
        //
        Square cell = chessboard.getSquare(1,7);
        Square cell2 = chessboard.getSquare(1,5);
        boolean move2 = move.move(cell, cell2);
        //
        cell = cell2;
        Square cell3 = chessboard.getSquare(1,4);
        boolean move3 = move.move(cell, cell3);
        //
        cell = cell3;
        cell.print();
        Square cell4 = chessboard.getSquare(1,3);
        cell4.print();
        boolean move4 = move.move(cell, cell4);
        //
        cell = cell4;
        Square cell5 = chessboard.getSquare(1,2);
        boolean move5 = move.move(cell, cell5);
        //
        cell = cell5;
        Square cell6 = chessboard.getSquare(1,1);
        boolean move6 = move.move(cell, cell6);
        //
        cell = cell6;
        Square cell7 = chessboard.getSquare(1,0);
        boolean move7 = move.move(cell, cell7);

        System.out.println("/move2 = "+move2+"/move3 = "+move3+"/move4 = "+move4+"/move5 = "+move5+"/move6 = "+move6+"/move7 = "+move7);
        chessboard.print();
    }
*/
}