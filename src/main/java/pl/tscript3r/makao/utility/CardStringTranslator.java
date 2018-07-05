package pl.tscript3r.makao.utility;

import pl.tscript3r.makao.domain.cards.Card;

public class CardStringTranslator {

	private static String translateCardNumberToString(byte number) {
		String result = "Unknown";
		if (number >= 2 && number <= 14)
			switch (number) {
				case 11: result = "J "; break;
				case 12: result = "Q "; break;
				case 13: result = "K "; break;
				case 14: result = "A "; break;
				default: 
					result = Byte.toString(number); 
					if(result.length() == 1)
						result = result + " ";
				break;
			}
		return result;
	}

	public synchronized static String getName(Card card) {
		return translateCardNumberToString(card.getNumber());
	}
}
