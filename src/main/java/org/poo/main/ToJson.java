package org.poo.main;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.fileio.ActionsInput;

import java.util.ArrayList;

public class ToJson {
    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Converts the deck to JSON format.
     *
     * @param deck the deck of cards
     * @param playerIndex the index of the player
     * @param output the JSON output node
     */
    public void turnDeckToJson(final ArrayList<Card> deck,
                               final int playerIndex, final ArrayNode output) {
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", "getPlayerDeck");
        objectNode.put("playerIdx", playerIndex);

        ArrayNode deckNode = objectMapper.createArrayNode();

        for (Card card : deck) {
            ObjectNode cardNode = objectMapper.createObjectNode();
            cardNode.put("mana", card.getMana());
            cardNode.put("attackDamage", ((Minion) card).getAttackDamage());
            cardNode.put("health", card.getHealth());
            cardNode.put("description", card.getDescription());
            ArrayNode colors = objectMapper.createArrayNode();
            for (String color : card.getColors()) {
                colors.add(color);
            }
            cardNode.put("colors", colors);
            cardNode.put("name", card.getName());
            deckNode.add(cardNode);
        }
        objectNode.put("output", deckNode);
        output.add(objectNode);
    }

    /**
     * Converts the hero to JSON format.
     *
     * @param hero the hero
     * @param playerIndex the index of the player
     * @param output the JSON output node
     */
    public void turnHeroToJson(final Hero hero, final int playerIndex, final ArrayNode output) {
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", "getPlayerHero");
        objectNode.put("playerIdx", playerIndex);

        ObjectNode heroNode = objectMapper.createObjectNode();
        heroNode.put("mana", hero.getMana());
        heroNode.put("description", hero.getDescription());
        ArrayNode colors = objectMapper.createArrayNode();
        for (String color : hero.getColors()) {
            colors.add(color);
        }
        heroNode.put("colors", colors);
        heroNode.put("name", hero.getName());
        heroNode.put("health", hero.getHealth());
        objectNode.put("output", heroNode);
        output.add(objectNode);
    }

    /**
     * Gets the player's turn in JSON format.
     *
     * @param action the action input
     * @param turn the current turn
     * @param output the JSON output node
     */
    public void getPlayerTurn(final ActionsInput action, final int turn, final ArrayNode output) {
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", "getPlayerTurn");
        objectNode.put("output", turn);
        output.add(objectNode);
    }

    /**
     * Treats the errors for the place card action and adds them to the output in JSON format.
     * @param action
     * @param output
     * @param error
     */
    public void treatErrorsPlaceCard(final ActionsInput action, final ArrayNode output,
                                     final String error) {
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", "placeCard");
        objectNode.put("handIdx", action.getHandIdx());
        objectNode.put("error", error);
        output.add(objectNode);
    }
    /**
     * Get the cards in the player's hand into JSON format.
     * @param hand
     * @param output
     * @param playerIndex
     */
    public void getCardsInHand(final ArrayList<Card> hand, final int playerIndex,
                               final ArrayNode output) {
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", "getCardsInHand");
        objectNode.put("playerIdx", playerIndex);

        ArrayNode handNode = objectMapper.createArrayNode();

        for (Card card : hand) {
            ObjectNode cardNode = objectMapper.createObjectNode();
            cardNode.put("mana", card.getMana());
            cardNode.put("attackDamage", ((Minion) card).getAttackDamage());
            cardNode.put("health", card.getHealth());
            cardNode.put("description", card.getDescription());
            ArrayNode colors = objectMapper.createArrayNode();
            for (String color : card.getColors()) {
                colors.add(color);
            }
            cardNode.put("colors", colors);
            cardNode.put("name", card.getName());
            handNode.add(cardNode);
        }
        objectNode.put("output", handNode);
        output.add(objectNode);
    }

