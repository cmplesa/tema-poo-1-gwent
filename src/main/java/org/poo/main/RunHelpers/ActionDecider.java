package org.poo.main.RunHelpers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.fileio.ActionsInput;
import org.poo.main.Player;


import org.poo.main.cards.Card;
import org.poo.main.cards.Hero;
import org.poo.main.cards.Minion;
import org.poo.main.game.Game;

import java.util.ArrayList;

public class ActionDecider {

    /**
     * This method retrieves the deck based on the index.
     *
     * @param index the index to determine which deck to retrieve
     * @param output the ArrayNode to store the output
     * @param json the toJson instance to handle JSON operations
     * @param deck1 the first deck
     * @param deck2 the second deck
     */
    public void getDeckIf(final int index, final ArrayNode output, final ToJson json,
                          final ArrayList<Card> deck1, final ArrayList<Card> deck2) {
        if (index == 1) {
            json.turnDeckToJson(deck1, index, output);
        } else {
            json.turnDeckToJson(deck2, index, output);
        }
    }

    /**
     * This method retrieves the player's hero based on the index.
     *
     * @param index the index to determine which player's hero to retrieve
     * @param output the ArrayNode to store the output
     * @param json the toJson instance to handle JSON operations
     * @param hero1 the first player's hero
     * @param hero2 the second player's hero
     */
    public void getPlayerHeroIf(final int index, final ArrayNode output, final ToJson json,
                                final Hero hero1, final Hero hero2) {
        if (index == 1) {
            json.turnHeroToJson(hero1, index, output);
        } else {
            json.turnHeroToJson(hero2, index, output);
        }
    }

    /**
     * This method places a card on the table based on the game state and action.
     *
     * @param action the action input containing the hand index
     * @param game the current game state
     * @param playerOne the first player
     * @param playerTwo the second player
     * @param index the index to determine which player's turn it is
     * @param output the ArrayNode to store the output
     * @param toJson the toJson instance to handle JSON operations
     */
    public void placeCardIf(final ActionsInput action, final Game game, final Player playerOne,
                            final Player playerTwo, final int index, final ArrayNode output,
                            final ToJson toJson) {
        if (game.getTurn() == MagicNumbers.PLAYER_ONE_TURN.getValue() && action.getHandIdx()
                < playerOne.getHand().size()
                && playerOne.getHand().size() > 0) {
            Card card = playerOne.getHand().get(action.getHandIdx());
            if (playerOne.getMana() < card.getMana()) {
                toJson.treatErrorsPlaceCard(action, output,
                        "Not enough mana to place card on table.");
            } else if ((card.getName().equals("Sentinel") || card.getName().equals("Berserker")
                    || card.getName().equals("The Cursed One")
                    || card.getName().equals("Disciple")) && playerOne.getBackRow().size()
                    > MagicNumbers.MAX_BACK_ROW_SIZE.getValue()) {
                toJson.treatErrorsPlaceCard(action, output,
                        "Cannot place card on table since row is full.");
            } else if ((card.getName().equals("The Ripper") || card.getName().equals("Miraj")
                    || card.getName().equals("Goliath") || card.getName().equals("The Cursed One"))
                    && playerOne.getFrontRow().size()
                    > MagicNumbers.MAX_FRONT_ROW_SIZE.getValue()) {
                toJson.treatErrorsPlaceCard(action, output,
                        "Cannot place card on table since row is full.");
            } else {
                if (card.getName().equals("Sentinel") || card.getName().equals("Berserker")
                        || card.getName().equals("The Cursed One")
                        || card.getName().equals("Disciple")) {
                    playerOne.setMana(playerOne.getMana() - card.getMana());
                    playerOne.getBackRow().add(card);
                    playerOne.getHand().remove(card);
                } else {
                    playerOne.setMana(playerOne.getMana() - card.getMana());
                    playerOne.getFrontRow().add(card);
                    playerOne.getHand().remove(card);
                }
            }
        } else if (game.getTurn() == MagicNumbers.PLAYER_TWO_TURN.getValue() && action.getHandIdx()
                < playerTwo.getHand().size()
                && playerTwo.getHand().size() > 0) {
            Card card = playerTwo.getHand().get(action.getHandIdx());
            if (playerTwo.getMana() < card.getMana()) {
                toJson.treatErrorsPlaceCard(action, output,
                        "Not enough mana to place card on table.");
            } else if ((card.getName().equals("Sentinel")
                    || card.getName().equals("Berserker")
                    || card.getName().equals("The Cursed One")
                    || card.getName().equals("Disciple"))
                    && playerTwo.getBackRow().size()
                    >= MagicNumbers.MAX_BACK_ROW_SIZE.getValue()) {
                toJson.treatErrorsPlaceCard(action, output,
                        "Cannot place card on table since row is full.");
            } else if ((card.getName().equals("The Ripper") || card.getName().equals("Miraj")
                    || card.getName().equals("Goliath") || card.getName().equals("The Cursed One"))
                    && playerTwo.getFrontRow().size()
                    >= MagicNumbers.MAX_FRONT_ROW_SIZE.getValue()) {
                toJson.treatErrorsPlaceCard(action, output,
                        "Cannot place card on table since row is full.");
            } else {
                if (card.getName().equals("Sentinel") || card.getName().equals("Berserker")
                        || card.getName().equals("The Cursed One")
                        || card.getName().equals("Disciple")) {
                    playerTwo.setMana(playerTwo.getMana() - card.getMana());
                    playerTwo.getBackRow().add(card);
                    playerTwo.getHand().remove(card);
                } else {
                    playerTwo.getFrontRow().add(card);
                    playerTwo.setMana(playerTwo.getMana() - card.getMana());
                    playerTwo.getHand().remove(card);
                }
            }
        }
    }

