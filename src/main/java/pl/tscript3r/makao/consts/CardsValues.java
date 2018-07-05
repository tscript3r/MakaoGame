package pl.tscript3r.makao.consts;

public class CardsValues {

	public final static byte CARD_TWO = 2;
	public final static byte CARD_THREE = 3;
	public final static byte CARD_FOUR = 4;
	public final static byte CARD_FIVE = 5;
	public final static byte CARD_SIX = 6;
	public final static byte CARD_SEVEN = 7;
	public final static byte CARD_EIGHT = 8;
	public final static byte CARD_NINE = 9;
	public final static byte CARD_TEEN = 10;
	public final static byte CARD_KNIGHT = 11;
	public final static byte CARD_QUEEN = 12;
	public final static byte CARD_KING = 13;
	public final static byte CARD_ACE = 14;

	public final static byte CARD_MIN_VALUE = CARD_TWO;
	public final static byte CARD_MAX_VALUE = CARD_ACE;
	
	public final static byte CARD_DEMANDOR = CARD_KNIGHT;
	public final static byte CARD_SLEEP = CARD_FOUR;

	public final static byte CARDS_LIST[] = { CARD_TWO, CARD_THREE, CARD_FOUR, CARD_FIVE, CARD_SIX, CARD_SEVEN,
			CARD_EIGHT, CARD_NINE, CARD_TEEN, CARD_KNIGHT, CARD_QUEEN, CARD_KING, CARD_ACE };

	public final static String CARDS_COLOR_CLUB = "♣";
	public final static String CARDS_COLOR_DIAMOND = "♦";
	public final static String CARDS_COLOR_HEART = "♥";
	public final static String CARDS_COLOR_SPADES = "♠";

	public final static String CARDS_COLOR_LIST[] = { CARDS_COLOR_CLUB, CARDS_COLOR_DIAMOND, CARDS_COLOR_HEART,
			CARDS_COLOR_SPADES };

	public final static byte[] CARDS_PICK_LIST = { CARD_TWO, CARD_THREE };
	public final static String[] CARDS_PICK_KING_COLORS = { CARDS_COLOR_SPADES, CARDS_COLOR_HEART };

	public final static byte[] CARDS_DEMAND_LIST = { CARD_FIVE, CARD_SIX, CARD_SEVEN, CARD_EIGHT, CARD_NINE,
			CARD_TEEN };
	

}
