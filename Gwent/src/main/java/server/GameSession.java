package server;

public class GameSession implements Runnable {
    private final ClientHandler player1;
    private final ClientHandler player2;

    public GameSession(ClientHandler player1, ClientHandler player2) {
        this.player1 = player1;
        this.player2 = player2;
    }

    @Override
    public void run() {

    }

}