    /**
     * This method retrieves the cards in hand based on the index.
     *
     * @param index the index to determine which player's hand to retrieve
     * @param output the ArrayNode to store the output
     * @param json the toJson instance to handle JSON operations
     * @param playerOne the first player
     * @param playerTwo the second player
     */
    public void getCardsInHandIf(final int index, final ArrayNode output, final ToJson json,
                                 final Player playerOne, final Player playerTwo) {
        if (index == 1) {
            json.getCardsInHand(playerOne.getHand(), index, output);
        } else {
            json.getCardsInHand(playerTwo.getHand(), index, output);
        }
    }

    /**
     *
     * This method retrieves the player's mana based on the index.
     * @param index
     * @param playerOneMana
     * @param playerTwoMana
     * @param output
     * @param json
     */
    public void getPlayerManaIf(final int index, final int playerOneMana, final int playerTwoMana,
                                final ArrayNode output, final ToJson json) {
        if (index == 1) {
            json.getPlayerMana(playerOneMana, index, output);
        } else {
            json.getPlayerMana(playerTwoMana, index, output);
        }
    }

    /**
     * This method decides if a card can attack another card.
     * @param action
     * @param game
     * @param playerOne
     * @param playerTwo
     * @param output
     * @param toJson
     */

    public void cardUsesAttackIf(final ActionsInput action, final Game game,
                                 final Player playerOne, final Player playerTwo,
                                 final ArrayNode output, final ToJson toJson) {
        Player player = new Player();
        Card cardAttacker = player.getPlayerCard(playerOne, playerTwo,
                action.getCardAttacker().getX(), action.getCardAttacker().getY());
        Card cardAttacked = player.getPlayerCard(playerOne, playerTwo,
                action.getCardAttacked().getX(), action.getCardAttacked().getY());
        if (cardAttacked == null || cardAttacker == null) {
            return;
        }
        boolean isTank = false;
        boolean attackedCardIsTank = false;
        ArrayList<Card> row = new ArrayList<>();
        if (game.getTurn() == 1) {
            row = playerTwo.getFrontRow();
        } else {
            row = playerOne.getFrontRow();
        }

        for (Card card : row) {
            if (card.getName().equals("Warden") || card.getName().equals("Goliath")) {
                isTank = true;
            }
            if (card.getName().equals(cardAttacked.getName()) && isTank) {
                attackedCardIsTank = true;
            }
        }

        Minion minionAttacker = (Minion) cardAttacker;
        if ((action.getCardAttacked().getX() == 0 || action.getCardAttacked().getX() == 1)
                && game.getTurn() != 1) {
            toJson.treatErrorsAttack(action, output,
                    "Attacked card does not belong to the enemy.");
        } else if ((action.getCardAttacked().getX() == 2 || action.getCardAttacked().getX()
                == MagicNumbers.THREE.getValue())
                && game.getTurn() != 2) {
            toJson.treatErrorsAttack(action, output,
                    "Attacked card does not belong to the enemy.");
        } else if (cardAttacker.getHasAttacked() == 1 || minionAttacker.getUsedMinion() == 1) {
            toJson.treatErrorsAttack(action, output,
                    "Attacker card has already attacked this turn.");
        } else if (minionAttacker.isFrozen()) {
            toJson.treatErrorsAttack(action, output, "Attacker card is frozen.");
        } else if (isTank && !attackedCardIsTank) {
            toJson.treatErrorsAttack(action, output, "Attacked card is not of type 'Tank'.");
        } else if (cardAttacked.getHealth() <= 0) {
            toJson.treatErrorsAttack(action, output, "Attacked card is already dead.");
        } else {
            cardAttacked.setHealth(cardAttacked.getHealth() - minionAttacker.getAttackDamage());
            cardAttacked.cardDies(action, playerOne, playerTwo, cardAttacked);
            cardAttacker.setHasAttacked(1);
        }
    }

