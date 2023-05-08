import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        TicTacToe game = new TicTacToe();
        Scanner in = new Scanner(System.in);
        while(true) {
            System.out.print("Open a tile: ");
            int userInput = in.nextInt();
            game.makeTurn(userInput);
            if(game.hasWinner()) break;

            game.computerTurn(game.getComputerIndex());

            game.showTiles();

            if(game.hasWinner()) break;
            if(game.isDraw()) break;
        }
    }
}
