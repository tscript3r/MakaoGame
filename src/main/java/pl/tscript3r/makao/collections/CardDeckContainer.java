package pl.tscript3r.makao.collections;

import pl.tscript3r.makao.consts.CardValues;
import pl.tscript3r.makao.domain.cards.Card;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CardDeckContainer {

    private final String MESSAGE_ERROR_NO_CARDS = "No more cards in the deck";
    final List<Card> deck = new ArrayList<>();

    public String getCardsList() {
        StringBuilder result = new StringBuilder();
        deck.forEach(card -> result.append(card.getCardName()));
        return result.toString();
    }

    public void generateDeck() {
        for (byte number : CardValues.CARDS_LIST) {
            for (String color : CardValues.CARDS_COLOR_LIST)
                deck.add(new Card(number, color));
        }
    }

    public void clearDeck() {
        deck.clear();
    }

    public Card popFirstCard() {
        if (!deck.isEmpty())
            return deck.remove(0);
        else
            throw new IndexOutOfBoundsException(MESSAGE_ERROR_NO_CARDS);
    }

    public Card popCard(int cardId) {
        if (!deck.isEmpty())
            for (int i = 0; i < deck.size(); i++) {
                if (deck.get(i).getCardId() == cardId)
                    return deck.remove(i);
            }
        else
            throw new IndexOutOfBoundsException(MESSAGE_ERROR_NO_CARDS);
        return null;
    }

    public int getCardsCount() {
        return deck.size();
    }

    public void addCard(Card card) {
        deck.add(card);
    }

    public void shuffleCards() {
        Collections.shuffle(deck);
    }

    public Boolean hasCardColor(String cardColor) {
        for (Card card : deck)
            if(card.getCardColor().equals(cardColor))
                return true;

        return false;
    }

    public Boolean hasCardNumber(byte cardNumber) {
        if (!deck.isEmpty())
            for (Card card : deck)
                if(card.getCardNumber() == cardNumber)
                    return true;

        return false;
    }

    public List<Card> getList() {
        return deck;
    }

    public Boolean isEmpty() {
        return deck.isEmpty();
    }

    public void sortCards() {
        Collections.sort(deck);
    }

}
