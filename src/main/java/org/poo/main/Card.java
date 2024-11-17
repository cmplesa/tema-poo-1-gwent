package org.poo.main;

import org.poo.fileio.CardInput;
import org.poo.fileio.ActionsInput;

import java.util.ArrayList;

public class Card {
    protected int mana;
    protected int health;
    protected String description;
    protected ArrayList<String> colors;
    protected String name;
    private int hasAttacked;

    public Card() {
    }

    public Card(final int mana, final int health, final String description,
                final ArrayList<String> colors, final String name) {
        this.mana = mana;
        this.health = health;
        this.description = description;
        this.colors = colors;
        this.name = name;
    }

    /**
     * Method to see if a card dies :(((
     * @param action
     * @param playerOne
     * @param playerTwo
     * @param cardAttacked
     */
    public void cardDies(final ActionsInput action, final Player playerOne,
                         final Player playerTwo, final Card cardAttacked) {
        if (cardAttacked.getHealth() <= 0) {
            if (action.getCardAttacked().getX() == 0) {
                playerTwo.getBackRow().remove(cardAttacked);
            } else if (action.getCardAttacked().getX() == 1) {
                playerTwo.getFrontRow().remove(cardAttacked);
            } else if (action.getCardAttacked().getX() == 2) {
                playerOne.getFrontRow().remove(cardAttacked);
            } else if (action.getCardAttacked().getX() == MagicNumbers.THREE.getValue()) {
                playerOne.getBackRow().remove(cardAttacked);
            }
        }
    }

    /**
     * Method that gets the mana of a card
     * @return
     */
    public int getMana() {
        return mana;
    }

    /**
     * Method that sets the mana of a card
     * @param mana
     */
    public void setMana(final int mana) {
        this.mana = mana;
    }

    /**
     * Method that gets the health of a card
     * @return
     */
    public int getHealth() {
        return health;
    }

    /**
     * Method that sets the health of a card
     * @param health
     */
    public void setHealth(final int health) {
        this.health = health;
    }

    /**
     * Method that gets the description of a card
     * @return
     */
    public String getDescription() {
        return description;
    }

    /**
     * Method that sets the description of a card
     * @param description
     */
    public void setDescription(final String description) {
        this.description = description;
    }

    /**
     * Method that gets the colors of a card
     * @return
     */
    public ArrayList<String> getColors() {
        return colors;
    }

    /**
     * Method that sets the colors of a card
     * @param colors
     */
    public void setColors(final ArrayList<String> colors) {
        this.colors = colors;
    }

    /**
     * Method that gets the name of a card
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * Method that sets the name of a card
     * @param name
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * Method that gets the hasAttacked of a card
     * @return
     */
    public int getHasAttacked() {
        return hasAttacked;
    }

    /**
     * Method that sets the hasAttacked of a card
     * @param hasAttacked
     */
    public void setHasAttacked(final int hasAttacked) {
        this.hasAttacked = hasAttacked;
    }
}
