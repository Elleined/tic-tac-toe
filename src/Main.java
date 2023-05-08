import java.util.Locale;
import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        TicTacToe game = new TicTacToe();
        Scanner in = new Scanner(System.in);

        game.showTiles();
        while(true) {
            System.out.print("Open a tile: ");
            int userInput = in.nextInt();

            game.makeTurn(userInput);
            if(game.hasWinner()) {
                System.out.println("===Player Wins===");
                break;
            }

            game.computerTurn(game.getComputerIndex());
            game.showTiles();
            if(game.hasWinner()) {
                System.out.println("===Computer Wins===");
                break;
            }

            if(game.isDraw()) {
                System.out.println("===DRAW===");
                break;
            }
        }

    }
}
