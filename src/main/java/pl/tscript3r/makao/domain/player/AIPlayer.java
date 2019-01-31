package pl.tscript3r.makao.domain.player;

import pl.tscript3r.makao.collections.CardDeckContainer;
import pl.tscript3r.makao.collections.GameCardDeckContainer;
import pl.tscript3r.makao.utility.Printer;

import java.util.List;

public class AIPlayer extends Player {

    public AIPlayer(String playerName, GameCardDeckContainer gameDeck, CardDeckContainer cardsDeck) {
        super(playerName, gameDeck, cardsDeck);
    }

    @Override
    public void move() {
        if (sleepRounds == 0) {
            List<MoveOption> options = movesOptionImpl.get();

            if (ownDeck.getCardsCount() == 1)
                Printer.printMessage(name + ": MAKAO!");

            if (!options.isEmpty()) {

                if (options.size() == 1) {
                    options.get(0).execute();
                } else {
                    int result = 0;
                    for (int i = 0; i < options.size(); i++)
                        if (options.get(i).value() >= options.get(result).value())
                            result = i;

                    Printer.printMessage("[" + name + "]: " + options.get(result).getDescription());
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
        List<MoveOption> options = movesOptionImpl.getDemands();
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