    /**
     * This method retrieves the card at a certain position.
     * @param action
     * @param game
     * @param playerOne
     * @param playerTwo
     * @param output
     * @param toJson
     */
    public void getCardAtPositionIf(final ActionsInput action, final Game game,
                                    final Player playerOne, final Player playerTwo,
                                    final ArrayNode output, final ToJson toJson) {
        int x = action.getX();
        int y = action.getY();
        if (x == 0) {
            if (playerTwo.getBackRow().size() < y) {
                toJson.treatCardPositionErrors(x, y, output,
                        "No card available at that position.");
            } else {
                Player player = new Player();
                toJson.getCardAtPosition(action,
                        player.getPlayerCard(playerOne, playerTwo, x, y), x, y, output);
            }
        } else if (x == 1) {
            if (playerTwo.getFrontRow().size() < y) {
                toJson.treatCardPositionErrors(x, y, output,
                        "No card available at that position.");
            } else {
                Player player = new Player();
                toJson.getCardAtPosition(action,
                        player.getPlayerCard(playerOne, playerTwo, x, y), x, y, output);
            }
        } else if (x == 2) {
            if (playerOne.getFrontRow().size() < y) {
                toJson.treatCardPositionErrors(x, y, output,
                        "No card available at that position.");
            } else {
                Player player = new Player();
                toJson.getCardAtPosition(action,
                        player.getPlayerCard(playerOne, playerTwo, x, y), x, y, output);
            }
        } else if (x == MagicNumbers.THREE.getValue()) {
            if (playerOne.getBackRow().size() < y) {
                toJson.treatCardPositionErrors(x, y, output,
                        "No card available at that position.");
            } else {
                Player player = new Player();
                toJson.getCardAtPosition(action,
                        player.getPlayerCard(playerOne, playerTwo, x, y), x, y, output);
            }
        }
    }

