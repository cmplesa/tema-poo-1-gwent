package org.poo.main;

import com.fasterxml.jackson.databind.node.ArrayNode;
import org.poo.fileio.ActionsInput;
import org.poo.fileio.CardInput;
import org.poo.fileio.DecksInput;
import org.poo.fileio.GameInput;

import java.util.ArrayList;

public class Game {

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
     * This method draws a card from the deck and adds it to the player's hand.
     *
     * @param player the player who will draw the card
     * @param deck the deck from which the card will be drawn
     * @return the updated hand of the player
     */
    public ArrayList<Card> drawCard(final Player player, final ArrayList<Card> deck) {
        if (deck.size() > 0) {
            player.getHand().add(deck.get(0));
            deck.remove(0);
        }
        return player.getHand();
    }

    /**
     * This method creates decks for a player based on the provided deck input.
     *
     * @param decksInput the input containing the deck information
     * @return the created decks for the player
     */
    public Decks createDecksForPlayer(final DecksInput decksInput) {
        ArrayList<ArrayList<CardInput>> decks = decksInput.getDecks();
        ArrayList<ArrayList<Card>> playerDecks = new ArrayList<>();
        Decks deckList = null;
        ArrayList<CardInput> deckInput = new ArrayList<>();

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
     * This method ends the current player's turn and prepares the game state for the next turn.
     *
     * @param playerOneDeck the deck of the first player
     * @param playerTwoDeck the deck of the second player
     * @param playerOne the first player
     * @param playerTwo the second player
     * @param game the current game instance
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
     * This method unblocks the cards in the player's deck by resetting their status.
     *
     * @param player the player whose cards will be unblocked
     * @param deck the deck of cards to be unblocked
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
     * This method sets up the game by drawing initial cards for
     * both players and setting the initial mana.
     * @param playerOne the first player
     * @param playerTwo the second player
     * @param game the current game instance
     * @param playerOneDeck the deck of the first player
     * @param playerTwoDeck the deck of the second player
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
     * This method runs the game by processing the actions
     * input and updating the game state accordingly.
     *
     * @param playerOne the first player
     * @param playerTwo the second player
     * @param actionsInput the list of actions to be processed
     * @param game the current game instance
     * @param wins the list containing the number of wins for each player
     * @param output the JSON output node to store the results
     */
    public void run(final Player playerOne, final Player playerTwo,
                    final ArrayList<ActionsInput> actionsInput, final Game game,
                    final ArrayList<Integer> wins, final ArrayNode output) {
        ArrayList<Card> playerOneDeck = playerOne.getPlayerDeck(playerOne.getPlayerDeckIdx(),
                playerOne, game);
        ArrayList<Card> playerTwoDeck = playerTwo.getPlayerDeck(playerTwo.getPlayerDeckIdx(),
                playerTwo, game);
        setTheGame(playerOne, playerTwo, game, playerOneDeck, playerTwoDeck);
        ToJson toJson = new ToJson();
        ActionDecider actionDecider = new ActionDecider();

        for (ActionsInput action : actionsInput) {
            if (action.getCommand().equals("getPlayerDeck")) {
                actionDecider.getDeckIf(action.getPlayerIdx(), output, toJson, playerOneDeck,
                        playerTwoDeck);
            } else if (action.getCommand().equals("getPlayerHero")) {
                actionDecider.getPlayerHeroIf(action.getPlayerIdx(), output, toJson,
                        playerOne.getPlayerHero(), playerTwo.getPlayerHero());
            } else if (action.getCommand().equals("getPlayerTurn")) {
                toJson.getPlayerTurn(action, game.getTurn(), output);
            } else if (action.getCommand().equals("endPlayerTurn")) {
                endTurn(playerOneDeck, playerTwoDeck, playerOne, playerTwo, game);
            } else if (action.getCommand().equals("placeCard")) {
                actionDecider.placeCardIf(action, game, playerOne, playerTwo,
                        action.getPlayerIdx(), output, toJson);
            } else if (action.getCommand().equals("getCardsInHand")) {
                actionDecider.getCardsInHandIf(action.getPlayerIdx(), output, toJson,
                        playerOne, playerTwo);
            } else if (action.getCommand().equals("getPlayerMana")) {
                actionDecider.getPlayerManaIf(action.getPlayerIdx(), playerOne.getMana(),
                        playerTwo.getMana(), output, toJson);
            } else if (action.getCommand().equals("getCardsOnTable")) {
                toJson.getCardsOnTable(playerOne.getFrontRow(), playerOne.getBackRow(),
                        playerTwo.getFrontRow(), playerTwo.getBackRow(), output);
            } else if (action.getCommand().equals("cardUsesAttack")) {
                actionDecider.cardUsesAttackIf(action, game, playerOne, playerTwo, output, toJson);
            } else if (action.getCommand().equals("getCardAtPosition")) {
                actionDecider.getCardAtPositionIf(action, game, playerOne, playerTwo,
                        output, toJson);
            } else if (action.getCommand().equals("cardUsesAbility")) {
                actionDecider.cardUsesAbility(output, toJson, playerOne, playerTwo, action, game);
            } else if (action.getCommand().equals("useAttackHero")) {
                actionDecider.useAttackHero(playerOne, playerTwo, game, output,
                        toJson, wins, action);
            } else if (action.getCommand().equals("useHeroAbility")) {
                actionDecider.useHeroAbility(playerOne, playerTwo, game, output, toJson, action);
            } else if (action.getCommand().equals("getFrozenCardsOnTable")) {
                toJson.getFrozenCardsOnTable(playerOne.getFrontRow(), playerOne.getBackRow(),
                        playerTwo.getFrontRow(), playerTwo.getBackRow(), output);
            } else if (action.getCommand().equals("getPlayerOneWins")) {
                toJson.getPlayerOneWins(wins.get(0), output);
            } else if (action.getCommand().equals("getPlayerTwoWins")) {
                toJson.getPlayerTwoWins(wins.get(1), output);
            } else if (action.getCommand().equals("getTotalGamesPlayed")) {
                toJson.getTotalGamesPlayed(wins.get(0) + wins.get(1), output);
            }
        }
    }

    /**
     * This method returns the row of cards for a given player and row index.
     *
     * @param playerOne the first player
     * @param playerTwo the second player
     * @param x the row index
     * @return the row of cards
     */
    public ArrayList<Card> getRow(final Player playerOne, final Player playerTwo, final int x) {
        if (x == 0) {
            return playerTwo.getBackRow();
        } else if (x == 1) {
            return playerTwo.getFrontRow();
        } else if (x == 2) {
            return playerOne.getFrontRow();
        } else {
            return playerOne.getBackRow();
        }
    }

    /**
     * This method attacks a card with another card.
     * @param cardAttacked
     * @param cardAttacker
     * @param row
     */

    public void attackCard(final Card cardAttacked, final Card cardAttacker,
                           final ArrayList<Card> row) {
        Minion minionAttacker = (Minion) cardAttacker;
        if (cardAttacker != null && cardAttacked != null) {

            if (minionAttacker != null) {
                cardAttacked.setHealth(cardAttacked.getHealth() - minionAttacker.getAttackDamage());
            }
            if (cardAttacked.getHealth() <= 0) {
                row.remove(cardAttacked);
            }


            cardAttacker.setHasAttacked(1);
        }
    }

    /**
     * This method returns the seed used for shuffling the decks.
     * @return
     */
    public int getShuffleSeed() {
        return shuffleSeed;
    }

    /**
     * This method sets the seed used for shuffling the decks.
     * @param shuffleSeed
     */
    public void setShuffleSeed(final int shuffleSeed) {
        this.shuffleSeed = shuffleSeed;
    }

    /**
     * This method returns the starting player index.
     * @return
     */
    public int getStartingIdx() {
        return startingIdx;
    }
    /**
     * This method sets the starting player index.
     * @param startingIdx
     */
    public void setStartingIdx(final int startingIdx) {
        this.startingIdx = startingIdx;
    }
    /**
     * This method returns the current turn index.
     * @return
     */
    public int getTurn() {
        return turn;
    }
    /**
     * This method sets the current turn index.
     * @param turnIdx
     */
    public void setTurn(final int turnIdx) {
        this.turn = turnIdx;
    }
    /**
     * This method returns the current round index.
     * @return
     */
    public int getRound() {
        return round;
    }
    /**
     * This method sets the current round index.
     * @param round
     */
    public void setRound(final int round) {
        this.round = round;
    }
}
