package org.poo.main.game;

/**
 * Enum representing game commands.
 */
public enum Command {
    GET_PLAYER_DECK,
    GET_PLAYER_HERO,
    GET_PLAYER_TURN,
    END_PLAYER_TURN,
    PLACE_CARD,
    GET_CARDS_IN_HAND,
    GET_PLAYER_MANA,
    GET_CARDS_ON_TABLE,
    CARD_USES_ATTACK,
    GET_CARD_AT_POSITION,
    CARD_USES_ABILITY,
    USE_ATTACK_HERO,
    USE_HERO_ABILITY,
    GET_FROZEN_CARDS_ON_TABLE,
    GET_PLAYER_ONE_WINS,
    GET_PLAYER_TWO_WINS,
    GET_TOTAL_GAMES_PLAYED;

    /**
     * Converts a string to a Command enum.
     *
     * @param command the command string
     * @return the corresponding Command enum
     */
    public static Command fromString(final String command) {
        switch (command) {
            case "getPlayerDeck":
                return GET_PLAYER_DECK;
            case "getPlayerHero":
                return GET_PLAYER_HERO;
            case "getPlayerTurn":
                return GET_PLAYER_TURN;
            case "endPlayerTurn":
                return END_PLAYER_TURN;
            case "placeCard":
                return PLACE_CARD;
            case "getCardsInHand":
                return GET_CARDS_IN_HAND;
            case "getPlayerMana":
                return GET_PLAYER_MANA;
            case "getCardsOnTable":
                return GET_CARDS_ON_TABLE;
            case "cardUsesAttack":
                return CARD_USES_ATTACK;
            case "getCardAtPosition":
                return GET_CARD_AT_POSITION;
            case "cardUsesAbility":
                return CARD_USES_ABILITY;
            case "useAttackHero":
                return USE_ATTACK_HERO;
            case "useHeroAbility":
                return USE_HERO_ABILITY;
            case "getFrozenCardsOnTable":
                return GET_FROZEN_CARDS_ON_TABLE;
            case "getPlayerOneWins":
                return GET_PLAYER_ONE_WINS;
            case "getPlayerTwoWins":
                return GET_PLAYER_TWO_WINS;
            case "getTotalGamesPlayed":
                return GET_TOTAL_GAMES_PLAYED;
            default:
                throw new IllegalArgumentException("Unknown command: " + command);
        }
    }
}
