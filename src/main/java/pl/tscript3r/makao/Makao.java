package pl.tscript3r.makao;

import pl.tscript3r.makao.collections.CardDeckContainer;
import pl.tscript3r.makao.collections.GameCardDeckContainer;
import pl.tscript3r.makao.domain.cards.Card;
import pl.tscript3r.makao.domain.player.AIPlayer;
import pl.tscript3r.makao.domain.player.HumanPlayer;
import pl.tscript3r.makao.domain.player.Player;

import java.util.ArrayList;
import java.util.List;

public class Makao {

    private final CardDeckContainer cardsDeck = new CardDeckContainer();
    private final GameCardDeckContainer gameDeck = new GameCardDeckContainer();
    private final List<Player> players = new ArrayList<>();

    public void addHumanPlayer(String name) {
        players.add(new HumanPlayer(name, gameDeck, cardsDeck));
    }

    public void addAIPlayer(String name) {
        players.add(new AIPlayer(name, gameDeck, cardsDeck));
    }

    private void clearDecks() {
        cardsDeck.clearDeck();
        gameDeck.clearDeck();
        players.forEach(Player::clearDeck);
    }

    private void generateCards() {
        cardsDeck.generateDeck();
        int decksCount = (players.size() / 4) + 1;
        for (int i = 0; i < decksCount; i++)
            cardsDeck.generateDeck();
    }

    private void shuffleCards() {
        cardsDeck.shuffleCards();
    }

    private void distributeCards(int cardsPerPlayer) {
        gameDeck.addCard(cardsDeck.popFirstCard());
        players.forEach(player -> {
            for (int i = 0; i <= cardsPerPlayer; i++) {
                Card card = cardsDeck.popFirstCard();
                player.addCard(card);
            }
        });
    }

    private void printGameDeck() {
        Card card = gameDeck.getFirstCard();
        System.out.println("\r\nGame deck: " + card.getCardName() + " \r\n");
    }

    private void transferCards(CardDeckContainer from, CardDeckContainer to) {
        if (from.getCardsCount() > 1)
            for (int i = 0; i < from.getCardsCount() - 1; i++)
                to.addCard(from.popFirstCard());
    }

    private void play() {
        Boolean won = false;
        while (true) {

            for (Player player : players) {
                if (player instanceof HumanPlayer)
                    printGameDeck();
                player.move();
                won = player.hasWon();
                if (won)
                    break;
            }

            if (won)
                break;

            transferCards(gameDeck, cardsDeck);
        }
    }

    public void start(int cardsPerPlayer) {
        clearDecks();
        generateCards();
        shuffleCards();
        distributeCards(cardsPerPlayer);
        play();
    }

}
