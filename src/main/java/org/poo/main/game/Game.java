package org.poo.main.game;

import com.fasterxml.jackson.databind.node.ArrayNode;
import org.poo.fileio.ActionsInput;
import org.poo.fileio.CardInput;
import org.poo.fileio.DecksInput;
import org.poo.main.RunHelpers.MagicNumbers;
import org.poo.main.Player;
import org.poo.main.RunHelpers.ActionDecider;
import org.poo.main.RunHelpers.ToJson;
import org.poo.main.cards.Card;
import org.poo.main.cards.Minion;
import org.poo.main.decks.Decks;

import java.util.ArrayList;

/**
 * Represents a game.
 */
public final class Game {

    private int shuffleSeed;
    private int startingIdx;
    private int turn;
    private int round;

    public Game(final int shuffleSeed, final int startingIdx) {
        this.shuffleSeed = shuffleSeed;
        this.startingIdx = startingIdx;
        this.turn = 0;
        this.round = 1;
    }

    /**
     * Draws a card for the player.
     *
     * @param player the player
     * @param deck the deck
     * @return the updated hand of the player
     */
    public ArrayList<Card> drawCard(final Player player, final ArrayList<Card> deck) {
        if (!deck.isEmpty()) {
            player.getHand().add(deck.getFirst());
            deck.removeFirst();
        }
        return player.getHand();
    }

    /**
     * Creates decks for a player.
     *
     * @param decksInput the decks input
     * @return the created decks
     */
    public Decks createDecksForPlayer(final DecksInput decksInput) {
        ArrayList<ArrayList<CardInput>> decks = decksInput.getDecks();
        ArrayList<ArrayList<Card>> playerDecks = new ArrayList<>();
        Decks deckList = null;
        ArrayList<CardInput> deckInput;

        for (int i = 0; i < decksInput.getNrDecks(); i++) {
            deckInput = decks.get(i);
            ArrayList<Card> deck = new ArrayList<>();
            for (int j = 0; j < decksInput.getNrCardsInDeck(); j++) {
                CardInput cardInput = deckInput.get(j);
                Minion mimu = new Minion(cardInput);
                deck.add(mimu);
            }
            playerDecks.add(deck);
            deckList = new Decks(decksInput.getNrCardsInDeck(),
                    decksInput.getNrDecks(), playerDecks);
        }
        return deckList;
    }

    /**
     * Ends the turn for the current player.
     *
     * @param playerOneDeck the deck of player one
     * @param playerTwoDeck the deck of player two
     * @param playerOne the first player
     * @param playerTwo the second player
     * @param game the game instance
     */
    public void endTurn(final ArrayList<Card> playerOneDeck, final ArrayList<Card> playerTwoDeck,
                        final Player playerOne, final Player playerTwo, final Game game) {
        if (game.getTurn() == 1) {
            game.setTurn(2);
            playerOne.setEndTurn(true);
            unblockCards(playerOne, playerOne.getBackRow());
            unblockCards(playerOne, playerOne.getFrontRow());
        } else {
            game.setTurn(1);
            playerTwo.setEndTurn(true);
            unblockCards(playerTwo, playerTwo.getBackRow());
            unblockCards(playerTwo, playerTwo.getFrontRow());
        }

        if (playerOne.isEndTurn() && playerTwo.isEndTurn()) {
            game.drawCard(playerOne, playerOneDeck);
            game.drawCard(playerTwo, playerTwoDeck);
            if (game.getRound() >= MagicNumbers.TEN.getValue()) {
                playerOne.setMana(playerOne.getMana() + MagicNumbers.TEN.getValue());
                playerTwo.setMana(playerTwo.getMana() + MagicNumbers.TEN.getValue());
            } else {
                playerOne.setMana(playerOne.getMana() + game.getRound() + 1);
                playerTwo.setMana(playerTwo.getMana() + game.getRound() + 1);
            }
            game.setRound(game.getRound() + 1);

            playerOne.setEndTurn(false);
            playerTwo.setEndTurn(false);
        }
    }

    /**
     * Unblocks the cards for the player.
     *
     * @param player the player
     * @param deck the deck
     */
    public void unblockCards(final Player player, final ArrayList<Card> deck) {
        for (Card card : deck) {
            Minion minion = (Minion) card;
            if (minion.isFrozen()) {
                minion.setFrozen(false);
            }
            if (minion.getHasAttacked() == 1) {
                minion.setHasAttacked(0);
            }
            if (minion.getUsedMinion() == 1) {
                minion.setUsedMinion(0);
            }
        }
        player.getPlayerHero().setHasAtacked(0);
    }

    /**
     * Sets up the game.
     *
     * @param playerOne the first player
     * @param playerTwo the second player
     * @param game the game instance
     * @param playerOneDeck the deck of player one
     * @param playerTwoDeck the deck of player two
     */
    public void setTheGame(final Player playerOne, final Player playerTwo, final Game game,
                           final ArrayList<Card> playerOneDeck,
                           final ArrayList<Card> playerTwoDeck) {
        playerOne.setHand(game.drawCard(playerOne, playerOneDeck));
        playerTwo.setHand(game.drawCard(playerTwo, playerTwoDeck));
        game.setTurn(game.getStartingIdx());
        if (game.getRound() >= MagicNumbers.TEN.getValue()) {
            playerOne.setMana(playerOne.getMana() + MagicNumbers.TEN.getValue());
            playerTwo.setMana(playerTwo.getMana() + MagicNumbers.TEN.getValue());
        } else {
            playerOne.setMana(playerOne.getMana() + game.getRound());
            playerTwo.setMana(playerTwo.getMana() + game.getRound());
        }
    }

