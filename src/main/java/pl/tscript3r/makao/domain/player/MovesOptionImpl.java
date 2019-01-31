package pl.tscript3r.makao.domain.player;

import pl.tscript3r.makao.collections.CardDeckContainer;
import pl.tscript3r.makao.collections.GameCardDeckContainer;
import pl.tscript3r.makao.consts.CardValues;
import pl.tscript3r.makao.domain.cards.Card;

import java.util.ArrayList;
import java.util.List;

public class MovesOptionImpl {

    private final static String MESSAGE_PICK = "Pick a card";
    private final static String MESSAGE_PICK_MULTIPLE = "Pick %d cards";
    private final static String MESSAGE_THROW = "Throw your %s card";
    private final static String MESSAGE_THROW_DEMAND = MESSAGE_THROW + " and demand a card";
    private final static String MESSAGE_DEMAND = "Demand %s card";
    private final static String MESSAGE_DEMAND_NOTHING = "Demand nothing";

    private final Player player;
    private final GameCardDeckContainer gameDeck;
    private final CardDeckContainer playersDeck;
    private final CardDeckContainer cardsDeck;
    private List<MoveOption> options;

    class ThrowMoveOption implements MoveOption {

        private final String message;
        private final Card card;

        private ThrowMoveOption(Card card, String message) {
            this.message = message;
            this.card = card;
        }

        @Override
        public String getDescription() {
            return message;
        }

        @Override
        public void execute() {
            gameDeck.addCard(player.getId(), playersDeck.popCard(card.getCardId()));
        }

        @Override
        public byte value() {
            return 1;
        }

    }

    class DemandMoveOption implements MoveOption {

        private Card card;

        private DemandMoveOption() {
        }

        private DemandMoveOption(Card card) {
            this.card = card;
        }

        @Override
        public String getDescription() {
            if (card != null)
                return String.format(MESSAGE_DEMAND, card.getCardName());
            else
                return MESSAGE_DEMAND_NOTHING;
        }

        @Override
        public void execute() {
            if (card != null)
                gameDeck.setDemandNumber(player.getId(), card.getCardNumber());

        }

        @Override
        public byte value() {
            return 1;
        }
    }

    public MovesOptionImpl(Player player, GameCardDeckContainer gameDeck, CardDeckContainer playersDeck,
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
                if (gameDeck.isPickGameEvent())
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
        if (playersDeck.hasCardColor(gameDeck.getFirstCard().getCardColor())
                || playersDeck.hasCardNumber(gameDeck.getFirstCard().getCardNumber())) {
            String color = gameDeck.getFirstCard().getCardColor();
            byte number = gameDeck.getFirstCard().getCardNumber();
            List<Card> list = playersDeck.getList();

            list.forEach(card -> {
                if (card.getCardColor().equals(color) || card.getCardNumber() == number)
                    if (card.getCardNumber() != CardValues.CARD_KNIGHT)
                        options.add(new ThrowMoveOption(card, String.format(MESSAGE_THROW, card.getCardName())));
                    else
                        options.add(new ThrowMoveOption(card, String.format(MESSAGE_THROW_DEMAND, card.getCardName())) {

                            @Override
                            public void execute() {
                                gameDeck.addCard(player.getId(), playersDeck.popCard(card.getCardId()));
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

            if (card.getCardNumber() == gameDeck.getDemandNumber())
                options.add(new ThrowMoveOption(card, String.format(MESSAGE_THROW, card.getCardName())));

            if (gameDeck.getFirstCard().getCardNumber() == CardValues.CARD_KNIGHT
                    && card.getCardNumber() == CardValues.CARD_KNIGHT)
                options.add(new ThrowMoveOption(card, String.format(MESSAGE_THROW_DEMAND, card.getCardName())) {

                    @Override
                    public void execute() {
                        gameDeck.addCard(player.getId(), playersDeck.popCard(card.getCardId()));
                        player.demandCard();
                    }

                });
        });

    }

    private void setSleepOptions() {
        if (playersDeck.hasCardNumber(CardValues.CARD_FOUR)) {
            List<Card> list = playersDeck.getList();
            list.forEach(card -> {
                if (card.getCardNumber() == CardValues.CARD_FOUR)
                    options.add(new ThrowMoveOption(card, String.format(MESSAGE_THROW, card.getCardName())));
            });
        } else
            player.setSleepRounds(gameDeck.getSleepCount());
    }

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

    private void setPickOptions() {
        Card latestCard = gameDeck.getFirstCard();
        List<Card> list = playersDeck.getList();
        list.forEach(card -> {
            if (card.getCardNumber() == latestCard.getCardNumber() && isPickCard(card)
                    || isPickCard(card) && card.getCardColor().equals(latestCard.getCardColor()))
                options.add(new ThrowMoveOption(card, String.format(MESSAGE_THROW, card.getCardName())));
        });
    }

    private void setEventOptions() {
        if (gameDeck.isDemandGameEvent())
            setDemandOptions();
        if (gameDeck.isSleepGameEvent())
            setSleepOptions();
        if (gameDeck.isPickGameEvent())
            setPickOptions();
    }

    public List<MoveOption> getDemands() {
        List<Card> list = playersDeck.getList();
        options = new ArrayList<>();
        options.add(new DemandMoveOption());
        list.forEach(card -> {
            for (byte cardNumber : CardValues.CARDS_DEMAND_LIST)
                if (card.getCardNumber() == cardNumber)
                    options.add(new DemandMoveOption(card));
        });

        return options;
    }

    public List<MoveOption> get() {
        options = new ArrayList<>();

        if (!gameDeck.isSleepGameEvent())
            setGetCardOption();

        if (!gameDeck.isAnyGameEvent())
            setNoEventOptions();
        else
            setEventOptions();

        return options;
    }
}
