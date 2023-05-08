import java.util.*;
import java.util.logging.Logger;

public class TicTacToe {

    private final static Logger LOG = Logger.getLogger(TicTacToe.class.getName());
    private final static int USER_TILE = 1;
    private final static int ENEMY_TILE = 2;
    private final static int DEFAULT_BLOCKING_INDEX = 10;
    private final List<Integer> TILES;
    private int userPickedIndex;

    private final Random rand = new Random();

    public TicTacToe() {
        this.TILES = Arrays.asList(new Integer[9]);
        Collections.fill(TILES, 0);
    }

    public void makeTurn(int indexToOpen) {
        if (indexToOpen <= 0 || indexToOpen > 9) throw new IllegalArgumentException("Please select only between 1 and 9");
        int targetIndex = indexToOpen - 1;
        // Check if tile is already open
        if(TILES.get(targetIndex) == USER_TILE || TILES.get(targetIndex) == ENEMY_TILE) throw new IllegalArgumentException("This tile is already open");

        this.userPickedIndex = targetIndex; // Getting the player choosed index to be used in getComputerIndex decision
        TILES.set(targetIndex, USER_TILE);
    }

    public void computerTurn(int indexToOpen) {
        if(isDraw()) return;
        if (indexToOpen < 0 || indexToOpen > 9) throw new IllegalArgumentException("Please select only between 1 and 9");

        int blockingIndex = this.getBlockingIndex();
        if (blockingIndex != DEFAULT_BLOCKING_INDEX && !this.isTileAlreadyOpen(blockingIndex)) {
            TILES.set(blockingIndex, ENEMY_TILE);
            LOG.fine("USER BLOCKED!");
            return;
        }

        if (this.isTileAlreadyOpen(indexToOpen)) {
            computerTurn(this.getComputerIndex());
            LOG.fine("Computer is picking another index");
            return;
        }

        TILES.set(indexToOpen, ENEMY_TILE);
    }

    public boolean isTileAlreadyOpen(int index) {
        return TILES.get(index) == USER_TILE || TILES.get(index) == ENEMY_TILE;
    }

    public boolean isDraw() {
        return TILES.stream().noneMatch(tile -> tile == 0) || TILES.stream().filter(tile -> tile == 0).count() <= 1;
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
                .anyMatch(indexes -> hasPatternOf(USER_TILE, indexes) || hasPatternOf(ENEMY_TILE, indexes));
    }

    public int getComputerIndex() {
        List<List<Integer>> combinations = this.getCombinations();

        // Get the possible player winning combinations
        List<List<Integer>> possiblePlayerCombinations = combinations.stream()
                .filter(list -> list.contains(this.userPickedIndex))
                .toList();

        // Pick a combination pattern based on player picked index
        int pickANumber = rand.nextInt(possiblePlayerCombinations.size() - 1);
        List<Integer> possibleCombination = possiblePlayerCombinations.get(pickANumber);

        // Pick a index
        int randomIndex = rand.nextInt(possibleCombination.size() - 1);
        int pickedIndex = possibleCombination.get(randomIndex);
        LOG.fine("Computer picked index " + pickedIndex);
        return pickedIndex;
    }

    public int getBlockingIndex() {
        List<List<Integer>> combinations = this.getCombinations();
        int blockingIndex = DEFAULT_BLOCKING_INDEX;
        for (List<Integer> indexes : combinations) {
            long patternCount = indexes.stream()
                    .map(TILES::get) // return the contained element of the index
                    .filter(tile -> tile == USER_TILE) // check if theres a winning pattern by counting the USER_TILE of the combination index contain element
                    .count();

            // Get the remaining index of pattern to block the winning pattern
            if (patternCount == 2) {
                blockingIndex = indexes.stream()
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
