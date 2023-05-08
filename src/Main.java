import java.util.*;
import java.util.stream.*;
public class Main {
    public static void main(String[] args) {
        TicTacToe game = new TicTacToe();
        Scanner in = new Scanner(System.in);
        while(true) {
            System.out.print("Open a tile: ");
            int userInput = in.nextInt();
            game.makeTurn(userInput);

            if(game.hasWinner()) break;
            game.computerTurn(game.randNum());
            game.getBlockingIndex();
            game.showTiles();
            if(game.hasWinner()) break;
            if(game.isDraw()) break;

        }
    }
}
