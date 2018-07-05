package pl.tscript3r.makao.collections;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import pl.tscript3r.makao.consts.CardsValues;
import pl.tscript3r.makao.domain.cards.Card;

public class CardDeckContainer {

	private final String MESSAGE_ERROR_NOCARDS = "No more cards in the deck";

	protected List<Card> deck = new ArrayList<Card>();
	
	public String getCardsList() {
		StringBuilder result = new StringBuilder();
		deck.forEach(card -> result.append(card.getName()));
		return result.toString();
	}

	public void generateDeck() {
		for (byte number : CardsValues.CARDS_LIST) {
			for (String color : CardsValues.CARDS_COLOR_LIST)
				deck.add(new Card(number, color));
		}
	}
	
	public void clearDeck() {
		deck.clear();
	}

	public void printDeck() {
		deck.forEach(card -> {
			System.out.println(card.getName());
		});
	}

	public Card getFirstCard() {
		if (!deck.isEmpty())
			return deck.get(0);
		else
			throw new IndexOutOfBoundsException(MESSAGE_ERROR_NOCARDS);
	}

	public Card popFirstCard() {
		if (!deck.isEmpty())
			return deck.remove(0);
		else
			throw new IndexOutOfBoundsException(MESSAGE_ERROR_NOCARDS);
	}

	public Card getCard(int cardId) {
		if (!deck.isEmpty())
			for (int i = 0; i < deck.size(); i++)
				if (deck.get(i).getId() == cardId)
					return deck.get(i);
				else
					;
		else
			throw new IndexOutOfBoundsException(MESSAGE_ERROR_NOCARDS);
		return null;
	}

	public Card popCard(int cardId) {
		if (!deck.isEmpty())
			for (int i = 0; i < deck.size(); i++)
				if (deck.get(i).getId() == cardId)
					return deck.remove(i);
				else;
		else
			throw new IndexOutOfBoundsException(MESSAGE_ERROR_NOCARDS);
		return null;
	}
	
	public int getCardsCount() {
		return deck.size();
	}

	public void addCard(Card card) {
		deck.add(card);
	}

	public void shuffle() {
		Collections.shuffle(deck);
	}

	public Boolean isCard(int cardNumber) {
		if (!deck.isEmpty())
			for (int i = 0; i < deck.size(); i++)
				if (deck.get(i).getNumber() == cardNumber)
					return true;
		return false;
	}

	public Boolean isCard(int cardNumber, String cardColor) {
		return false;

	}

	public Boolean hasColor(String cardColor) {
		if (!deck.isEmpty())
			for (int i = 0; i < deck.size(); i++)
				if (deck.get(i).getColor().equals(cardColor))
					return true;
		return false;
	}

	public Boolean hasNumber(byte cardNumber) {
		if (!deck.isEmpty())
			for (int i = 0; i < deck.size(); i++)
				if (deck.get(i).getNumber() == cardNumber)
					return true;
		return false;
	}

	public List<Card> getList() {
		return deck;
	}
	
	public Boolean isEmpty() {
		return deck.isEmpty();
	}
	
	public void sort() {
		Collections.sort(deck);
	}

}
