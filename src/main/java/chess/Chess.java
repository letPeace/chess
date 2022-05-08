package chess;

import sub.chessboard.Chessboard;
import sub.chessboard.Square;
import sub.exceptions.MovesSequenceIsEmptyException;
import sub.move.Move;
import sub.move.MovesSequence;
import sub.scanner.Console;
import test.Test;

public class Chess{

    public static void main(String[] args){
        Chessboard.print();

        try{
            Test.test("");
        } catch(NullPointerException e){
            System.out.println("Chessboard error.");
            return;
        } catch(NumberFormatException e){
            System.out.println("Incorrect Integers: "+e.getMessage());
            return;
        } catch(ArrayIndexOutOfBoundsException e){
            System.out.println("Lack of Integers: "+e.getMessage());
            return;
        }

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
            } catch(MovesSequenceIsEmptyException e){
                System.out.println(e.getMessage());
            } catch(Exception e){
                e.printStackTrace();
            }
        }
        Console.getInput().close();
    }

}
