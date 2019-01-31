package pl.tscript3r.makao.collections;

import pl.tscript3r.makao.consts.CardValues;
import pl.tscript3r.makao.domain.cards.Card;

public class GameCardDeckContainer extends CardDeckContainer {
    private int demandPlayerId = -1;
    private byte demandNumber = 0;
    private int sleepCount = 0;

    private int pickCount = 1;
    private Boolean pickEvent = false;
    private Boolean sleepEvent = false;
    private Boolean demandEvent = false;

    private Boolean isPickCard(Card card) {
        for (byte cardNumber : CardValues.CARDS_PICK_LIST) {
            if (card.getCardNumber() == cardNumber)
                return true;
            if (card.getCardNumber() == CardValues.CARD_KING)
                for (String cardColor : CardValues.CARDS_COLOR_LIST)
                    if (cardColor.equals(card.getCardColor()))
                        return true;
        }
        return false;
    }

    public void addCard(int playersId, Card card) {

        if (demandEvent)
            if (playersId == demandPlayerId) {
                // end of the demand event
                demandEvent = false;
                demandNumber = 0;
                demandPlayerId = -1;
            }

        if (card.getCardNumber() == CardValues.CARD_SLEEP) {
            sleepCount++;
            sleepEvent = true;
        }

        if (isPickCard(card)) {
            pickEvent = true;
            switch (card.getCardNumber()) {
                case CardValues.CARD_TWO:
                    pickCount += 2;
                    break;
                case CardValues.CARD_THREE:
                    pickCount += 3;
                    break;
                case CardValues.CARD_KING:
                    for (String color : CardValues.CARDS_PICK_KING_COLORS)
                        if (card.getCardColor().equals(color))
                            pickCount += 5;
            }
        }

        super.addCard(card);
    }

    public Card getFirstCard() {
        if (!deck.isEmpty())
            return deck.get(deck.size() - 1);
        else
            return null;
    }

    public Boolean isPickGameEvent() {
        return pickEvent;
    }

    public Boolean isSleepGameEvent() {
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

    public Boolean isDemandGameEvent() {
        return demandEvent;
    }

    public byte getDemandNumber() {
        return demandNumber;
    }

    public void setDemandNumber(int playersId, byte number) {
        demandEvent = true;
        demandNumber = number;
        demandPlayerId = playersId;
    }

    public Boolean isAnyGameEvent() {
        return (isPickGameEvent() || isSleepGameEvent() || isDemandGameEvent());
    }

}