    /**
     * This method decides if a card can use its ability.
     * @param output
     * @param toJson
     * @param playerOne
     * @param playerTwo
     * @param action
     * @param game
     */
    public void cardUsesAbility(final ArrayNode output, final ToJson toJson,
                                final Player playerOne, final Player playerTwo,
                                final ActionsInput action, final Game game) {
        Player player = new Player();
        Card cardAttacker = player.getPlayerCard(playerOne, playerTwo,
                action.getCardAttacker().getX(), action.getCardAttacker().getY());
        Card cardAttacked = player.getPlayerCard(playerOne, playerTwo,
                action.getCardAttacked().getX(), action.getCardAttacked().getY());
        if (cardAttacked == null || cardAttacker == null) {
            return;
        }
        Minion minionAttacker = (Minion) cardAttacker;
        if (minionAttacker.isFrozen()) {
            toJson.treatErrorsAttack(action, output, "Attacker card is frozen.");
        } else if (minionAttacker.getUsedMinion() == 1 || minionAttacker.getHasAttacked() == 1) {
            toJson.treatErrorsAttack(action, output,
                    "Attacker card has already attacked this turn.");
        } else if (minionAttacker.getName().equals("Disciple")) {
            if ((action.getCardAttacked().getX() == 0 || action.getCardAttacked().getX() == 1)
                    && (action.getCardAttacker().getX() == 2 || action.getCardAttacker().getX()
                    == MagicNumbers.THREE.getValue())) {
                toJson.treatErrorsAttack(action, output,
                        "Attacked card does not belong to the current player.");
            } else if ((action.getCardAttacked().getX() == 2 || action.getCardAttacked().getX()
                    == MagicNumbers.THREE.getValue()) && (action.getCardAttacker().getX() == 0
                    || action.getCardAttacker().getX() == 1)) {
                toJson.treatErrorsAttack(action, output,
                        "Attacked card does not belong to the current player.");
            } else {
                Minion minionAttacked = (Minion) cardAttacked;
                minionAttacked.setHealth(minionAttacked.getHealth() + 2);
                minionAttacker.setUsedMinion(1);
            }
        } else if (minionAttacker.getName().equals("The Ripper")
                || minionAttacker.getName().equals("Miraj")
                || minionAttacker.getName().equals("The Cursed One")) {
            boolean isTank = false;
            boolean attackedCardIsTank = false;
            ArrayList<Card> row = new ArrayList<>();
            if (game.getTurn() == 1) {
                row = playerTwo.getFrontRow();
            } else {
                row = playerOne.getFrontRow();
            }

            for (Card card : row) {
                if (card.getName().equals("Warden") || card.getName().equals("Goliath")) {
                    isTank = true;
                }
            }
            if (isTank && (cardAttacked.getName().equals("Warden")
                    || cardAttacked.getName().equals("Goliath"))) {
                attackedCardIsTank = true;
            }

            if ((action.getCardAttacked().getX() == 0 || action.getCardAttacked().getX() == 1)
                    && (action.getCardAttacker().getX() == 1
                    || action.getCardAttacker().getX() == 0)) {
                toJson.treatErrorsAttack(action, output,
                        "Attacked card does not belong to the enemy.");
            } else if ((action.getCardAttacked().getX() == 2
                    || action.getCardAttacked().getX() == MagicNumbers.THREE.getValue())
                    && (action.getCardAttacker().getX() == 2
                    || action.getCardAttacker().getX() == MagicNumbers.THREE.getValue())) {
                toJson.treatErrorsAttack(action, output,
                        "Attacked card does not belong to the enemy.");
            } else if (isTank && !attackedCardIsTank) {
                toJson.treatErrorsAttack(action, output,
                        "Attacked card is not of type 'Tank'.");
            } else {
                Minion minionAttacked = (Minion) cardAttacked;
                if (minionAttacker.getName().equals("The Cursed One")) {
                    int attack = minionAttacked.getAttackDamage();
                    int life = minionAttacked.getHealth();
                    minionAttacked.setAttackDamage(life);
                    minionAttacked.setHealth(attack);
                    minionAttacked.cardDies(action, playerOne, playerTwo, cardAttacked);
                    minionAttacker.setUsedMinion(1);
                } else if (minionAttacker.getName().equals("The Ripper")) {
                    int attack = minionAttacked.getAttackDamage();
                    if (attack < 2) {
                        attack = 0;
                    } else {
                        attack -= 2;
                    }
                    minionAttacked.setAttackDamage(attack);
                    minionAttacker.setUsedMinion(1);
                } else if (minionAttacker.getName().equals("Miraj")) {
                    int lifeAttacker = minionAttacker.getHealth();
                    int lifeAttacked = minionAttacked.getHealth();
                    minionAttacker.setHealth(lifeAttacked);
                    minionAttacked.setHealth(lifeAttacker);
                    minionAttacker.setUsedMinion(1);
                }
            }
        }
    }