    /**
     * Get mana from player into JSON format.
     * @param mana
     * @param playerIndex
     * @param output
     */
    public void getPlayerMana(final int mana, final int playerIndex, final ArrayNode output) {
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", "getPlayerMana");
        objectNode.put("playerIdx", playerIndex);
        objectNode.put("output", mana);
        output.add(objectNode);
    }

    /**
     * Take all cards on the table and put them into JSON format.
     * @param frontRowPlayerOne
     * @param backRowPlayerOne
     * @param frontRowPlayerTwo
     * @param backRowPlayerTwo
     * @param output
     */
    public void getCardsOnTable(final ArrayList<Card> frontRowPlayerOne,
                                final ArrayList<Card> backRowPlayerOne,
                                final ArrayList<Card> frontRowPlayerTwo,
                                final ArrayList<Card> backRowPlayerTwo,
                                final ArrayNode output) {
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", "getCardsOnTable");

        ArrayNode rowsOut = objectMapper.createArrayNode();

        ArrayNode backRowPlayerTwoNode = objectMapper.createArrayNode();

        for (Card card : backRowPlayerTwo) {
            ObjectNode cardNode = objectMapper.createObjectNode();
            cardNode.put("mana", card.getMana());
            cardNode.put("attackDamage", ((Minion) card).getAttackDamage());
            cardNode.put("health", card.getHealth());
            cardNode.put("description", card.getDescription());
            ArrayNode colors = objectMapper.createArrayNode();
            for (String color : card.getColors()) {
                colors.add(color);
            }
            cardNode.put("colors", colors);
            cardNode.put("name", card.getName());
            backRowPlayerTwoNode.add(cardNode);
        }

        rowsOut.add(backRowPlayerTwoNode);

        ArrayNode frontRowPlayerTwoNode = objectMapper.createArrayNode();

        for (Card card : frontRowPlayerTwo) {
            ObjectNode cardNode = objectMapper.createObjectNode();
            cardNode.put("mana", card.getMana());
            cardNode.put("attackDamage", ((Minion) card).getAttackDamage());
            cardNode.put("health", card.getHealth());
            cardNode.put("description", card.getDescription());
            ArrayNode colors = objectMapper.createArrayNode();
            for (String color : card.getColors()) {
                colors.add(color);
            }
            cardNode.put("colors", colors);
            cardNode.put("name", card.getName());
            frontRowPlayerTwoNode.add(cardNode);
        }

        rowsOut.add(frontRowPlayerTwoNode);

        ArrayNode frontRowPlayerOneNode = objectMapper.createArrayNode();

        for (Card card : frontRowPlayerOne) {
            ObjectNode cardNode = objectMapper.createObjectNode();
            cardNode.put("mana", card.getMana());
            cardNode.put("attackDamage", ((Minion) card).getAttackDamage());
            cardNode.put("health", card.getHealth());
            cardNode.put("description", card.getDescription());
            ArrayNode colors = objectMapper.createArrayNode();
            for (String color : card.getColors()) {
                colors.add(color);
            }
            cardNode.put("colors", colors);
            cardNode.put("name", card.getName());
            frontRowPlayerOneNode.add(cardNode);
        }

        rowsOut.add(frontRowPlayerOneNode);

        ArrayNode backRowPlayerOneNode = objectMapper.createArrayNode();

        for (Card card : backRowPlayerOne) {
            ObjectNode cardNode = objectMapper.createObjectNode();
            cardNode.put("mana", card.getMana());
            cardNode.put("attackDamage", ((Minion) card).getAttackDamage());
            cardNode.put("health", card.getHealth());
            cardNode.put("description", card.getDescription());
            ArrayNode colors = objectMapper.createArrayNode();
            for (String color : card.getColors()) {
                colors.add(color);
            }
            cardNode.put("colors", colors);
            cardNode.put("name", card.getName());
            backRowPlayerOneNode.add(cardNode);
        }

        rowsOut.add(backRowPlayerOneNode);
        objectNode.set("output", rowsOut);
        output.add(objectNode);
    }

