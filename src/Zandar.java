public class Zandar {

    static Frame frame;
    static StartScreen start_screen;
    static Board board;
    
    static Bot bot;
    static Player player;
    static Deck deck;

    enum ActivePlayer {PLAYER, BOT, NONE};
    static ActivePlayer active_player = ActivePlayer.NONE;
    public static void main(String[] args) throws Exception {
        frame = new Frame();
        showStartScreen();
        showBoard();
        initDeck();
        initPlayer();
        initBot();
        active_player = ActivePlayer.PLAYER;
        
        while(deck.cards.size() != 0) {
            dealCards();

            switch (active_player) {
                case PLAYER:
                    chooseAction();
                    break;
                case BOT:
                    break;
                default:
                    break;            
            }
        }

        active_player = active_player == ActivePlayer.PLAYER? ActivePlayer.BOT : active_player;

    }

    static void showStartScreen() throws InterruptedException {
        start_screen = new StartScreen();
        frame.add(start_screen);
        start_screen.initPlayers();
        start_screen.initStartButton();
        start_screen.initChoosePlayerLabel();

        start_screen.add(start_screen.start_btn);
        start_screen.add(start_screen.player_1_btn);
        start_screen.add(start_screen.player_2_btn);
        start_screen.add(start_screen.player_3_btn);
        start_screen.add(start_screen.player_4_btn);
        start_screen.add(start_screen.choose_player_label);

        start_screen.start_btn.setLocation(Constants.START_BUTTON_X, Constants.START_BUTTON_Y);
        start_screen.player_1_btn.setLocation(Constants.PLAYER_1_BUTTON_X, Constants.PLAYER_BUTTON_Y);
        start_screen.player_2_btn.setLocation(Constants.PLAYER_2_BUTTON_X, Constants.PLAYER_BUTTON_Y);
        start_screen.player_3_btn.setLocation(Constants.PLAYER_3_BUTTON_X, Constants.PLAYER_BUTTON_Y);
        start_screen.player_4_btn.setLocation(Constants.PLAYER_4_BUTTON_X, Constants.PLAYER_BUTTON_Y);
        start_screen.choose_player_label.setLocation(Constants.CHOOSE_PLAYER_LABEL_X, Constants.CHOOSE_PLAYER_LABEL_Y);

        while( !start_screen.start_game) {
            Thread.sleep(50);
        }
        start_screen.setVisible(false);
    }

    static void showBoard() {
        board = new Board();
        frame.add(board);
    }

    static void initPlayer() {
        player = new Player(start_screen.getChoosenPlayer());
        board.add(player);
        board.add(player.num_cards_label);
        board.add(player.deck_backside_label);
        player.setLocation(Constants.PLAYER_IMAGE_POSITION_X, Constants.PLAYER_IMAGE_POSITION_Y);
        player.num_cards_label.setLocation(Constants.PLAYER_NUM_CARD_POSITION_X, Constants.PLAYER_NUM_CARD_POSITION_Y);
        player.deck_backside_label.setLocation(Constants.PLAYER_DECK_POSITION_X, Constants.PLAYER_DECK_POSITION_Y);
    }

    static void initBot() {
        bot = new Bot();
        board.add(bot);
        board.add(bot.num_cards_label);
        board.add(bot.deck_backside_label);
        bot.setLocation(Constants.BOT_IMAGE_POSITION_X, Constants.BOT_IMAGE_POSITION_Y);
        bot.num_cards_label.setLocation(Constants.BOT_NUM_CARD_POSITION_X, Constants.BOT_NUM_CARD_POSITION_Y);
        bot.deck_backside_label.setLocation(Constants.BOT_DECK_POSITION_X, Constants.BOT_DECK_POSITION_Y);
    }

    static void initDeck() {
        deck = new Deck();
        board.add(deck.num_cards_label);
        board.add(deck.deck_backside_label);
        deck.num_cards_label.setLocation(Constants.DECK_NUM_CARD_POSITION_X, Constants.DECK_NUM_CARD_POSITION_Y);
        deck.deck_backside_label.setLocation(Constants.DECK_POSITION_X, Constants.DECK_POSITION_Y);
    }

    static void dealCards() throws InterruptedException{

        int X = Constants.CARDS_MOST_LEFT_POSITION;

        for(int num_card = 0, dealt_cards = 1; num_card < 8; num_card++, dealt_cards++) {
            Card card = deck.getCard();
            deck.num_cards_label.setText(Integer.toString(deck.cards.size()));
            if(num_card % 2 == active_player.ordinal()) { 
                player.cards.add(card);
                board.add(card);
                card.setLocation(X, Constants.PLAYER_CARD_Y);
            }
            else { 
                bot.cards.add(card);
                board.add(card);
                card.setLocation(X, Constants.BOT_CARD_Y);
            }
            X = dealt_cards == 2? X += Constants.PLAYER_CARD_DISTANCE : X; 
            dealt_cards = dealt_cards == 2 ? 0: dealt_cards;
            Thread.sleep(Constants.SLEEP_BETWEEN_DEALING);
        }

        X = Constants.CARDS_MOST_LEFT_POSITION;
        for(int i = 0; i < 4; i++) {
            Card card = deck.getCard();
            board.cards.add(card);
            board.add(card);
            deck.num_cards_label.setText(Integer.toString(deck.cards.size()));
            card.setLocation(X, Constants.BOARD_CARD_Y);
            X += Constants.BOARD_CARD_DISTANCE;
            Thread.sleep(Constants.SLEEP_BETWEEN_DEALING);
        }
    }

    static void chooseAction() {

    }
}
