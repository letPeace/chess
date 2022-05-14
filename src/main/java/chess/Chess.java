package chess;

import sub.chessboard.Chessboard;
import sub.chessboard.Square;
import sub.exceptions.MovesSequenceException;
import sub.exceptions.SquareException;
import sub.move.Checkmate;
import sub.move.Move;
import sub.move.MovesSequence;
import sub.scanner.Console;
import test.Test;

public class Chess{

    public static void main(String[] args){

//        Test.testAll();
        Chessboard.print();

        while(true){
            try{
                String fileRankFrom = Console.getInput().nextLine();
                if(fileRankFrom.equals("end")) break;
                if(fileRankFrom.equals("print")){
                    MovesSequence.print();
                    continue;
                }
                if(fileRankFrom.equals("back")){
                    if(Move.moveBack()) Chessboard.print();
                    else System.out.println("impossible to move back");
                    continue;
                }
                if(fileRankFrom.equals("last")){
                    System.out.println(MovesSequence.getLastMove());
                    continue;
                }
                if(fileRankFrom.equals("reset")){
                    Chessboard.reset();
                    Chessboard.print();
                    continue;
                }
                int xFrom = fileRankFrom.charAt(0) - 'a' + 1;
                int yFrom = fileRankFrom.charAt(1) - '0';
                Square squareFrom = Chessboard.getSquare(xFrom, yFrom);
                //
                String fileRankTo = Console.getInput().nextLine();
                int xTo = fileRankTo.charAt(0) - 'a' + 1;
                int yTo = fileRankTo.charAt(1) - '0';
                Square squareTo = Chessboard.getSquare(xTo, yTo);
                //
                boolean isMoveSuccessful = Move.move(squareFrom, squareTo);
                System.out.println("["+xFrom+","+yFrom+"] -> ["+xTo+","+yTo+"] = "+isMoveSuccessful);
                Chessboard.print();
                if(Checkmate.getCheckmate()) break;
            } catch(MovesSequenceException e){
                System.out.println(e.getMessage());
            } catch(SquareException e){
                System.out.println("Chessboard error: "+e.getMessage());
                return;
            }
        }
        Console.getInput().close();
    }

}