    /**
     * Treats the errors for the attack action and adds them to the output in JSON format.
     * @param action
     * @param output
     * @param error
     */
    public void treatErrorsAttack(final ActionsInput action, final ArrayNode output,
                                  final String error) {
        ObjectNode attackCardOutput = new ObjectMapper().createObjectNode();
        attackCardOutput.put("command", action.getCommand());

        ObjectNode attacker = new ObjectMapper().createObjectNode();
        attacker.put("x", action.getCardAttacker().getX());
        attacker.put("y", action.getCardAttacker().getY());
        attackCardOutput.set("cardAttacker", attacker);

        ObjectNode attacked = new ObjectMapper().createObjectNode();
        attacked.put("x", action.getCardAttacked().getX());
        attacked.put("y", action.getCardAttacked().getY());
        attackCardOutput.set("cardAttacked", attacked);

        attackCardOutput.put("error", error);
        output.add(attackCardOutput);
    }

    /**
     * Treats the errors for the card position action and adds them to the output in JSON format.
     * @param x
     * @param y
     * @param output
     * @param error
     */
    public void treatCardPositionErrors(final int x, final int y,
                                        final ArrayNode output, final String error) {
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", "getCardAtPosition");
        objectNode.put("x", x);
        objectNode.put("y", y);
        objectNode.put("output", error);
        output.add(objectNode);
    }

    /**
     * Get the card at a certain position into JSON format.
     * @param action
     * @param card
     * @param x
     * @param y
     * @param output
     */
    public void getCardAtPosition(final ActionsInput action, final Card card, final int x,
                                  final int y, final ArrayNode output) {
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", action.getCommand());
        objectNode.put("x", x);
        objectNode.put("y", y);

        ObjectNode cardNode = objectMapper.createObjectNode();
        cardNode.put("mana", card.getMana());
        cardNode.put("attackDamage", ((Minion) card).getAttackDamage());
        cardNode.put("health", card.getHealth());
        cardNode.put("description", card.getDescription());
        ArrayNode colors = objectMapper.createArrayNode();
        for (String color : card.getColors()) {
            colors.add(color);
        }
        cardNode.put("colors", colors);
        cardNode.put("name", card.getName());
        objectNode.set("output", cardNode);
        output.add(objectNode);
    }

    /**
     * Treats the errors for the use hero ability action and adds them to the output in JSON format.
     * @param command
     * @param output
     * @param error
     * @param affectedRow
     */
    public void treatUseHeroAbilityErrors(final String command, final ArrayNode output,
                                          final String error, final int affectedRow) {
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", command);
        objectNode.put("affectedRow", affectedRow);
        objectNode.put("error", error);
        output.add(objectNode);
    }

