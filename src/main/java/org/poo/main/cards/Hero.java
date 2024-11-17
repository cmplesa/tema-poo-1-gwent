package org.poo.main.cards;

import org.poo.fileio.CardInput;

import java.util.ArrayList;

public final class Hero extends Card {

    private static final int MAX_HP = 30;
    private int hasAtacked;

    public Hero() {
    }

    public Hero(final int mana, final int health, final String description, final String name,
                final int attackDamage, final ArrayList<String> colors) {
        super(mana, health, description, colors, name);
        this.setHealth(MAX_HP);
    }

    public Hero(final CardInput card) {
        this.setHealth(MAX_HP);
        this.setMana(card.getMana());
        this.setDescription(card.getDescription());
        this.setColors(card.getColors());
        this.setName(card.getName());
    }

    /**
     * Uses the hero's ability on the given row of cards.
     *
     * @param row the row of cards
     * @param hero the hero using the ability
     */
    public void useAbility(final ArrayList<Card> row, final Hero hero) {
        switch (name) {
            case "Lord Royce":
                applySubZero(row, hero);
                break;
            case "Empress Thorina":
                applyLowBlow(row, hero);
                break;
            case "King Mudface":
                applyEarthBorn(row, hero);
                break;
            case "General Kocioraw":
                applyBloodThirst(row, hero);
                break;
            default:
                // Default case to handle unexpected values
                break;
        }
    }

    private void applySubZero(final ArrayList<Card> row, final Hero hero) {
        for (Card card : row) {
            Minion minion = (Minion) card;
            minion.setFrozen(true);
        }
        hero.setHasAtacked(1);
    }

    private void applyLowBlow(final ArrayList<Card> row, final Hero hero) {
        int max = row.get(0).getHealth();
        Card maxCard = row.get(0);
        for (Card card : row) {
            if (card.getHealth() > max) {
                max = card.getHealth();
                maxCard = card;
            }
        }
        row.remove(maxCard);
        hero.setHasAtacked(1);
    }

    private void applyEarthBorn(final ArrayList<Card> row, final Hero hero) {
        for (Card card : row) {
            Minion minion = (Minion) card;
            minion.setHealth(minion.getHealth() + 1);
        }
        hero.setHasAtacked(1);
    }

    private void applyBloodThirst(final ArrayList<Card> row, final Hero hero) {
        for (Card card : row) {
            Minion minion = (Minion) card;
            minion.setAttackDamage(minion.getAttackDamage() + 1);
        }
        hero.setHasAtacked(1);
    }

    /**
     * Gets the attack status of the hero.
     *
     * @return the attack status
     */
    public int getHasAtacked() {
        return hasAtacked;
    }

    /**
     * Sets the attack status of the hero.
     *
     * @param hasAtacked the attack status
     */
    public void setHasAtacked(final int hasAtacked) {
        this.hasAtacked = hasAtacked;
    }
}
