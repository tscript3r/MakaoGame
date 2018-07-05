package pl.tscript3r.makao.domain.player;

import java.util.List;

import pl.tscript3r.makao.collections.CardDeckContainer;
import pl.tscript3r.makao.collections.GameCardDeckContainer;
import pl.tscript3r.makao.interfaces.MoveOption;
import pl.tscript3r.makao.utility.Printer;

public class AIPlayer extends Player {

	public AIPlayer(String playerName, GameCardDeckContainer gameDeck, CardDeckContainer cardsDeck) {
		super(playerName, gameDeck, cardsDeck);
	}

	@Override
	public void move() {
		if (sleepRounds == 0) {
			List<MoveOption> options = movesOptions.get();

			if(ownDeck.getCardsCount() == 1)
				Printer.printMessage(name + ": MAKAO!");
			
			if (!options.isEmpty()) {

				if (options.size() == 1) {
					options.get(0).execute();
				} else {
					int result = 0;
					for (int i = 0; i < options.size(); i++)
						if (options.get(i).value() >= options.get(result).value())
							result = i;
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
		if (options.size() > 1) {
			int result = 0;
			for (int i = 0; i < options.size(); i++)
				if (options.get(i).value() >= options.get(result).value())
					result = i;
			options.get(result).execute();
		} else
			options.get(0).execute();
	}

}
