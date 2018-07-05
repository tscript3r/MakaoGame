package pl.tscript3r.makao.domain.cards;

import java.security.InvalidParameterException;

import pl.tscript3r.makao.consts.CardsValues;
import pl.tscript3r.makao.utility.CardStringTranslator;

public class Card implements Comparable<Card> {
	
	protected static int cardId = 0;
	protected byte number;
	protected String color;
	protected int id;
	
	public Card(byte cardNumber, String cardColor) {
		id = cardId++;
		if(cardNumber > CardsValues.CARD_MAX_VALUE || cardNumber < CardsValues.CARD_MIN_VALUE)
			throw new InvalidParameterException("Card number out of range");
		this.number = cardNumber;
		this.color = cardColor;
	}
	
	public String getName() {
		return "[" + CardStringTranslator.getName(this) + color + "]";
	}

	public byte getNumber() {
		return number;
	}
	
	public String getColor() {
		return color;
	}
	
	public int getId() {
		return id;
	}

	@Override
	public int compareTo(Card arg0) {
		return this.getNumber() - arg0.getNumber();
	}

}