    /**
     * This method decides if a card can attack the enemy hero.
     * @param playerOne
     * @param playerTwo
     * @param game
     * @param output
     * @param toJson
     * @param wins
     * @param action
     */
    public void useAttackHero(final Player playerOne, final Player playerTwo, final Game game,
                              final ArrayNode output, final ToJson toJson,
                              final ArrayList<Integer> wins, final ActionsInput action) {
        Player player = new Player();
        Card attackerCard = player.getPlayerCard(playerOne, playerTwo,
                action.getCardAttacker().getX(), action.getCardAttacker().getY());
        if (attackerCard == null) {
            return;
        }
        Minion minionAttacker = (Minion) attackerCard;
        boolean isTank = false;
        boolean attackedCardIsTank = false;
        ArrayList<Card> row = new ArrayList<>();
        if (game.getTurn() == 1) {
            row = playerTwo.getFrontRow();
        } else {
            row = playerOne.getFrontRow();
        }

        for (Card card : row) {
            if (card.getName().equals("Warden") || card.getName().equals("Goliath")) {
                isTank = true;
            }
        }
        if (minionAttacker.isFrozen()) {
            toJson.attackingHeroErrors(action, output, "Attacker card is frozen.");
        } else if (minionAttacker.getHasAttacked() == 1
                || minionAttacker.getUsedMinion() == 1) {
            toJson.attackingHeroErrors(action, output,
                    "Attacker card has already attacked this turn.");
        } else if (isTank) {
            toJson.attackingHeroErrors(action, output,
                    "Attacked card is not of type 'Tank'.");
        } else {
            boolean isDead = false;
            Hero playerHero;
            if (action.getCardAttacker().getX() == 0 || action.getCardAttacker().getX() == 1) {
                playerHero = playerOne.getPlayerHero();
            } else {
                playerHero = playerTwo.getPlayerHero();
            }
            playerHero.setHealth(playerHero.getHealth() - minionAttacker.getAttackDamage());
            if (playerHero.getHealth() <= 0) {
                isDead = true;
            }
            minionAttacker.setHasAttacked(1);
            if (isDead) {
                if (action.getCardAttacker().getX() == 0 || action.getCardAttacker().getX() == 1) {
                    ObjectNode objectNode = new ObjectMapper().createObjectNode();
                    objectNode.put("gameEnded", "Player two killed the enemy hero.");
                    output.add(objectNode);
                    wins.set(1, wins.get(1) + 1);
                } else {
                    ObjectNode objectNode = new ObjectMapper().createObjectNode();
                    objectNode.put("gameEnded", "Player one killed the enemy hero.");
                    output.add(objectNode);
                    wins.set(0, wins.get(0) + 1);
                }
            }
        }
    }

