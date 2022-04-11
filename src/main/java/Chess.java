
import sub.Chessboard;
import sub.Square;

import java.util.Scanner;

public class Chess{

    public static void main(String[] args){
        Chessboard chessboard = new Chessboard();
        chessboard.print();
//        testPawn(chessboard);
//        testRook(chessboard);
//        testKnight(chessboard);
//        testBishop(chessboard);
//        testQueen(chessboard);
//        testKing(chessboard);
        Scanner input = new Scanner(System.in);
        while(true){
            try{
                String fileRankFrom = input.nextLine();
                if(fileRankFrom.equals("end")) break;
                int xFrom = fileRankFrom.charAt(0) - 'a' + 1;
                int yFrom = fileRankFrom.charAt(1) - '0';
                //
                String fileRankTo = input.nextLine();
                int xTo = fileRankTo.charAt(0) - 'a' + 1;
                int yTo = fileRankTo.charAt(1) - '0';
                //
                Square cellFrom = chessboard.getSquare(xFrom, yFrom);
                Square cellTo = chessboard.getSquare(xTo, yTo);
                //
                boolean move = chessboard.move(cellFrom, cellTo);
                System.out.println("["+xFrom+","+yFrom+"] -> ["+xTo+","+yTo+"] = "+move);
                chessboard.print();
            } catch(Exception e){
                System.out.println(e.getMessage());
            }
        }
        input.close();
    }

    public static void testKing(Chessboard chessboard){
        chessboard.getSquare(5, 7).setPiece(null); // remove pawn
        //
        Square cell = chessboard.getSquare(5,8);
        Square cell2 = chessboard.getSquare(5,7);
        boolean move2 = chessboard.move(cell, cell2);
        //
        cell = cell2;
        Square cell3 = chessboard.getSquare(6,6);
        boolean move3 = chessboard.move(cell, cell3);
        //
        cell = cell3;
        Square cell4 = chessboard.getSquare(6,5);
        boolean move4 = chessboard.move(cell, cell4);
        //
        cell = cell4;
        Square cell5 = chessboard.getSquare(5,4);
        boolean move5 = chessboard.move(cell, cell5);
        //
        cell = cell5;
        Square cell6 = chessboard.getSquare(4,4);
        boolean move6 = chessboard.move(cell, cell6);
        //
        cell = cell6;
        Square cell7 = chessboard.getSquare(3,5);
        boolean move7 = chessboard.move(cell, cell7);
        //
        cell = cell7;
        Square cell8 = chessboard.getSquare(4,6);
        boolean move8 = chessboard.move(cell, cell8);
        //
        cell = cell8;
        Square cell9 = chessboard.getSquare(5,6);
        boolean move9 = chessboard.move(cell, cell9);
        //
        cell = cell9;
        Square cell10 = chessboard.getSquare(5,7);
        boolean move10 = chessboard.move(cell, cell10);

        System.out.println("/move2 = "+move2+"/move3 = "+move3+"/move4 = "+move4+"/move5 = "+move5+"/move6 = "+move6+"/move7 = "+move7+"/move8 = "+move8+"/move9 = "+move9+"/move10 = "+move10);
        chessboard.print();
    }

    public static void testQueen(Chessboard chessboard){
        Square cell = chessboard.getSquare(4,8);
        Square cell2 = chessboard.getSquare(2,6);
        boolean move2 = chessboard.move(cell, cell2);
        //
        cell = cell2;
        Square cell3 = chessboard.getSquare(2,4);
        boolean move3 = chessboard.move(cell, cell3);
        //
        cell = cell3;
        Square cell4 = chessboard.getSquare(3,3);
        boolean move4 = chessboard.move(cell, cell4);
        //
        cell = cell4;
        Square cell5 = chessboard.getSquare(6,3);
        boolean move5 = chessboard.move(cell, cell5);
        //
        cell = cell5;
        Square cell6 = chessboard.getSquare(8,5);
        boolean move6 = chessboard.move(cell, cell6);
        //
        cell = cell6;
        Square cell7 = chessboard.getSquare(7,6);
        boolean move7 = chessboard.move(cell, cell7);
        //
        cell = cell7;
        Square cell8 = chessboard.getSquare(1,6);
        boolean move8 = chessboard.move(cell, cell8);

        System.out.println("/move2 = "+move2+"/move3 = "+move3+"/move4 = "+move4+"/move5 = "+move5+"/move6 = "+move6+"/move7 = "+move7+"/move8 = "+move8);
        chessboard.print();
    }

