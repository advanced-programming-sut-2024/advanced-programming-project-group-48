package server;

import model.GameEnvironment;

import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

public class GameSession implements Runnable {
    private final ClientHandler player1;
    private final ClientHandler player2;
    public CountDownLatch gameEndLatch;
    public static ArrayList<GameSession> allGameSessions = new ArrayList<>();
    public boolean isRunning = false;

    public GameSession(ClientHandler player1, ClientHandler player2, CountDownLatch gameEndLatch) {
        this.player1 = player1;
        this.player2 = player2;
        this.gameEndLatch = gameEndLatch;
        allGameSessions.add(this);
    }

    public boolean isRunning() {
        return isRunning;
    }

    @Override
    public void run() {
        this.isRunning=true;
        GameEnvironment game = new GameEnvironment(player1.getCurrentUser().getDeck(), player2.getCurrentUser().getDeck());
        String player1Json = game.toJson();
        player1.sendMessage(player1Json);


        while (true){
            String player1Move = player1.receiveMessage();
            if(player1Move.equals("end")){
                player2.sendMessage("end");
                break;
            }
            GameEnvironment gameEnvironment = GameEnvironment.fromJson(player1Move);
            gameEnvironment.swapGameEnvironmentFields();
            player2.sendMessage(gameEnvironment.toJson());
            String player2Move = player2.receiveMessage();
            if(player2Move.equals("end")){
                player1.sendMessage("end");
                break;
            }
            gameEnvironment = GameEnvironment.fromJson(player2Move);
            gameEnvironment.swapGameEnvironmentFields();
            player1.sendMessage(gameEnvironment.toJson());
        }

        // Game end - notify client handlers
//        gameEndLatch.countDown(); // This signals the end of the game to both ClientHandler threads
    }

}
