package pl.tscript3r.makao.domain.player;

import java.util.ArrayList;
import java.util.List;

import pl.tscript3r.makao.collections.CardDeckContainer;
import pl.tscript3r.makao.collections.GameCardDeckContainer;
import pl.tscript3r.makao.consts.CardsValues;
import pl.tscript3r.makao.domain.cards.Card;
import pl.tscript3r.makao.interfaces.MoveOption;

public class MovesOptions {

	private final static String MESSAGE_PICK = "Pick a card";
	private final static String MESSAGE_PICK_MULTIPLE = "Pick %d cards";
	private final static String MESSAGE_THROW = "Throw your %s card";
	private final static String MESSAGE_THROW_DEMAND = MESSAGE_THROW + " and demand a card";
	private final static String MESSAGE_DEMAND = "Demand %s card";
	private final static String MESSAGE_DEMAND_NOTHING = "Demand nothing";

	private Player player;
	private GameCardDeckContainer gameDeck;
	private CardDeckContainer playersDeck;
	private CardDeckContainer cardsDeck;
	private List<MoveOption> options;

	class ThrowMoveOption implements MoveOption {

		private String message;
		private Card card;

		public ThrowMoveOption(Card card, String message) {
			this.message = message;
			this.card = card;
		}

		@Override
		public String getDescription() {
			return message;
		}

		@Override
		public void execute() {
			gameDeck.addCard(player.getId(), playersDeck.popCard(card.getId()));
		}

		@Override
		public byte value() {
			return 1;
		}

	}

	class DemandMoveOption implements MoveOption {

		private Card card;

		public DemandMoveOption() {
		}

		public DemandMoveOption(Card card) {
			this.card = card;
		}

		@Override
		public String getDescription() {
			if (card != null)
				return String.format(MESSAGE_DEMAND, card.getName());
			else
				return MESSAGE_DEMAND_NOTHING;
		}

		@Override
		public void execute() {
			if (card != null)
				gameDeck.setDemandNumber(player.getId(), card.getNumber());

		}

		@Override
		public byte value() {
			return 1;
		}
	}

	public MovesOptions(Player player, GameCardDeckContainer gameDeck, CardDeckContainer playersDeck,
			CardDeckContainer cardsDeck) {
		this.player = player;
		this.gameDeck = gameDeck;
		this.cardsDeck = cardsDeck;
		this.playersDeck = playersDeck;
	}

	private void setGetCardOption() {
		options.add(new MoveOption() {

			@Override
			public String getDescription() {
				if (gameDeck.isPickEvent())
					return String.format(MESSAGE_PICK_MULTIPLE, gameDeck.getPickCount());
				else
					return MESSAGE_PICK;
			}

			@Override
			public void execute() {
				for (int i = 0; i <= gameDeck.getPickCount(); i++)
					playersDeck.addCard(cardsDeck.popFirstCard());
				gameDeck.setPicketUp();
			}

			@Override
			public byte value() {
				return 0;
			}

		});
	}

	private void setNoEventOptions() {
		if (playersDeck.hasColor(gameDeck.getFirstCard().getColor())
				|| playersDeck.hasNumber(gameDeck.getFirstCard().getNumber())) {
			String color = gameDeck.getFirstCard().getColor();
			byte number = gameDeck.getFirstCard().getNumber();
			List<Card> list = playersDeck.getList();

			list.forEach(card -> {
				if (card.getColor().equals(color) || card.getNumber() == number)
					if (card.getNumber() != CardsValues.CARD_KNIGHT)
						options.add(new ThrowMoveOption(card, String.format(MESSAGE_THROW, card.getName())));
					else
						options.add(new ThrowMoveOption(card, String.format(MESSAGE_THROW_DEMAND, card.getName())) {

							@Override
							public void execute() {
								gameDeck.addCard(player.getId(), playersDeck.popCard(card.getId()));
								player.demandCard();
							}
							
							@Override
							public byte value() {
								return 2;
							}

						});
			});
		}
	}

	private void setDemandOptions() {
		List<Card> list = playersDeck.getList();
		list.forEach(card -> {

			if (card.getNumber() == gameDeck.getDemandNumber())
				options.add(new ThrowMoveOption(card, String.format(MESSAGE_THROW, card.getName())));

			if (gameDeck.getFirstCard().getNumber() == CardsValues.CARD_KNIGHT
					&& card.getNumber() == CardsValues.CARD_KNIGHT)
				options.add(new ThrowMoveOption(card, String.format(MESSAGE_THROW_DEMAND, card.getName())) {

					@Override
					public void execute() {
						gameDeck.addCard(player.getId(), playersDeck.popCard(card.getId()));
						player.demandCard();
					}

				});
		});

	}

	private void setSleepOptions() {
		if (playersDeck.hasNumber(CardsValues.CARD_FOUR)) {
			List<Card> list = playersDeck.getList();
			list.forEach(card -> {
				if (card.getNumber() == CardsValues.CARD_FOUR)
					options.add(new ThrowMoveOption(card, String.format(MESSAGE_THROW, card.getName())));
			});
		} else
			player.setSleepRounds(gameDeck.getSleepCount());
	}

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

	private void setPickOptions() {
		Card latestCard = gameDeck.getFirstCard();
		List<Card> list = playersDeck.getList();
		list.forEach(card -> {
			if (card.getNumber() == latestCard.getNumber() && isPickCard(card)
					|| isPickCard(card) && card.getColor().equals(latestCard.getColor()))
				options.add(new ThrowMoveOption(card, String.format(MESSAGE_THROW, card.getName())));
		});
	}

	private void setEventOptions() {
		if (gameDeck.isDemandEvent())
			setDemandOptions();
		if (gameDeck.isSleepEvent())
			setSleepOptions();
		if (gameDeck.isPickEvent())
			setPickOptions();
	}

	public List<MoveOption> getDemands() {
		List<Card> list = playersDeck.getList();
		options = new ArrayList<MoveOption>();
		options.add(new DemandMoveOption());
		list.forEach(card -> {
			for (byte cardNumber : CardsValues.CARDS_DEMAND_LIST)
				if (card.getNumber() == cardNumber)
					options.add(new DemandMoveOption(card));
		});

		return options;
	}

	public List<MoveOption> get() {
		options = new ArrayList<MoveOption>();

		if (!gameDeck.isSleepEvent())
			setGetCardOption();

		if (!gameDeck.isAnyEvent())
			setNoEventOptions();
		else
			setEventOptions();

		return options;
	}
}
