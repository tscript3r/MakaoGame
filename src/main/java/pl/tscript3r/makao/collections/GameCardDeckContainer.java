package pl.tscript3r.makao.collections;

import pl.tscript3r.makao.consts.CardsValues;
import pl.tscript3r.makao.domain.cards.Card;

public class GameCardDeckContainer extends CardDeckContainer {
	private int demandPlayerId = -1;
	private byte demandNumber = 0;
	private int sleepCount = 0;

	private int pickCount = 1;
	private Boolean pickEvent = false;
	private Boolean sleepEvent = false;
	private Boolean demandEvent = false;
	private Boolean demandApplied = false;

	private Boolean isPickCard(Card card) {
		for (byte cardNumber : CardsValues.CARDS_PICK_LIST) {
			if (card.getNumber() == cardNumber)
				return true;
			if (card.getNumber() == CardsValues.CARD_KING)
				for (String cardColor : CardsValues.CARDS_COLOR_LIST)
					if (cardColor.equals(card.getColor()))
						return true;
		}
		return false;
	}

	public void addCard(int playersId, Card card) {
		if (demandEvent)
			if (playersId == demandPlayerId) {
				// end of the event
				demandEvent = false;
				demandApplied = false;
				demandNumber = 0;
				demandPlayerId = -1;
			} else
				if (card.getNumber() == demandNumber) 
					demandApplied = true;

		if (card.getNumber() == CardsValues.CARD_SLEEP) {
			sleepCount++;
			sleepEvent = true;
		}

		if (isPickCard(card)) {
			pickEvent = true;
			switch (card.getNumber()) {
			case CardsValues.CARD_TWO:
				pickCount += 2;
				break;
			case CardsValues.CARD_THREE:
				pickCount += 3;
				break;
			case CardsValues.CARD_KING:
				for (String color : CardsValues.CARDS_PICK_KING_COLORS)
					if (card.getColor().equals(color))
						pickCount += 5;	
			}
		}

		super.addCard(card);
	}

	@Override
	public Card getFirstCard() {
		if (!deck.isEmpty())
			return deck.get(deck.size() - 1);
		else
			return null;
	}

	public Boolean isPickEvent() {
		return pickEvent;
	}

	public Boolean isSleepEvent() {
		return sleepEvent;
	}

	public int getSleepCount() {
		int tmp = sleepCount;
		sleepCount = 0;
		sleepEvent = false;
		return tmp;
	}

	public int getPickCount() {
		return (pickCount > 1) ? pickCount - 1 : pickCount;
	}
	
	public void setPicketUp() {
		if (pickEvent) {
			// Default has to be 1
			pickCount = 1;
			pickEvent = false;
		}	
	}

	public Boolean isDemandEvent() {
		return demandEvent;
	}

	public Boolean isDemandApplied() {
		return demandApplied;
	}

	public byte getDemandNumber() {
		return demandNumber;
	}

	public void setDemandNumber(int playersId, byte number) {
		demandEvent = true;
		demandNumber = number;
		demandPlayerId = playersId;
	}

	public Boolean isAnyEvent() {
		if (!isPickEvent() && !isSleepEvent() && !isDemandEvent())
			return false;
		else
			return true;
	}

}
