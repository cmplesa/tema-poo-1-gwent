package org.poo.main.decks;

import org.poo.main.cards.Card;

import java.util.ArrayList;

public class Decks {
    private int nrCardsInDeck;
    private int nrDecks;
    private ArrayList<ArrayList<Card>> decks;

    public Decks(final int nrCardsInDeck, final int nrDecks,
                 final ArrayList<ArrayList<Card>> decks) {
        this.nrCardsInDeck = nrCardsInDeck;
        this.nrDecks = nrDecks;
        this.decks = decks;
    }

    /**
     * Gets the number of cards in each deck.
     *
     * @return the number of cards in each deck
     */
    public int getNrCardsInDeck() {
        return nrCardsInDeck;
    }
    /**
     * Sets the number of cards in each deck.
     *
     * @param nrCardsInDeck the number of cards in each deck
     */
    public void setNrCardsInDeck(final int nrCardsInDeck) {
        this.nrCardsInDeck = nrCardsInDeck;
    }
    /**
     * Gets the number of decks.
     *
     * @return the number of decks
     */
    public int getNrDecks() {
        return nrDecks;
    }
    /**
     * Sets the number of decks.
     *
     * @param nrDecks the number of decks
     */
    public void setNrDecks(final int nrDecks) {
        this.nrDecks = nrDecks;
    }
    /**
     * Gets the list of decks.
     *
     * @return the list of decks
     */
    public ArrayList<ArrayList<Card>> getDecks() {
        return decks;
    }
    /**
     * Sets the list of decks.
     *
     * @param decks the list of decks
     */
    public void setDecks(final ArrayList<ArrayList<Card>> decks) {
        this.decks = decks;
    }

}
