package pl.tscript3r.makao.domain.player;

import java.util.ArrayList;
import java.util.List;

import pl.tscript3r.makao.collections.CardDeckContainer;
import pl.tscript3r.makao.collections.GameCardDeckContainer;
import pl.tscript3r.makao.interfaces.MoveOption;
import pl.tscript3r.makao.utility.Printer;

public class HumanPlayer extends Player {

	public HumanPlayer(String playerName, GameCardDeckContainer gameDeck, CardDeckContainer cardsDeck) {
		super(playerName, gameDeck, cardsDeck);
	}

	@Override
	public void move() {
		if (sleepRounds == 0) {
			List<MoveOption> options = movesOptions.get();
			List<String> optionsMessages = new ArrayList<String>();

			if (!options.isEmpty()) {
				Printer.printMessage(getCardsList());
				
				if (options.size() == 1) {
					Printer.printMessage(
							"[" + name + "]: " + "Your only options is : " + options.get(0).getDescription());
					options.get(0).execute();
				} else {
					for (MoveOption option : options) {
						optionsMessages.add(option.getDescription());
					}
					int result = Printer.getMenuChoice("[" + name + "]: " + "Your options are:", optionsMessages,
							"Option: ");
					options.get(result).execute();
				}
				if (hasWon())
					Printer.printMessage("Player " + name + " has won the game! Hurey!");
			}

		} else {
			sleepRounds--;
			if (sleepRounds < 0)
				sleepRounds = 0;
			Printer.printMessage("Player " + name + " skips this round..");
		}
	}

	@Override
	public void demandCard() {
		List<MoveOption> options = movesOptions.getDemands();
		List<String> optionsMessages = new ArrayList<String>();

		for (MoveOption option : options) {
			optionsMessages.add(option.getDescription());
		}

		int result = Printer.getMenuChoice("Your demands options are:", optionsMessages, "Demand: ");
		options.get(result).execute();
	}

}