    /**
     * Get frozen cards on the table into JSON format.
     * @param frontRowPlayerOne
     * @param backRowPlayerOne
     * @param frontRowPlayerTwo
     * @param backRowPlayerTwo
     * @param output
     */
    public void getFrozenCardsOnTable(final ArrayList<Card> frontRowPlayerOne,
                                      final ArrayList<Card> backRowPlayerOne,
                                      final ArrayList<Card> frontRowPlayerTwo,
                                      final ArrayList<Card> backRowPlayerTwo,
                                      final ArrayNode output) {
        ObjectNode frozenCards = objectMapper.createObjectNode();

        ArrayNode frozenCardsOut = objectMapper.createArrayNode();

        for (Card card : backRowPlayerTwo) {
            if (((Minion) card).isFrozen()) {
                ObjectNode cardNode = objectMapper.createObjectNode();
                cardNode.put("mana", card.getMana());
                cardNode.put("attackDamage", ((Minion) card).getAttackDamage());
                cardNode.put("health", card.getHealth());
                cardNode.put("description", card.getDescription());
                ArrayNode colors = objectMapper.createArrayNode();
                for (String color : card.getColors()) {
                    colors.add(color);
                }
                cardNode.put("colors", colors);
                cardNode.put("name", card.getName());
                frozenCardsOut.add(cardNode);
            }
        }

        for (Card card : frontRowPlayerTwo) {
            if (((Minion) card).isFrozen()) {
                ObjectNode cardNode = objectMapper.createObjectNode();
                cardNode.put("mana", card.getMana());
                cardNode.put("attackDamage", ((Minion) card).getAttackDamage());
                cardNode.put("health", card.getHealth());
                cardNode.put("description", card.getDescription());
                ArrayNode colors = objectMapper.createArrayNode();
                for (String color : card.getColors()) {
                    colors.add(color);
                }
                cardNode.put("colors", colors);
                cardNode.put("name", card.getName());
                frozenCardsOut.add(cardNode);
            }
        }

        for (Card card : frontRowPlayerOne) {
            if (((Minion) card).isFrozen()) {
                ObjectNode cardNode = objectMapper.createObjectNode();
                cardNode.put("mana", card.getMana());
                cardNode.put("attackDamage", ((Minion) card).getAttackDamage());
                cardNode.put("health", card.getHealth());
                cardNode.put("description", card.getDescription());
                ArrayNode colors = objectMapper.createArrayNode();
                for (String color : card.getColors()) {
                    colors.add(color);
                }
                cardNode.put("colors", colors);
                cardNode.put("name", card.getName());
                frozenCardsOut.add(cardNode);
            }
        }

        for (Card card : backRowPlayerOne) {
            if (((Minion) card).isFrozen()) {
                ObjectNode cardNode = objectMapper.createObjectNode();
                cardNode.put("mana", card.getMana());
                cardNode.put("attackDamage", ((Minion) card).getAttackDamage());
                cardNode.put("health", card.getHealth());
                cardNode.put("description", card.getDescription());
                ArrayNode colors = objectMapper.createArrayNode();
                for (String color : card.getColors()) {
                    colors.add(color);
                }
                cardNode.put("colors", colors);
                cardNode.put("name", card.getName());
                frozenCardsOut.add(cardNode);
            }
        }

        frozenCards.put("command", "getFrozenCardsOnTable");
        frozenCards.set("output", frozenCardsOut);
        output.add(frozenCards);
    }

    /**
     * Get the number of wins for player one into JSON format.
     * @param wins
     * @param output
     */
    public void getPlayerOneWins(final int wins, final ArrayNode output) {
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", "getPlayerOneWins");
        objectNode.put("output", wins);
        output.add(objectNode);
    }

    /**
     * Get the number of wins for player two into JSON format.
     * @param wins
     * @param output
     */
    public void getPlayerTwoWins(final int wins, final ArrayNode output) {
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", "getPlayerTwoWins");
        objectNode.put("output", wins);
        output.add(objectNode);
    }

    /**
     * Get the total number of games played into JSON format.
     * @param games
     * @param output
     */
    public void getTotalGamesPlayed(final int games, final ArrayNode output) {
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", "getTotalGamesPlayed");
        objectNode.put("output", games);
        output.add(objectNode);
    }

    /**
     * Treats the errors for the attacking hero action and adds them to the output in JSON format.
     * @param actions
     * @param output
     * @param error
     */
    public void attackingHeroErrors(final ActionsInput actions, final ArrayNode output,
                                    final String error) {
        ObjectNode attackHeroOutput = new ObjectMapper().createObjectNode();
        attackHeroOutput.put("command", actions.getCommand());

        ObjectNode attacker = new ObjectMapper().createObjectNode();
        attacker.put("x", actions.getCardAttacker().getX());
        attacker.put("y", actions.getCardAttacker().getY());
        attackHeroOutput.set("cardAttacker", attacker);

        attackHeroOutput.put("error", error);
        output.add(attackHeroOutput);
    }
}
