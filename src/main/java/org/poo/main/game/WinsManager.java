package org.poo.main.game;

import java.util.ArrayList;

/**
 * Singleton class to manage the wins of players.
 */
public final class WinsManager {
    private static WinsManager instance;
    private ArrayList<Integer> wins;

    private WinsManager() {
        wins = new ArrayList<>();
        wins.add(0); // Player One wins
        wins.add(0); // Player Two wins
    }

    /**
     * Gets the singleton instance of WinsManager.
     *
     * @return the singleton instance
     */
    public static synchronized WinsManager getInstance() {
        if (instance == null) {
            instance = new WinsManager();
        }
        return instance;
    }

    /**
     * Gets the list of wins.
     *
     * @return the list of wins
     */
    public ArrayList<Integer> getWins() {
        return wins;
    }

    /**
     * Increments the wins for Player One.
     */
    public void incrementPlayerOneWins() {
        wins.set(0, wins.get(0) + 1);
    }

    /**
     * Increments the wins for Player Two.
     */
    public void incrementPlayerTwoWins() {
        wins.set(1, wins.get(1) + 1);
    }
}