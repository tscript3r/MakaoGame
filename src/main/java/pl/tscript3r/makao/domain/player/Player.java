package pl.tscript3r.makao.domain.player;


import pl.tscript3r.makao.collections.CardDeckContainer;
import pl.tscript3r.makao.collections.GameCardDeckContainer;
import pl.tscript3r.makao.domain.cards.Card;
import pl.tscript3r.makao.utility.Printer;

public abstract class Player {

    private static int id = 0;
    private final int playersId;
    int sleepRounds = 0;
    final String name;
    final CardDeckContainer ownDeck = new CardDeckContainer();
    final GameCardDeckContainer gameDeck;
    final MovesOptionImpl movesOptionImpl;

    Player(String playerName, GameCardDeckContainer gameDeck, CardDeckContainer cardsDeck) {
        this.playersId = id++;
        this.name = playerName;
        this.gameDeck = gameDeck;
        movesOptionImpl = new MovesOptionImpl(this, gameDeck, ownDeck, cardsDeck);
    }

    public void setSleepRounds(int sleepRounds) {
        this.sleepRounds = sleepRounds;
        Printer.printMessage(
                "Player " + name + "skips " + sleepRounds + " rounds.\r\n");
    }

    public Boolean hasWon() {
        return ownDeck.isEmpty();
    }

    public void clearDeck() {
        ownDeck.clearDeck();
    }

    public int getId() {
        return playersId;
    }

    public void addCard(Card card) {
        ownDeck.addCard(card);
        ownDeck.sortCards();
    }

    String getCardsList() {
        return ownDeck.getCardsList();
    }

    public abstract void demandCard();

    public abstract void move();
}
