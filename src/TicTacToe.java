import java.util.*;

public class TicTacToe {

    private final static int USER_TILE = 1;
    private final static int ENEMY_TILE = 2;
    private final List<Integer> TILES;

    public TicTacToe() {
        this.TILES = Arrays.asList(new Integer[9]);
        Collections.fill(TILES, 0);
    }

    public void makeTurn(int indexToOpen) {
        if (indexToOpen <= 0 || indexToOpen > 9) throw new IllegalArgumentException("Please select only between 1 and 9");
        int targetIndex = indexToOpen - 1;
        // Check if tile is already open
        if(TILES.get(targetIndex) == USER_TILE || TILES.get(targetIndex) == ENEMY_TILE) throw new IllegalArgumentException("This tile is already open");
        TILES.set(targetIndex, USER_TILE);
    }

    public int randNum() {
        return new Random().nextInt(1, 9);
    }

    public void computerTurn(int indexToOpen) {
        if(isDraw()) return;
        if (indexToOpen <= 0 || indexToOpen > 9) throw new IllegalArgumentException("Please select only between 1 and 9");
        int targetIndex = indexToOpen - 1;
        // Check if tiles is already open
        if(TILES.get(targetIndex) == USER_TILE || TILES.get(targetIndex) == ENEMY_TILE) {
            this.computerTurn(randNum());
            return;
        }
        TILES.set(targetIndex, ENEMY_TILE);
    }

    public boolean isDraw() {
        return TILES.stream().noneMatch(tile -> tile == 0);
    }

    public List<List<Integer>> getCombinations() {
        return List.of(
                Arrays.asList(0, 1, 2),
                Arrays.asList(3, 4, 5),
                Arrays.asList(6, 7, 8),
                Arrays.asList(0, 3, 6),
                Arrays.asList(1, 4, 7),
                Arrays.asList(2, 5, 8),
                Arrays.asList(0, 4, 8),
                Arrays.asList(2, 4, 6)
        );
    }

    public boolean hasWinner() {
        return this.getCombinations()
                .stream()
                .anyMatch(indexes -> hasPatternOf(USER_TILE, indexes)
                        || hasPatternOf(ENEMY_TILE, indexes)
                );
    }

    public int getBlockingIndex() {
        List<List<Integer>> combinations = this.getCombinations();
        int blockingIndex = 0;
        for (List<Integer> indexes : combinations) {
            long patternCount = indexes.stream()
                    .map(TILES::get) // return the contained element of the index
                    .filter(tile -> tile == USER_TILE) // check if theres a winning pattern by counting the USER_TILE of the combination index contain element
                    .count();

            // Get the remaining index of pattern to block the winning pattern
            if (patternCount == 2) {
                blockingIndex =   indexes.stream()
                        .filter(index -> TILES.get(index) != USER_TILE)
                        .findFirst()
                        .orElseThrow();
            }
        }
        return blockingIndex;
    }

    public boolean hasPatternOf(int tileValueToMatch, List<Integer> indexes) {
        return indexes.stream()
                .map(TILES::get)
                .allMatch(value -> value == tileValueToMatch);
    }

    public void showTiles() {
        TILES.subList(0, 3).forEach(System.out::print);
        System.out.println();
        TILES.subList(3, 6).forEach(System.out::print);
        System.out.println();
        TILES.subList(6, 9).forEach(System.out::print);
        System.out.println();
    }
}
