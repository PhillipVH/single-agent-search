package za.ac.sun.cs.search.singleagent;

import java.util.Arrays;
import java.util.LinkedList;

public abstract class Board implements Comparable<Board> {

    protected short[] currentState;
    protected short[] goalState = new short[]{0, 1, 2, 3, 4, 5, 6, 7, 8};

    private Board parent;

    private int N;
    private int blankIdx;
    private int cost;

    /**
     * Initialize the internal state of the board and calculate the size of it.
     *
     * @param initialState An array of the initial tile configuration, as read from left to right and top to bottom.
     */
    public Board(short[] initialState) {
        /* We are working under the assumption that N-puzzle boards are always square. */
        N = (int) Math.sqrt(initialState.length);

        /* Initialize the current state. */
        currentState = new short[initialState.length];

        /* Load the initial state into this Board instance. */
        for (int i = 0; i < initialState.length; i++) {
            currentState[i] = initialState[i];

            /* Update the coordinates of the blank tile. */
            if (currentState[i] == 0) {
                blankIdx = i;
            }
        }
        currentState = initialState;
    }

    public int compareTo(Board other) {
        int thisValue = this.getCost();
        int otherValue = other.getCost();

        int v = thisValue - otherValue;

        return (v > 0) ? 1 : 0;
    }

    public abstract LinkedList<Board> getNeighbors();

    public Direction[] getLegalMoves() {

        Direction[] legalMoves = {Direction.UP};

        return legalMoves;
    }

    public short getAt(int i, int j) {
        return currentState[i * N + j];
    }

    public void putAt(int i, int j, short value) {
        currentState[i * N + j] = value;
    }

    public boolean isTerminal() {
        return Arrays.equals(currentState, goalState);
    }

    @Override
    public String toString() {
        StringBuilder outputBuilder = new StringBuilder();

        for (int i = 0; i < N; i++) {
            outputBuilder.append('[');
            for (int j = 0; j < N; j++) {
                outputBuilder.append(getAt(i, j));
                if (j != N - 1) {
                    outputBuilder.append(' ');
                }
            }
            outputBuilder.append(']');
            outputBuilder.append('\n');
        }

        return outputBuilder.toString();
    }

    public Integer getHeuristicCostEstimate() {
        return 1;
    }

    public abstract ExplicitBoard makeMove(Direction move);
}