    public static void testBishop(Chessboard chessboard){
        Square cell = chessboard.getSquare(3,1);
        Square cell2 = chessboard.getSquare(5,3);
        boolean move2 = chessboard.move(cell, cell2);
        //
        cell = cell2;
        Square cell3 = chessboard.getSquare(3,5);
        boolean move3 = chessboard.move(cell, cell3);
        //
        cell = cell3;
        Square cell4 = chessboard.getSquare(2,4);
        boolean move4 = chessboard.move(cell, cell4);
        //
        cell = cell4;
        Square cell5 = chessboard.getSquare(3,3);
        boolean move5 = chessboard.move(cell, cell5);
        //
        cell = cell5;
        Square cell6 = chessboard.getSquare(3,2);
        boolean move6 = chessboard.move(cell, cell6);
        //
        cell = cell6;
        Square cell7 = chessboard.getSquare(4,1);
        boolean move7 = chessboard.move(cell, cell7);

        System.out.println("/move2 = "+move2+"/move3 = "+move3+"/move4 = "+move4+"/move5 = "+move5+"/move6 = "+move6+"/move7 = "+move7);
        chessboard.print();
    }

    public static void testKnight(Chessboard chessboard){
        Square cell = chessboard.getSquare(2,1);
        Square cell2 = chessboard.getSquare(3,3);
        boolean move2 = chessboard.move(cell, cell2);
        //
        cell = cell2;
        Square cell3 = chessboard.getSquare(2,5);
        boolean move3 = chessboard.move(cell, cell3);
        //
        cell = cell3;
        Square cell4 = chessboard.getSquare(1,3);
        boolean move4 = chessboard.move(cell, cell4);
        //
        cell = cell4;
        Square cell5 = chessboard.getSquare(2,1);
        boolean move5 = chessboard.move(cell, cell5);
        //
        cell = cell5;
        Square cell_33 = chessboard.getSquare(3,3);
        boolean move_33 = chessboard.move(cell, cell_33);
        //
        cell = cell_33;
        Square cell6 = chessboard.getSquare(5,4);
        boolean move6 = chessboard.move(cell, cell6);
        //
        cell = cell6;
        Square cell7 = chessboard.getSquare(3,5);
        boolean move7 = chessboard.move(cell, cell7);
        //
        cell = cell7;
        Square cell8 = chessboard.getSquare(1,4);
        boolean move8 = chessboard.move(cell, cell8);
        //
        cell = cell8;
        Square cell9 = chessboard.getSquare(3,3);
        boolean move9 = chessboard.move(cell, cell9);
        //
        cell = cell9;
        Square cell10 = chessboard.getSquare(5,3);
        boolean move10 = chessboard.move(cell, cell10);

        System.out.println("/move2 = "+move2+"/move3 = "+move3+"/move4 = "+move4+"/move5 = "+move5+"/move6 = "+move6+"/move7 = "+move7+"/move8 = "+move8+"/move9 = "+move9+"/move10 = "+move10);
        chessboard.print();
    }

    public static void testRook(Chessboard chessboard){
        chessboard.getSquare(1, 2).setPiece(null); // remove pawn
        //
        Square cell = chessboard.getSquare(1,1);
        Square cell2 = chessboard.getSquare(1,6);
        boolean move2 = chessboard.move(cell, cell2);
        //
        cell = cell2;
        Square cell3 = chessboard.getSquare(8,6);
        boolean move3 = chessboard.move(cell, cell3);
        //
        cell = cell3;
        Square cell4 = chessboard.getSquare(8,3);
        boolean move4 = chessboard.move(cell, cell4);
        //
        cell = cell4;
        Square cell5 = chessboard.getSquare(2,3);
        boolean move5 = chessboard.move(cell, cell5);
        //
        cell = cell5;
        Square cell6 = chessboard.getSquare(3,4);
        boolean move6 = chessboard.move(cell, cell6);
        //
        cell = cell6;
        Square cell7 = chessboard.getSquare(1,2);
        boolean move7 = chessboard.move(cell, cell7);

        System.out.println("/move2 = "+move2+"/move3 = "+move3+"/move4 = "+move4+"/move5 = "+move5+"/move6 = "+move6+"/move7 = "+move7);
        chessboard.print();
    }

    public static void testPawn(Chessboard chessboard){
        Square cell = chessboard.getSquare(1,7);
        Square cell2 = chessboard.getSquare(1,5);
        boolean move2 = chessboard.move(cell, cell2);
        //
        cell = cell2;
        Square cell3 = chessboard.getSquare(1,4);
        boolean move3 = chessboard.move(cell, cell3);
        //
        cell = cell3;
        cell.print();
        Square cell4 = chessboard.getSquare(1,3);
        cell4.print();
        boolean move4 = chessboard.move(cell, cell4);
        //
        cell = cell4;
        Square cell5 = chessboard.getSquare(1,2);
        boolean move5 = chessboard.move(cell, cell5);
        //
        cell = cell5;
        Square cell6 = chessboard.getSquare(1,1);
        boolean move6 = chessboard.move(cell, cell6);
        //
        cell = cell6;
        Square cell7 = chessboard.getSquare(1,0);
        boolean move7 = chessboard.move(cell, cell7);

        System.out.println("/move2 = "+move2+"/move3 = "+move3+"/move4 = "+move4+"/move5 = "+move5+"/move6 = "+move6+"/move7 = "+move7);
        chessboard.print();
    }

}
