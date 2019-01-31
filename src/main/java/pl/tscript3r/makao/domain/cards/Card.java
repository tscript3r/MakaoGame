package pl.tscript3r.makao.domain.cards;

import pl.tscript3r.makao.consts.CardValues;
import pl.tscript3r.makao.utility.CardToStringTranslator;

import java.security.InvalidParameterException;

public class Card implements Comparable<Card> {

    private static int cardId = 0;
    private final int id;
    private byte number;
    private String color;

    public Card(byte cardNumber, String cardColor) {
        id = cardId++;
        if (cardNumber > CardValues.CARD_MAX_VALUE || cardNumber < CardValues.CARD_MIN_VALUE)
            throw new InvalidParameterException("Card number out of range");
        this.number = cardNumber;
        this.color = cardColor;
    }

    public String getCardName() {
        return "[" + CardToStringTranslator.getName(this) + color + "]";
    }

    public byte getCardNumber() {
        return number;
    }

    public String getCardColor() {
        return color;
    }

    public int getCardId() {
        return id;
    }

    @Override
    public int compareTo(Card arg0) {
        return this.getCardNumber() - arg0.getCardNumber();
    }

}