    /**
     * This method decides if a card can use its ability on the enemy hero.
     * @param playerOne
     * @param playerTwo
     * @param game
     * @param output
     * @param toJson
     * @param action
     */
    public void useHeroAbility(final Player playerOne, final Player playerTwo, final Game game,
                               final ArrayNode output, final ToJson toJson,
                               final ActionsInput action) {
        int row = action.getAffectedRow();
        Player player = new Player();
        if (game.getTurn() == 1) {
            player = playerOne;
        } else {
            player = playerTwo;
        }
        Hero playerHero = player.getPlayerHero();
        if (player.getMana() < playerHero.getMana()) {
            toJson.treatUseHeroAbilityErrors("useHeroAbility",
                    output, "Not enough mana to use hero's ability.", row);
        } else if (playerHero.getHasAtacked() == 1) {
            toJson.treatUseHeroAbilityErrors("useHeroAbility",
                    output, "Hero has already attacked this turn.", row);
        } else if (playerHero.getName().equals("Lord Royce")
                || playerHero.getName().equals("Empress Thorina")) {
            if ((game.getTurn() == 1 && (action.getAffectedRow() == 2
                    || action.getAffectedRow() == MagicNumbers.THREE.getValue()))
                    || (game.getTurn() == 2
                    && (action.getAffectedRow() == 0 || action.getAffectedRow() == 1))) {
                toJson.treatUseHeroAbilityErrors("useHeroAbility",
                        output, "Selected row does not belong to the enemy.", row);
            } else {
                if (playerHero.getName().equals("Lord Royce")) {
                    int rowTemp = action.getAffectedRow();
                    ArrayList<Card> rowToAttack = new ArrayList<>();
                    if (rowTemp == 0) {
                        rowToAttack = playerTwo.getBackRow();
                    } else if (rowTemp == 1) {
                        rowToAttack = playerTwo.getFrontRow();
                    } else if (rowTemp == 2) {
                        rowToAttack = playerOne.getFrontRow();
                    } else {
                        rowToAttack = playerOne.getBackRow();
                    }
                    for (Card card : rowToAttack) {
                        Minion minion = (Minion) card;
                        minion.setFrozen(true);
                    }
                    playerHero.setHasAtacked(1);
                    player.setMana(player.getMana() - playerHero.getMana());
                } else {
                    int rowTemp = action.getAffectedRow();
                    ArrayList<Card> rowToAttack = new ArrayList<>();
                    if (rowTemp == 0) {
                        rowToAttack = playerTwo.getBackRow();
                    } else if (rowTemp == 1) {
                        rowToAttack = playerTwo.getFrontRow();
                    } else if (rowTemp == 2) {
                        rowToAttack = playerOne.getFrontRow();
                    } else {
                        rowToAttack = playerOne.getBackRow();
                    }
                    int max = rowToAttack.get(0).getHealth();
                    Card maxCard = rowToAttack.get(0);
                    for (Card card : rowToAttack) {
                        Minion minion = (Minion) card;
                        if (minion.getHealth() > max) {
                            max = minion.getHealth();
                            maxCard = card;
                        }
                    }
                    rowToAttack.remove(maxCard);
                    playerHero.setHasAtacked(1);
                    player.setMana(player.getMana() - playerHero.getMana());
                }
            }
        } else if (playerHero.getName().equals("General Kocioraw")
                || playerHero.getName().equals("King Mudface")) {
            if ((game.getTurn() == 1 && (action.getAffectedRow() == 0
                    || action.getAffectedRow() == 1)) || (game.getTurn() == 2
                    && (action.getAffectedRow() == 2 || action.getAffectedRow()
                    == MagicNumbers.THREE.getValue()))) {
                toJson.treatUseHeroAbilityErrors("useHeroAbility",
                        output, "Selected row does not belong to the current player.", row);
            } else {
                if (playerHero.getName().equals("General Kocioraw")) {
                    int rowTemp = action.getAffectedRow();
                    ArrayList<Card> rowToAttack = new ArrayList<>();
                    if (rowTemp == 0) {
                        rowToAttack = playerTwo.getBackRow();
                    } else if (rowTemp == 1) {
                        rowToAttack = playerTwo.getFrontRow();
                    } else if (rowTemp == 2) {
                        rowToAttack = playerOne.getFrontRow();
                    } else {
                        rowToAttack = playerOne.getBackRow();
                    }
                    for (Card card : rowToAttack) {
                        Minion minion = (Minion) card;
                        minion.setAttackDamage(minion.getAttackDamage() + 1);
                    }
                    playerHero.setHasAtacked(1);
                    player.setMana(player.getMana() - playerHero.getMana());
                } else {
                    int rowTemp = action.getAffectedRow();
                    ArrayList<Card> rowToAttack = new ArrayList<>();
                    if (rowTemp == 0) {
                        rowToAttack = playerTwo.getBackRow();
                    } else if (rowTemp == 1) {
                        rowToAttack = playerTwo.getFrontRow();
                    } else if (rowTemp == 2) {
                        rowToAttack = playerOne.getFrontRow();
                    } else {
                        rowToAttack = playerOne.getBackRow();
                    }
                    for (Card card : rowToAttack) {
                        Minion minion = (Minion) card;
                        minion.setHealth(minion.getHealth() + 1);
                    }
                    playerHero.setHasAtacked(1);
                    player.setMana(player.getMana() - playerHero.getMana());
                }
            }
        }
    }

}