    /**
     * Runs the game.
     *
     * @param playerOne the first player
     * @param playerTwo the second player
     * @param actionsInput the actions input
     * @param game the game instance
     * @param wins the wins
     * @param output the output
     */
    public void run(final Player playerOne, final Player playerTwo,
                    final ArrayList<ActionsInput> actionsInput, final Game game,
                    final WinsManager winsManager, final ArrayNode output) {
        ArrayList<Card> playerOneDeck = playerOne.getPlayerDeck(playerOne.getPlayerDeckIdx(),
                playerOne, game);
        ArrayList<Card> playerTwoDeck = playerTwo.getPlayerDeck(playerTwo.getPlayerDeckIdx(),
                playerTwo, game);
        setTheGame(playerOne, playerTwo, game, playerOneDeck, playerTwoDeck);
        ToJson toJson = new ToJson();
        ActionDecider actionDecider = new ActionDecider();

        for (ActionsInput action : actionsInput) {
            Command command = Command.fromString(action.getCommand());

            switch (command) {
                case GET_PLAYER_DECK:
                    actionDecider.getDeckIf(action.getPlayerIdx(), output, toJson,
                            playerOneDeck, playerTwoDeck);
                    break;
                case GET_PLAYER_HERO:
                    actionDecider.getPlayerHeroIf(action.getPlayerIdx(), output,
                            toJson, playerOne.getPlayerHero(), playerTwo.getPlayerHero());
                    break;
                case GET_PLAYER_TURN:
                    toJson.getPlayerTurn(action, game.getTurn(), output);
                    break;
                case END_PLAYER_TURN:
                    endTurn(playerOneDeck, playerTwoDeck, playerOne, playerTwo, game);
                    break;
                case PLACE_CARD:
                    actionDecider.placeCardIf(action, game, playerOne, playerTwo,
                            action.getPlayerIdx(), output, toJson);
                    break;
                case GET_CARDS_IN_HAND:
                    actionDecider.getCardsInHandIf(action.getPlayerIdx(), output,
                            toJson, playerOne, playerTwo);
                    break;
                case GET_PLAYER_MANA:
                    actionDecider.getPlayerManaIf(action.getPlayerIdx(), playerOne.getMana(),
                            playerTwo.getMana(), output, toJson);
                    break;
                case GET_CARDS_ON_TABLE:
                    toJson.getCardsOnTable(playerOne.getFrontRow(), playerOne.getBackRow(),
                            playerTwo.getFrontRow(), playerTwo.getBackRow(), output);
                    break;
                case CARD_USES_ATTACK:
                    actionDecider.cardUsesAttackIf(action, game, playerOne, playerTwo,
                            output, toJson);
                    break;
                case GET_CARD_AT_POSITION:
                    actionDecider.getCardAtPositionIf(action, game, playerOne, playerTwo,
                            output, toJson);
                    break;
                case CARD_USES_ABILITY:
                    actionDecider.cardUsesAbility(output, toJson, playerOne, playerTwo,
                            action, game);
                    break;
                case USE_ATTACK_HERO:
                    actionDecider.useAttackHero(playerOne, playerTwo, game, output, toJson,
                            winsManager, action);
                    break;
                case USE_HERO_ABILITY:
                    actionDecider.useHeroAbility(playerOne, playerTwo, game, output, toJson,
                            action);
                    break;
                case GET_FROZEN_CARDS_ON_TABLE:
                    toJson.getFrozenCardsOnTable(playerOne.getFrontRow(), playerOne.getBackRow(),
                            playerTwo.getFrontRow(), playerTwo.getBackRow(), output);
                    break;
                case GET_PLAYER_ONE_WINS:
                    toJson.getPlayerOneWins(winsManager.getWins().get(0), output);
                    break;
                case GET_PLAYER_TWO_WINS:
                    toJson.getPlayerTwoWins(winsManager.getWins().get(1), output);
                    break;
                case GET_TOTAL_GAMES_PLAYED:
                    toJson.getTotalGamesPlayed(winsManager.getWins().get(0) + winsManager.getWins().get(1), output);
                    break;
                default:
                    throw new IllegalArgumentException("Unknown command: " + command);
            }
        }
    }

    public int getShuffleSeed() {
        return shuffleSeed;
    }

    public void setShuffleSeed(final int shuffleSeed) {
        this.shuffleSeed = shuffleSeed;
    }

    public int getStartingIdx() {
        return startingIdx;
    }

    public void setStartingIdx(final int startingIdx) {
        this.startingIdx = startingIdx;
    }

    public int getTurn() {
        return turn;
    }

    public void setTurn(final int turnIdx) {
        this.turn = turnIdx;
    }

    public int getRound() {
        return round;
    }

    public void setRound(final int round) {
        this.round = round;
    }
}
