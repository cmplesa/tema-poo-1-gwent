package org.poo.main;

import org.poo.fileio.CardInput;
import java.util.ArrayList;

public final class Minion extends Card {

    private int attackDamage;
    private int tank;
    private int ability;
    private boolean frozen;
    private int usedMinion;

    public Minion(final CardInput cardInput) {
        this.setDescription(cardInput.getDescription());
        this.setColors(cardInput.getColors());
        this.setName(cardInput.getName());
        this.setHealth(cardInput.getHealth());
        this.setMana(cardInput.getMana());
        this.setAttackDamage(cardInput.getAttackDamage());
        usedMinion = 0;
        frozen = false;
        switch (name) {
            case "Golliath":
            case "Warden":
                tank = 1;
                ability = 0;
                break;
            case "Sentinel":
            case "Berserker":
                tank = 0;
                ability = 0;
                break;
            case "The Ripper":
            case "Miraj":
            case "The Cursed One":
            case "Disciple":
                tank = 0;
                ability = 1;
                break;
            default:
                // Default case to handle unexpected values
                break;
        }
    }

    public Minion(final int mana, final int attackDamage, final String description,
                  final String name, final int health, final ArrayList<String> colors) {
        super(mana, health, description, colors, name);
        this.attackDamage = attackDamage;
        frozen = false;
        switch (name) {
            case "Golliath":
            case "Warden":
                tank = 1;
                ability = 0;
                break;
            case "Sentinel":
            case "Berserker":
                tank = 0;
                ability = 0;
                break;
            case "The Ripper":
            case "Miraj":
            case "The Cursed One":
            case "Disciple":
                tank = 0;
                ability = 1;
                break;
            default:
                // Default case to handle unexpected values
                break;
        }
        usedMinion = 0;
    }

    /**
     * Uses the minion's ability on the target minion.
     *
     * @param target the target minion
     * @param special the special minion
     */
    public void useAbility(final Minion target, final Minion special) {
        switch (name) {
            case "The Ripper":
                applyWeakNess(target, special);
                break;
            case "Miraj":
                applySkyjack(target, special);
                break;
            case "The Cursed One":
                applyShapeShift(target, special);
                break;
            case "Disciple":
                applyGodsPlan(target, special);
                break;
            default:
                // Default case to handle unexpected values
                break;
        }
    }

    private void applyWeakNess(final Minion target, final Minion special) {
        if (target.getAttackDamage() < 2) {
            target.setAttackDamage(0);
        } else {
            target.setAttackDamage(target.getAttackDamage() - 2);
        }
    }

    private void applySkyjack(final Minion target, final Minion special) {
        int myLife = special.getHealth();
        int enemyLife = target.getHealth();
        special.setHealth(enemyLife);
        target.setHealth(myLife);
    }

    private void applyShapeShift(final Minion target, final Minion special) {
        int attackEnemy = target.getAttackDamage();
        int lifeEnemy = target.getHealth();
        target.setHealth(attackEnemy);
        target.setAttackDamage(lifeEnemy);
    }

    private void applyGodsPlan(final Minion target, final Minion special) {
        target.setHealth(target.getHealth() + 2);
    }

    /**
     * Gets the attack damage of the minion.
     *
     * @return the attack damage
     */
    public int getAttackDamage() {
        return attackDamage;
    }

    /**
     * Sets the attack damage of the minion.
     *
     * @param attackDamage the attack damage
     */
    public void setAttackDamage(final int attackDamage) {
        this.attackDamage = attackDamage;
    }

    /**
     * Gets the tank status of the minion.
     *
     * @return the tank status
     */
    public int getTank() {
        return tank;
    }

    /**
     * Sets the tank status of the minion.
     *
     * @param tank the tank status
     */
    public void setTank(final int tank) {
        this.tank = tank;
    }

    /**
     * Gets the ability status of the minion.
     *
     * @return the ability status
     */
    public int getAbility() {
        return ability;
    }

    /**
     * Sets the ability status of the minion.
     *
     * @param ability the ability status
     */
    public void setAbility(final int ability) {
        this.ability = ability;
    }

    /**
     * Gets the used minion status.
     *
     * @return the used minion status
     */
    public int getUsedMinion() {
        return usedMinion;
    }

    /**
     * Sets the used minion status.
     *
     * @param usedMinion the used minion status
     */
    public void setUsedMinion(final int usedMinion) {
        this.usedMinion = usedMinion;
    }

    /**
     * Checks if the minion is frozen.
     *
     * @return true if the minion is frozen, false otherwise
     */
    public boolean isFrozen() {
        return frozen;
    }

    /**
     * Sets the frozen status of the minion.
     *
     * @param frozen the frozen status
     */
    public void setFrozen(final boolean frozen) {
        this.frozen = frozen;
    }
}
