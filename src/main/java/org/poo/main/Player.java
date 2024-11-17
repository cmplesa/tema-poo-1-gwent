package org.poo.main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Player {
    private Decks playerDecks;
    private int playerDeckIdx;
    private int mana;
    private ArrayList<Card> hand;
    private ArrayList<Card> frontRow;
    private ArrayList<Card> backRow;
    private Hero playerHero;
    private boolean endTurn;

    public Player() {

    }

    public Player(final Decks playerDecks, final int playerIdx, final Hero playerHero) {
        this.playerDecks = playerDecks;
        this.mana = 0;
        this.hand = new ArrayList<>();
        this.frontRow = new ArrayList<>();
        this.backRow = new ArrayList<>();
        this.playerHero = playerHero;
        this.endTurn = false;
        this.playerDeckIdx = playerIdx;
    }

    /**
     * Method that takes the card in the position x, y
     * @param playerOne
     * @param playerTwo
     * @param x
     * @param y
     * @return
     */
    public Card getPlayerCard(final Player playerOne, final Player playerTwo,
                              final int x, final int y) {
        Card playerCard = null;
        if (x == 0) {
            if (playerTwo.getBackRow().size() > y) {
                playerCard = playerTwo.getBackRow().get(y);
            }
        } else if (x == 1) {
            if (playerTwo.getFrontRow().size() > y) {
                playerCard = playerTwo.getFrontRow().get(y);
            }
        } else if (x == 2) {
            if (playerOne.getFrontRow().size() > y) {
                playerCard = playerOne.getFrontRow().get(y);
            }
        } else {
            if (playerOne.getBackRow().size() > y) {
                playerCard = playerOne.getBackRow().get(y);
            }
        }
        return playerCard;
    }

    /**
     * Method that returns the deck of the player
     * @param idx
     * @param player
     * @param game
     * @return
     */
    public ArrayList<Card> getPlayerDeck(final int idx, final Player player, final Game game) {
        ArrayList<Card> deck = player.getPlayerDecks().getDecks().get(idx);
        Collections.shuffle(deck, new Random(game.getShuffleSeed()));
        return deck;
    }

    /**
     * Method that returns player's deck
     * @return
     */
    public Decks getPlayerDecks() {
        return playerDecks;
    }

    /**
     * Method that sets player's deck
     * @param playerDecks
     */
    public void setPlayerDecks(final Decks playerDecks) {
        this.playerDecks = playerDecks;
    }

    /**
     * Method that returns player's mana
     * @return
     */
    public int getMana() {
        return mana;
    }

    /**
     * Method that sets player's mana
     * @param mana
     */
    public void setMana(final int mana) {
        this.mana = mana;
    }

    /**
     * Method that returns player's hand
     * @return
     */
    public ArrayList<Card> getHand() {
        return hand;
    }

    /**
     * Method that sets player's hand
     * @param hand
     */
    public void setHand(final ArrayList<Card> hand) {
        this.hand = hand;
    }

    /**
     * Method that returns player's front row
     * @return
     */
    public ArrayList<Card> getFrontRow() {
        return frontRow;
    }

    /**
     * Method that sets player's front row
     * @param frontRow
     */
    public void setFrontRow(final ArrayList<Card> frontRow) {
        this.frontRow = frontRow;
    }

    /**
     * Method that returns player's back row
     * @return
     */
    public ArrayList<Card> getBackRow() {
        return backRow;
    }

    /**
     * Method that sets player's back row
     * @param backRow
     */
    public void setBackRow(final ArrayList<Card> backRow) {
        this.backRow = backRow;
    }

    /**
     * Method that returns player's hero
     * @return
     */
    public Hero getPlayerHero() {
        return playerHero;
    }

    /**
     * Method that sets player's hero
     * @param playerHero
     */
    public void setPlayerHero(final Hero playerHero) {
        this.playerHero = playerHero;
    }

    /**
     * Method that returns if the turn is over
     * @return
     */
    public boolean isEndTurn() {
        return endTurn;
    }

    /**
     * Method that sets if the turn is over
     * @param endTurn
     */
    public void setEndTurn(final boolean endTurn) {
        this.endTurn = endTurn;
    }

    /**
     * Method that returns the index of the player's deck
     * @return
     */
    public int getPlayerDeckIdx() {
        return playerDeckIdx;
    }

    /**
     * Method that sets the index of the player's deck
     * @param playerDeckIdx
     */
    public void setPlayerDeckIdx(final int playerDeckIdx) {
        this.playerDeckIdx = playerDeckIdx;
    }
}
