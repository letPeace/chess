package test;

import sub.chessboard.Chessboard;
import sub.chessboard.Square;
import sub.exceptions.SquareNullException;
import sub.exceptions.TestFailException;
import sub.move.Move;

import java.util.ArrayList;
import java.util.Collections;

public class Test{

    private static final ArrayList<String> moveSequence = new ArrayList<>();

    private static ArrayList<String> getMoveSequence(){
        return moveSequence;
    }

    public static void clearMoveSequence(){
        getMoveSequence().clear();
    }

    public static void testAll(){
        String[] testArguments = new String[]{"pawn","rook","knight","bishop","queen","king","check","promoting","capture"};
        for(String argument : testArguments) {
            try{
                Test.test(argument);
                System.out.println(argument + " -> success");
            } catch (TestFailException e) {
                System.out.println("Test \""+argument+"\" failed: "+e.getMessage());
            } catch (NumberFormatException e) {
                System.out.println("Incorrect Integers: " + e.getMessage());
                return;
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("Lack of Integers: " + e.getMessage());
                return;
            } catch (SquareNullException e) {
                System.out.println("Chessboard error: " + e.getMessage());
                return;
            } finally{
                Test.clearMoveSequence();
                Chessboard.reset();
            }
        }
    }

    public static boolean test(String testObject) throws NullPointerException, NumberFormatException, ArrayIndexOutOfBoundsException, SquareNullException{
        // Structure: [xFrom yFrom]+[xTo yTo]+[expected result]+[do not change turn]
        try{
            if(testObject.equals("pawn")){
                Collections.addAll(getMoveSequence(),"17 15 0","12 14 1","17 15 1","14 15 0","14 25 0","22 23 1","27 25 1","14 15 0","14 25 1","15 14 1");
            } else if(testObject.equals("rook")){
                Chessboard.getSquare(1, 2).removePiece();
                Collections.addAll(getMoveSequence(),"17 16 0","11 17 1","18 16 0 1","17 27 1 1","27 22 0","27 16 0","27 23 1","18 13 1","23 13 1");
            } else if(testObject.equals("knight")){
                Collections.addAll(getMoveSequence(),"17 16 0","21 33 1 1","33 54 1 1","54 35 1 1","35 14 1 1","14 33 1 1","33 45 1 1","45 37 1 1","37 25 1 1","25 33 1 1","33 35 0","33 24 0","33 43 0","33 12 0 1","78 86 1");
            } else if(testObject.equals("bishop")){
                Chessboard.getSquare(4, 2).removePiece();
                Collections.addAll(getMoveSequence(),"17 16 0","31 86 1 1","86 68 0","86 77 1 1","77 22 0","77 33 1","68 77 1","33 88 0","33 77 1");
            } else if(testObject.equals("queen")){
                Chessboard.getSquare(3, 7).removePiece();
                Move.setOppositeTurn();
                Collections.addAll(getMoveSequence(),"48 26 1 1","26 22 1 1","22 31 1 1","31 41 1 1","41 52 1 1","52 57 0","52 56 1 1","56 47 0","56 46 1 1","46 37 1 1","37 31 0");
            } else if(testObject.equals("king")){
                Chessboard.getSquare(5, 7).removePiece();
                Move.setOppositeTurn();
                Collections.addAll(getMoveSequence(),"58 56 0","58 48 0","58 57 1 1","57 46 1 1","46 55 1 1","55 65 1 1","65 76 1 1","76 66 1 1","66 44 0","66 57 1 1","57 58 1");
            } else if(testObject.equals("check")){
                Chessboard.getSquare(3, 2).removePiece();
                Chessboard.getSquare(6, 2).removePiece();
                Chessboard.getSquare(4, 7).removePiece();
                Chessboard.getSquare(5, 7).removePiece();
                Collections.addAll(getMoveSequence(),"41 14 1","48 84 0","48 42 0","58 57 1","51 62 1","48 44 1","62 53 0","14 58 0","52 53 1 1","53 54 0","14 47 1","57 56 0","57 47 1");
            } else if(testObject.equals("promoting")){
                Collections.addAll(getMoveSequence(),"32 34 1","47 45 1","34 45 1","38 47 1","45 46 1 1","46 37 1 1","37 38 1");
            } else if(testObject.equals("capture")){
                Collections.addAll(getMoveSequence(),"32 34 1 1","34 35 1","27 25 1","35 26 1");
            } else{ // заглушка
                return true;
            }
        } catch(TestFailException testFailException){
            throw testFailException;
        }
        return justDoIt();
    }

    private static boolean justDoIt() throws NullPointerException, NumberFormatException, ArrayIndexOutOfBoundsException, SquareNullException{
        for(String moveData : getMoveSequence()){
            try {
                String[] moveDataArray = moveData.split(" ");
                // FROM
                int coordinateFrom = Integer.parseInt(moveDataArray[0]);
                int positionXFrom = coordinateFrom/10, positionYFrom = coordinateFrom%10;
                Square squareFrom = Chessboard.getSquare(positionXFrom,positionYFrom);
                // TO
                int coordinateTo = Integer.parseInt(moveDataArray[1]);
                int positionXTo = coordinateTo/10, positionYTo = coordinateTo%10;
                Square squareTo = Chessboard.getSquare(positionXTo,positionYTo);
                // RESULT
                boolean result = Integer.parseInt(moveDataArray[2]) != 0;
                // MOVE
                boolean moveSuccess = Move.move(squareFrom, squareTo);
                if(moveSuccess != result){
                    throw new TestFailException(coordinateFrom+" -> "+coordinateTo+" must be "+result);
                }
                // TURN
                if(moveDataArray.length == 4 && Integer.parseInt(moveDataArray[3]) == 1) Move.setOppositeTurn();
                // PRINT
//                Chessboard.print();
            } catch(NumberFormatException e){
                throw new NumberFormatException("{"+moveData+"}");
            } catch(ArrayIndexOutOfBoundsException e){
                throw new ArrayIndexOutOfBoundsException("{"+moveData+"}");
            } catch(SquareNullException e){
                throw new SquareNullException("{"+e.getMessage()+"}");
            }
        }
        return true;
    }

}
