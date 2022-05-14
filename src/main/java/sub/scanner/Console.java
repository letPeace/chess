package sub.scanner;

import sub.enums.PieceName;

import java.util.Scanner;

public class Console{

    private static final Scanner input = new Scanner(System.in);

    public static Scanner getInput(){
        return input;
    }

    public static PieceName choicePieceName(){
        while(true){
            try{
                System.out.print("Enter a name of piece: ");
                String pieceName = Console.getInput().nextLine().trim();
                if(pieceName.equals(PieceName.ROOK.getName())) return PieceName.ROOK;
                if(pieceName.equals(PieceName.KNIGHT.getName())) return PieceName.KNIGHT;
                if(pieceName.equals(PieceName.BISHOP.getName())) return PieceName.BISHOP;
                if(pieceName.equals(PieceName.QUEEN.getName())) return PieceName.QUEEN;
                System.out.println("Enter a correct name of piece!");
            } catch(Exception e){
                e.printStackTrace();
            }
        }
    }

}
